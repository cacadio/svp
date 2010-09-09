package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.OpcaoVoto;
import fbv.com.negocio.ResultadoEleicao;
import fbv.com.util.GerenciadorConexaoBDR;

public class RepositorioResultado implements IRepositorioResultado {

	private Statement statement; 
	
	public RepositorioResultado() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		this.statement = conexao.createStatement();				
	}

	public ArrayList<ResultadoEleicao> consultar(int idEleicao) throws Exception {
		try
        {
			ResultSet rs = null;
            try
            {
            	String sql = "SELECT ID_OPCAO_VOTO, DESCRICAO, CAMINHO_IMAGEM, " +
            						"(SELECT SUM(VALOR_VOTO) FROM voto" +
            						" WHERE ID_ELEICAO = " + idEleicao +
            						" AND ID_OPCAO_VOTO = ov.ID_OPCAO_VOTO) AS VALOR_TOTAL_VOTOS" +
            				 " FROM opcao_voto ov" +
            				 " WHERE ID_ELEICAO = " + idEleicao + ";";
            	
            	rs = statement.executeQuery(sql);
            	
            	ArrayList<ResultadoEleicao> resultado = new ArrayList<ResultadoEleicao>();
            	
                while (rs.next())
                {
                	//Capturando os valores do result set
                	int idOpcaoVoto = rs.getInt("ID_OPCAO_VOTO");
                	String descOpcaoVoto = rs.getString("DESCRICAO");
                	String pathFoto = rs.getString("CAMINHO_IMAGEM");
                	int vlVoto = rs.getInt("VALOR_TOTAL_VOTOS");
                	
                	ResultadoEleicao resultOpcao = new ResultadoEleicao(new Eleicao(idEleicao));
                	resultOpcao.setOpcaoVoto(new OpcaoVoto(idOpcaoVoto, idEleicao, descOpcaoVoto, pathFoto));
                	resultOpcao.setTotalVotos(vlVoto);
                	
                	resultado.add(resultOpcao);
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
