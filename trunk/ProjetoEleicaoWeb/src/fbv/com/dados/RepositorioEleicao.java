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

public class RepositorioEleicao implements IRepositorioBD {

	private Connection conexao;
	private Statement statement; 
	
	public RepositorioEleicao() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	
	public void incluir(Object pObjeto) throws SQLException, ExcecaoRegistroJaExistente {
		Eleicao eleicao = (Eleicao)pObjeto;
        try
        {
            try
            {
				SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
				String sql = "INSERT INTO eleicao " 
						   + "(ID_ESTADO, DESCRICAO, IN_PUBLICA, IN_VISIBILIDADE_ABERTA, " 
						   + "IN_MAIS_DE_UM_VOTO, DT_FIM, DT_INICIO)" 
						   + "VALUES(" + eleicao.getEstado().getValor() + ", '" 
						               + eleicao.getDescricao() + "', " 
						               + (eleicao.isPublica()? "1": "0") + ", " 
						               + (eleicao.isVisibilidadeVoto()? "1": "0") + ", " 
						               + (eleicao.isMultiplosVotos()? "1": "0") + ", " 
						               + (eleicao.getDataEncerramento() != null ? "'" + sdt.format(eleicao.getDataEncerramento()) + "'" : "null") + ", " 
						               + (eleicao.getDataAbertura() != null ? "'" + sdt.format(eleicao.getDataAbertura()) + "'" : "null")+ ");";
		
				statement.executeUpdate(sql);
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
	
	public void alterar(Object pObjeto) throws SQLException, ExcecaoAcessoRepositorio {
		Eleicao eleicao = (Eleicao)pObjeto;
        try
        {
			int codigo = eleicao.getId();
			
            if (consultarPelaChave(eleicao) != null)
            {
            	SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
                try
                {
                	// Atualiza o estado
                	String sql = eleicao.getEstado().getUpdateSQL(eleicao);
   	
                	statement.executeUpdate(sql);
				   	
	            	// Atualiza a tabela de eleição
                	sql = "UPDATE eleicao SET "
                		+ "DESCRICAO = '" + eleicao.getDescricao() + "' " + ", " 
                		+ "IN_PUBLICA = " + (eleicao.isPublica()? "1": "0") + ", " 
                		+ "IN_VISIBILIDADE_ABERTA = " + (eleicao.isVisibilidadeVoto()? "1": "0") + ", " 
						+ "IN_MAIS_DE_UM_VOTO = " + (eleicao.isMultiplosVotos()? "1": "0") + ", " 
						+ "DT_FIM = " + (eleicao.getDataEncerramento() != null ? "'" + sdt.format(eleicao.getDataEncerramento()) + "'" : "null") + ", " 
						+ "DT_INICIO = " + (eleicao.getDataAbertura() != null ? "'" + sdt.format(eleicao.getDataAbertura()) + "'" : "null") 
                		+ "WHERE ID_ELEICAO = " + codigo + ";";
	            		
	            	statement.executeUpdate(sql);
	            
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

	public Object consultarPelaChave(Object pChave) throws SQLException, ExcecaoRegistroNaoExistente {
		Eleicao eleicao = (Eleicao)pChave;
		int codigo = eleicao.getId();
		try
	        {
				ResultSet rs = null;
	            try
	            {
	            	Eleicao retorno = null;
	            	rs = statement.executeQuery("SELECT * FROM eleicao WHERE ID_ELEICAO = " + codigo + ";");
	                while (rs.next())
	                {
	                	retorno = preencherObjeto(rs);
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
            throw new ExcecaoRegistroNaoExistente(e.getMessage() /*+ "/n" + e.toString()*/);
        }
	}

	@SuppressWarnings("unchecked")
	public ArrayList consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente {
		try
        {
			ArrayList<Eleicao> retorno = null;
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT * from eleicao;");

            	retorno = new ArrayList<Eleicao>();
            	
            	while (rs.next())
                {
                	Eleicao eleicao = preencherObjeto(rs);
                	
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

	public void excluir(Object pObject) throws SQLException, ExcecaoAcessoRepositorio {
		Eleicao eleicao = (Eleicao)pObject;
		int codigo = eleicao.getId();
        try
        {
        	statement.executeUpdate("DELETE FROM eleicao WHERE ID_ELEICAO = " + codigo + ";");                 	
        }
        catch (Exception e)
        {
            throw new ExcecaoAcessoRepositorio(e.getMessage() );
        }

	}

	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	Eleicao preencherObjeto(ResultSet rs) throws SQLException{
		
		Eleicao eleicao = new EleicaoPontuacao();
    	//Capturando os valores do result set
    	eleicao.setId(rs.getInt("ID_ELEICAO"));
    	eleicao.setEstado(EstadoEleicao.getEstado(rs.getInt("ID_ESTADO")));
		eleicao.setDescricao(rs.getString ("DESCRICAO"));
		eleicao.setPublica(rs.getBoolean("IN_PUBLICA"));
		eleicao.setVisibilidadeVoto(rs.getBoolean("IN_VISIBILIDADE_ABERTA"));
		eleicao.setMultiplosVotos(rs.getBoolean("IN_MAIS_DE_UM_VOTO"));
		eleicao.setDataAbertura(rs.getDate("DT_INICIO"));
		eleicao.setDataEncerramento(rs.getDate("DT_FIM"));
		
		return eleicao;
	}


}
