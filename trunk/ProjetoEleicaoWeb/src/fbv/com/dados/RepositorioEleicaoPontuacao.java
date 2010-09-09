package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.util.EstadoEleicao;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Eleicao = Nome da classe de entidade
pEleicao = Parâmetro da entidade
*/

public class RepositorioEleicaoPontuacao implements IRepositorioBD {
	
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioEleicaoPontuacao() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pEleicao) throws ExcecaoRegistroJaExistente {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            try
            {
             	String sql = "INSERT INTO eleicao " +
								 "(ID_ESTADO, DESCRICAO, IN_PUBLICA, IN_VISIBILIDADE_ABERTA, " +
								  "IN_MAIS_DE_UM_VOTO, DT_FIM, DT_INICIO)" +
							 "VALUES(" + eleicao.getEstado().getValor() + ", '" + eleicao.getDescricao() + "', " + 
								 (eleicao.isPublica()? "1": "0") + ", " + 
								 (eleicao.isVisibilidadeVoto()? "1": "0") + ", " + 
								 (eleicao.isMultiplosVotos()? "1": "0") + ", " + 
								 (eleicao.getDataEncerramento() != null ? "'" + sdt.format(eleicao.getDataEncerramento()) + "'" : "null") + ", " +
								 (eleicao.getDataAbertura() != null ? "'" + sdt.format(eleicao.getDataAbertura()) + "'" : "null")+ ");";
	
				statement.executeUpdate(sql);
				
				int codigo = -1;
				
				ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
				
				if (rs.next())
					codigo = rs.getInt(1);
            	
            	sql = "INSERT INTO pontuacao " +
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
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+eleicao.getId());
        }
	}
	
	public void alterar(Object pEleicao) throws ExcecaoAcessoRepositorio {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
        try
        {
			int codigo = eleicao.getId();
			
            if (consultarPelaChave(eleicao) != null)
            {
                try
                {
                	String sql = eleicao.getEstado().getUpdateSQL(eleicao);
   	
                	statement.executeUpdate(sql);
				   	
                	if (eleicao.getEstado().getValor() == 1){
		            	sql = "UPDATE pontuacao SET " +
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
	
	public void excluir(Object pEleicao) throws ExcecaoAcessoRepositorio {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
		int codigo = eleicao.getId();
        try
        {
            if (consultarPelaChave(eleicao) != null)
            {
                try
                {
                   	statement.executeUpdate("DELETE FROM pontuacao WHERE ID_ELEICAO_PONTUACAO = " + codigo + ";");
                   	statement.executeUpdate("DELETE FROM eleicao WHERE ID_ELEICAO = " + codigo + ";");                 	
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
	
	private EleicaoPontuacao preencherObjeto(ResultSet rs) throws SQLException{
		
    	EleicaoPontuacao eleicao = new EleicaoPontuacao();
    	//Capturando os valores do result set
    	eleicao.setId(rs.getInt("ID_ELEICAO"));
    	eleicao.setEstado(EstadoEleicao.getEstado(rs.getInt("ID_ESTADO")));
		eleicao.setDescricao(rs.getString ("DESCRICAO"));
		eleicao.setPublica(rs.getBoolean("IN_PUBLICA"));
		eleicao.setVisibilidadeVoto(rs.getBoolean("IN_VISIBILIDADE_ABERTA"));
		eleicao.setMultiplosVotos(rs.getBoolean("IN_MAIS_DE_UM_VOTO"));
		eleicao.setDataAbertura(rs.getDate("DT_INICIO"));
		eleicao.setDataEncerramento(rs.getDate("DT_FIM"));
		eleicao.setPontuacaoMinima(rs.getInt("PONTUACAO_MINIMA"));
		eleicao.setPontuacaoMaxima(rs.getInt("PONTUACAO_MAXIMA"));
		eleicao.setIntervaloPontuacao(rs.getInt("GRAU_INTERVALOS"));

		return eleicao;
	}
} 
