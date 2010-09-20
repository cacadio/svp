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
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Eleicao = Nome da classe de entidade
pEleicao = Parâmetro da entidade
*/

public class RepositorioEleicaoPontuacao extends RepositorioEleicao implements IRepositorioBD {
	
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioEleicaoPontuacao() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	@Override
	public void incluir(Object pEleicao) throws ExcecaoRegistroJaExistente {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
        try
        {
            try
            {
            	// Inclui na tabela de eleição
            	super.incluir(pEleicao);
				
				int codigo = -1;
				
				ResultSet rs = statement.executeQuery("SELECT MAX(ID_ELEICAO) AS ID_ELEICAO FROM eleicao");
				
				if (rs.next())
					codigo = rs.getInt(1);
            	
            	String sql = "INSERT INTO pontuacao " +
	   				 "(ID_ELEICAO_PONTUACAO, PONTUACAO_MINIMA, " +
	   				  "PONTUACAO_MAXIMA, GRAU_INTERVALOS)" +
	   				 "VALUES(" + codigo + 
	   				 		 ", " + eleicao.getPontuacaoMinima() +  
	   				 		 ", " + eleicao.getPontuacaoMaxima() + 
	   				 		 ", " + eleicao.getIntervaloPontuacao() + ");";
            	
            	statement.executeUpdate(sql);
            	
            	eleicao.setId(codigo);
            }
            catch (Exception e)
            {
                throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
            } 
        } catch (Exception e) {
            throw new ExcecaoRegistroJaExistente(""+eleicao.getId());
        } 
	}
	
	@Override
	public void alterar(Object pEleicao) throws ExcecaoAcessoRepositorio {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
        try
        {
			int codigo = eleicao.getId();
			
            if (consultarPelaChave(eleicao) != null)
            {
                try
                {
                	
                	super.alterar(pEleicao);
				   	
                	if (eleicao.getEstado().getValor() == 1){
                		String sql = "UPDATE pontuacao SET " +
		   				 			 "PONTUACAO_MINIMA = " + eleicao.getPontuacaoMinima() + ", " +
		   				 		     "PONTUACAO_MAXIMA = " + eleicao.getPontuacaoMaxima() + ", " +
		   				 		     "GRAU_INTERVALOS = " + eleicao.getIntervaloPontuacao() + " " +
		   				 	         "WHERE ID_ELEICAO_PONTUACAO = " + codigo + ";";
	           	
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
	
	@Override
	public void excluir(Object pEleicao) throws ExcecaoAcessoRepositorio {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
		int codigo = eleicao.getId();
        try
        {
            if (consultarPelaChave(eleicao) != null)
            {
                try
                {
                	statement.executeUpdate("DELETE FROM voto WHERE ID_ELEICAO = " + codigo + ";");
                	statement.executeUpdate("DELETE FROM opcao_voto WHERE ELEICAO_ID_ELEICAO = " + codigo + ";");
                   	statement.executeUpdate("DELETE FROM pontuacao WHERE ID_ELEICAO_PONTUACAO = " + codigo + ";");
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
	
	//	------------------	
    //BUSCA PELA CHAVE PRIMÁRIA
	//-------------------
	@Override
	public EleicaoPontuacao consultarPelaChave(Object pEleicao) throws ExcecaoRegistroNaoExistente {
		Eleicao eleicao = (Eleicao)pEleicao;
		int codigo = eleicao.getId();
		try
	        {
				ResultSet rs = null;
	            try
	            {
	            	EleicaoPontuacao retorno = null;
	            	rs = statement.executeQuery("SELECT E.*, P.* from eleicao E " + 
	            								"INNER JOIN pontuacao P " +
	            										"ON P.ID_ELEICAO_PONTUACAO = E.ID_ELEICAO " +
	            								"WHERE E.ID_ELEICAO = " + codigo + ";");
	                while (rs.next())
	                {
	                	retorno = preencherObjeto(rs);
	                }
	
	                return retorno;                               
	
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

	@Override
	public ArrayList<EleicaoPontuacao> consultarTodos() throws SQLException {
		try
        {
			ArrayList<EleicaoPontuacao> retorno = null;
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT E.*, P.* from eleicao E " + 
            								"INNER JOIN pontuacao P " +
            										"ON P.ID_ELEICAO_PONTUACAO = E.ID_ELEICAO;");

            	retorno = new ArrayList<EleicaoPontuacao>();
            	
            	while (rs.next())
                {
                	EleicaoPontuacao eleicao = preencherObjeto(rs);
                	
                	retorno.add(eleicao);
                }

                return retorno;                               

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
	
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@Override
	EleicaoPontuacao preencherObjeto(ResultSet rs) throws SQLException{
 
		Eleicao eleicao = super.preencherObjeto(rs);
		// Monta uma eleiçãoEscolhaUnica com base na eleicao retornada
		EleicaoPontuacao eleicaoPontuacao = new EleicaoPontuacao();
		eleicaoPontuacao.setId(eleicao.getId());
		eleicaoPontuacao.setDataAbertura(eleicao.getDataAbertura());
		eleicaoPontuacao.setDataEncerramento(eleicao.getDataEncerramento());
		eleicaoPontuacao.setDescricao(eleicao.getDescricao());
		eleicaoPontuacao.setEstado(eleicao.getEstado());
		eleicaoPontuacao.setMultiplosVotos(eleicao.isMultiplosVotos());
		eleicaoPontuacao.setPublica(eleicao.isPublica());
		eleicaoPontuacao.setVisibilidadeVoto(eleicao.isVisibilidadeVoto());

		eleicaoPontuacao.setPontuacaoMinima(rs.getInt("PONTUACAO_MINIMA"));
		eleicaoPontuacao.setPontuacaoMaxima(rs.getInt("PONTUACAO_MAXIMA"));
		eleicaoPontuacao.setIntervaloPontuacao(rs.getInt("GRAU_INTERVALOS"));

		return eleicaoPontuacao;
	}
} 
