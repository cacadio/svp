package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.util.EstadoEleicao;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Eleicao = Nome da classe de entidade
pEleicao = Parâmetro da entidade
*/

public class RepositorioEleicaoEscolhaUnica extends RepositorioEleicao implements IRepositorioBD {
	
	//private GerenciadorConexaoBDR banco;
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioEleicaoEscolhaUnica() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		//this.conexao = conexao;
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pEleicao) throws ExcecaoRegistroJaExistente {
		EleicaoEscolhaUnica eleicao = (EleicaoEscolhaUnica)pEleicao;
        try
        {
            try
            {
            	super.incluir(pEleicao);
            	
            	int codigo = -1;
            	
            	ResultSet rs = statement.executeQuery("SELECT MAX(ID_ELEICAO) AS ID_ELEICAO FROM eleicao");
            	
            	if (rs.next())
            		codigo = rs.getInt(1);
            	
            	String sql = "INSERT INTO escolha_unica " +
			   				 "(ID_ELEICAO_ESCOLHA_UNICA, ID_ELEICAO_PAI, " +
			   				 "BRANCO_NULO, PERCENT_VITORIA)" +
			   				 "VALUES(" + codigo + ", " + 
			   				 (eleicao.getEleicaoPai() != null? eleicao.getEleicaoPai().getId(): "null") + ", " + 
			   				 (eleicao.isCampoNulo()? "1": "0") + ", " +
			   				 eleicao.getPercentualVitoria() + ");";
            	
            	statement.executeUpdate(sql);
            	
            	eleicao.setId(codigo);
            }
            catch (Exception e)
            {
                throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
            } 
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+eleicao.getId());
        }
	}
	
	public void alterar(Object pEleicao) throws ExcecaoAcessoRepositorio {
		EleicaoEscolhaUnica eleicao = (EleicaoEscolhaUnica)pEleicao;
        try
        {
			int codigo = eleicao.getId();
			
            if (consultarPelaChave(eleicao) != null)
            {
                try
                {
                	super.alterar(pEleicao);
	            	
                	if (eleicao.getEstado().getValor() == 1){
		            	String sql = "UPDATE escolha_unica SET " +
		   				 		     "ID_ELEICAO_PAI = " + (eleicao.getEleicaoPai() != null? eleicao.getEleicaoPai().getId(): "null") + ", " +
		   				 		     "BRANCO_NULO = " + (eleicao.isCampoNulo()? "1": "0") + ", " +
		   				 		     "PERCENT_VITORIA = " + eleicao.getPercentualVitoria() + " " +
		   				 		     "WHERE ID_ELEICAO_ESCOLHA_UNICA = " + codigo + ";";
	           	
		            	statement.executeUpdate(sql);
                	}
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Erro ao remover no banco de dados! Registro não Existente!");
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoAcessoRepositorio("Erro ao inicializar dados do Repositorio!" );
        }
	}
	
	public void excluir(Object pEleicao) throws ExcecaoAcessoRepositorio {
		EleicaoEscolhaUnica eleicao = (EleicaoEscolhaUnica)pEleicao;
		int codigo = eleicao.getId();
        try
        {
			
            if (consultarPelaChave(eleicao) != null)
            {
                try
                {
                	statement.executeUpdate("DELETE FROM voto WHERE ID_ELEICAO = " + codigo + ";");
                	statement.executeUpdate("DELETE FROM opcao_voto WHERE ELEICAO_ID_ELEICAO = " + codigo + ";");
                   	statement.executeUpdate("DELETE FROM escolha_unica WHERE ID_ELEICAO_ESCOLHA_UNICA = " + codigo + ";");
                   	super.excluir(pEleicao);                	
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao excluir no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Não é possível excluir pois" + '\n' + " não existe este Eleicao !");
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoAcessoRepositorio(e.getMessage() );
        }

	}
	

	@Override
	/**
	 * BUSCA PELA CHAVE PRIMÁRIA
	 */
	public EleicaoEscolhaUnica consultarPelaChave(Object pEleicao) throws ExcecaoRegistroNaoExistente {
		Eleicao eleicao = (Eleicao)pEleicao;
		int codigo = eleicao.getId();
		try
	        {
				ResultSet rs = null;
	            try
	            {
	            	EleicaoEscolhaUnica retorno = null;
	            	
	            	GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
	        		Connection conexao = banco.getConexao(false);
	        		//this.conexao = conexao;
	        		Statement st = conexao.createStatement();		
	            	
	            	rs = st.executeQuery("SELECT E.*, EU.* from eleicao E " + 
	            								"INNER JOIN escolha_unica EU " +
	            										"ON EU.ID_ELEICAO_ESCOLHA_UNICA = E.ID_ELEICAO " +
	            								"WHERE E.ID_ELEICAO = " + codigo + ";");
	                while (rs.next())
	                {
	                	retorno = new EleicaoEscolhaUnica();
	                	//Capturando os valores do result set
	                	retorno.setId(rs.getInt("ID_ELEICAO"));
	                	retorno.setEstado(EstadoEleicao.getEstado(rs.getInt("ID_ESTADO")));
	                	retorno.setDescricao(rs.getString ("DESCRICAO"));
	                	retorno.setPublica(rs.getBoolean("IN_PUBLICA"));
	                	retorno.setVisibilidadeVoto(rs.getBoolean("IN_VISIBILIDADE_ABERTA"));
	                	retorno.setMultiplosVotos(rs.getBoolean("IN_MAIS_DE_UM_VOTO"));
	                	retorno.setDataAbertura(rs.getDate("DT_INICIO"));
	                	retorno.setDataEncerramento(rs.getDate("DT_FIM"));
	            		if (rs.getObject("ID_ELEICAO_PAI") != null){
	            			try {
	            				retorno.setEleicaoPai(this.consultarPelaChave(new EleicaoEscolhaUnica(rs.getInt("ID_ELEICAO_PAI"))));
	            			} catch (ExcecaoRegistroNaoExistente e) {
	            				e.printStackTrace();
	            			}
	            		}
	            		retorno.setCampoNulo(rs.getBoolean("BRANCO_NULO"));
	            		retorno.setPercentualVitoria(rs.getDouble("PERCENT_VITORIA"));
	                	//retorno = preencherObjeto(rs);
	                }
	
	                return retorno;                               
	
	            }
	            catch (Exception e)
	            {
	                throw new ExcecaoAcessoRepositorio("Erro ao buscar no banco de dados!" /*+ "/n" + e.toString()*/);
	            }
	            
				finally
				{
					/*if (rs != null)
					{
						try {rs.close();}catch(Exception e) {}
					}*/
					if (conexao != null)
					{
						try {statement.close();}catch(Exception e) {}
					}		
					if (conexao != null)
					{
						try {conexao.close();}catch(Exception e) {}
					}				
				}
	       }
        catch (Exception e)
        {
            throw new ExcecaoRegistroNaoExistente(e.getMessage() /*+ "/n" + e.toString()*/);
        }
	}

	public ArrayList<EleicaoEscolhaUnica> consultarTodos() throws SQLException {
		try
        {
			ArrayList<EleicaoEscolhaUnica> retorno = null;
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT E.*, EU.* from eleicao E " + 
            								"INNER JOIN escolha_unica EU " +
            										"ON EU.ID_ELEICAO_ESCOLHA_UNICA = E.ID_ELEICAO;");

            	retorno = new ArrayList<EleicaoEscolhaUnica>();
            	
            	
            	
            	while (rs.next())
                {
                	EleicaoEscolhaUnica eleicao = preencherObjeto(rs);
                	
                	retorno.add(eleicao);
                }

                return retorno;                               

            }
            catch (Exception e)
            {
                throw new ExcecaoAcessoRepositorio("Erro ao buscar no banco de dados!");
            }
            
			finally
			{
				if (rs != null)
				{
					try {rs.close();}catch(Exception e) {}
				}
				if (conexao != null)
				{
					try {statement.close();}catch(Exception e) {}
				}		
				if (conexao != null)
				{
					try {conexao.close();}catch(Exception e) {}
				}				
			}
        }
	    catch (Exception e)
	    {
	        throw new SQLException(e.getMessage());
	    }
	}
	
	@Override
	EleicaoEscolhaUnica preencherObjeto(ResultSet rs) throws SQLException{
		Eleicao eleicao = super.preencherObjeto(rs);
		// Monta uma eleiçãoEscolhaUnica com base na eleicao retornada
    	EleicaoEscolhaUnica eleicaoEscolhaUnica = new EleicaoEscolhaUnica(eleicao.getId());
    	eleicaoEscolhaUnica.setDataAbertura(eleicao.getDataAbertura());
    	eleicaoEscolhaUnica.setDataEncerramento(eleicao.getDataEncerramento());
    	eleicaoEscolhaUnica.setDescricao(eleicao.getDescricao());
    	eleicaoEscolhaUnica.setEstado(eleicao.getEstado());
    	eleicaoEscolhaUnica.setMultiplosVotos(eleicao.isMultiplosVotos());
    	eleicaoEscolhaUnica.setPublica(eleicao.isPublica());
    	eleicaoEscolhaUnica.setVisibilidadeVoto(eleicao.isVisibilidadeVoto());
    	
		if (rs.getObject("ID_ELEICAO_PAI") != null){
			try {
				eleicaoEscolhaUnica.setEleicaoPai(consultarPelaChave(new EleicaoEscolhaUnica(rs.getInt("ID_ELEICAO_PAI"))));
			} catch (ExcecaoRegistroNaoExistente e) {
				e.printStackTrace();
			}
		}
		
		eleicaoEscolhaUnica.setCampoNulo(rs.getBoolean("BRANCO_NULO"));
		eleicaoEscolhaUnica.setPercentualVitoria(rs.getDouble("PERCENT_VITORIA"));

		return eleicaoEscolhaUnica;
	}
} 
