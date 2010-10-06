package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.negocio.OpcaoVoto;
import fbv.com.negocio.ResultadoEleicao;
import fbv.com.negocio.ResultadoOpcaoVoto;
import fbv.com.util.GerenciadorConexaoBDR;

public class RepositorioResultado implements IRepositorioResultado {

	private Statement statement; 
	
	public RepositorioResultado() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		this.statement = conexao.createStatement();				
	}

	public ResultadoEleicao consultar(Eleicao eleicao) throws Exception {
		try
        {
			ResultSet rs = null;
            try
            {
            	String sql = "SELECT ID_OPCAO_VOTO, DESCRICAO, CAMINHO_IMAGEM, " +
            						"(SELECT SUM(VALOR_VOTO) FROM voto" +
            						" WHERE ID_ELEICAO = ov.ELEICAO_ID_ELEICAO " + 
            						" AND ID_OPCAO_VOTO = ov.ID_OPCAO_VOTO) AS TOTAL_VOTOS_OPCAO, " +
            						"(SELECT SUM(VALOR_VOTO) FROM voto" +
            						" WHERE ID_ELEICAO = ov.ELEICAO_ID_ELEICAO) AS TOTAL_VOTOS" +
            				 " FROM opcao_voto ov" +
            				 " WHERE ELEICAO_ID_ELEICAO = " + eleicao.getId() + 
            				 " ORDER BY 4 DESC;";
            	
            	rs = statement.executeQuery(sql);
            	
            	ResultadoEleicao resultado  = new ResultadoEleicao(eleicao);

            	int totalVotos = 0;
                while (rs.next())
                {
                	//Capturando os valores do result set
                	int idOpcaoVoto = rs.getInt("ID_OPCAO_VOTO");
                	String descOpcaoVoto = rs.getString("DESCRICAO");
                	String pathFoto = rs.getString("CAMINHO_IMAGEM");
                	int vlVoto = rs.getInt("TOTAL_VOTOS_OPCAO");
                	totalVotos = rs.getInt("TOTAL_VOTOS");
                	
                	ResultadoOpcaoVoto resultOpcao = new ResultadoOpcaoVoto(new OpcaoVoto(idOpcaoVoto, eleicao.getId(), descOpcaoVoto, pathFoto));
                	resultOpcao.setTotalVotos(vlVoto);
                	resultOpcao.setPercentualVotos((vlVoto * 100.00) / totalVotos);
                	
                	resultado.getResultadoOpcoes().add(resultOpcao);
                }
                resultado.setTotalVotos(totalVotos);
                if (eleicao.getEstado().equals(Eleicao.APURADA) && eleicao instanceof EleicaoEscolhaUnica){
                	sql = "SELECT e.ID_ELEICAO, e.DESCRICAO FROM escolha_unica eu " +
                		"INNER JOIN eleicao e " +
                				"ON e.ID_ELEICAO = eu.ID_ELEICAO_ESCOLHA_UNICA " +
                		"WHERE eu.ID_ELEICAO_PAI = " + eleicao.getId();
                	
                	ResultSet rs2 = statement.executeQuery(sql);
                	
                	if (rs2.next()){
                		EleicaoEscolhaUnica eleicaoEU = new EleicaoEscolhaUnica(rs2.getInt("ID_ELEICAO"));
                		eleicaoEU.setDescricao(rs2.getString("DESCRICAO"));
                		resultado.setEleicao2Turno(eleicaoEU);
                	}
                }
                return resultado;                               

            }
            catch (Exception e)
            {
                throw new ExcecaoAcessoRepositorio("Erro ao buscar no banco de dados!" /*+ "/n" + e.toString()*/);
            }
            
			finally
			{
				if (rs != null)
				{
					try {rs.close();}catch(Exception e) {}
				}
			}
       }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() /*+ "/n" + e.toString()*/);
        }
	}

}
