package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.OpcaoVoto;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Opcaovoto = Nome da classe de entidade
pOpcaovoto = Parâmetro da entidade
*/

public class RepositorioOpcaoVoto implements IRepositorioBD, IRepositorioGenericoBD {
	
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioOpcaoVoto() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pOpcaoVoto) throws ExcecaoRegistroJaExistente {
		OpcaoVoto opcaoVoto = (OpcaoVoto)pOpcaoVoto;
        try
        {
			//Armazenando os valores do código e a Descrição do OpcaoVoto para passar para o statement
			String desc = opcaoVoto.getDescricao();
			int id_eleicao = opcaoVoto.getIdEleicao();
			String caminhoImagem = opcaoVoto.getPath_foto();
			
            if (consultarPelaChave(opcaoVoto) != null)
            {
            	throw new ExcecaoRegistroJaExistente(""+opcaoVoto.getId());

            }
            else
            {
                try
                {
                	statement.executeUpdate("INSERT INTO opcao_voto VALUES(null, " + id_eleicao + ",'" + desc + "', '"+ caminhoImagem+"');");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
                } 
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+opcaoVoto.getId());
        }

		
	}
	
	public void alterar(Object pOpcaoVoto) throws ExcecaoAcessoRepositorio {
		OpcaoVoto opcaoVoto = (OpcaoVoto)pOpcaoVoto;
        try
        {
			//Armazenando os valores do código e a Descrição do OpcaoVoto para passar para o statement
			int codigo = opcaoVoto.getId();
			String desc = opcaoVoto.getDescricao();
			String caminhoImagem = opcaoVoto.getPath_foto();
			int idEleicao = opcaoVoto.getIdEleicao();
			
            if (consultarPelaChave(opcaoVoto) != null)
            {
            	
                try
                {
                   	statement.executeUpdate("UPDATE opcao_voto SET descricao = '"+ desc +"'," 
                   			+ " eleicao_id_eleicao = "
                   			+ idEleicao
                   			+ " , "
                   			+ " caminho_imagem = "
                   			+ "'"+ caminhoImagem +"'"
                   			+ " WHERE id_opcao_voto = " + codigo + ";");
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
	
	public void excluir(Object pOpcaoVoto) throws ExcecaoAcessoRepositorio {
		OpcaoVoto opcaoVoto = (OpcaoVoto)pOpcaoVoto;
		int codigo = opcaoVoto.getId();
        try
        {
			
            if (consultarPelaChave(opcaoVoto) != null)
            {
                try
                {
                	String sql = "DELETE FROM opcao_voto WHERE id_opcao_voto = " + codigo + ";";
                   	statement.executeUpdate(sql);
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Não é possível excluir pois" + '\n' + " não existe este OpcaoVoto !");
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
	public OpcaoVoto consultarPelaChave(Object pOpcaoVoto) throws ExcecaoRegistroNaoExistente {
		OpcaoVoto opcaoVoto = (OpcaoVoto)pOpcaoVoto;
		int codigo = opcaoVoto.getId();
		try
	        {
				ResultSet rs = null;
	            try
	            {
	            	OpcaoVoto retorno = null;
	            	rs = statement.executeQuery("SELECT * FROM opcao_voto WHERE id_opcao_voto = " + codigo);
	                while (rs.next())
	                {
	                	//Capturando os valores do result set
	                	int idOpcaoVoto = rs.getInt ("id_opcao_voto");
						String dsOpcaoVoto = rs.getString ("descricao");
						String caminhoImagem = rs.getString("caminho_imagem");
						int idEleicao = rs.getInt("eleicao_id_eleicao");
						//Criando uma nova instância de OpcaoVoto com os parâmetros capturados
	                    //armanenando a instância em retorno
						retorno = new OpcaoVoto(idOpcaoVoto, idEleicao, dsOpcaoVoto, caminhoImagem);
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
	
//	------------------	
    //BUSCA PELO ID ELEICAO
	//-------------------
	public  ArrayList<OpcaoVoto> consultarPeloIDEleicao(Object pOpcaoVoto) throws ExcecaoRegistroNaoExistente {
		OpcaoVoto opcaoVoto = (OpcaoVoto)pOpcaoVoto;
		ArrayList<OpcaoVoto> colecaoOpcaoVoto = new ArrayList<OpcaoVoto>();
		int idEleicao = opcaoVoto.getIdEleicao();
		try
	        {
				ResultSet rs = null;
	            try
	            {
	            	rs = statement.executeQuery("SELECT OPCAO_VOTO.ID_OPCAO_VOTO, OPCAO_VOTO.ELEICAO_ID_ELEICAO,OPCAO_VOTO.DESCRICAO,OPCAO_VOTO.CAMINHO_IMAGEM FROM svp.OPCAO_VOTO WHERE OPCAO_VOTO.ELEICAO_ID_ELEICAO = " + idEleicao);
	            	
	                while (rs.next())
	                {
	                	//Capturando os valores do result set
	                	int idOpcaoVoto = rs.getInt ("id_opcao_voto");
						String dsOpcaoVoto = rs.getString ("descricao");
						String caminhoImagem = rs.getString("caminho_imagem");
						idEleicao = rs.getInt("eleicao_id_eleicao");
						//Criando uma nova instância de Usuario com os parâmetros capturados
						opcaoVoto = new OpcaoVoto(idOpcaoVoto, idEleicao ,dsOpcaoVoto, caminhoImagem);
						colecaoOpcaoVoto.add(opcaoVoto);  
	                }
	
	                return colecaoOpcaoVoto;                               
	
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

	public ArrayList<OpcaoVoto> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente {
		ArrayList<OpcaoVoto> colecaoOpcaoVoto = new ArrayList<OpcaoVoto>();
		OpcaoVoto opcaoVoto = null;
		try
        {
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT * FROM opcao_voto ORDER BY eleicao_id_eleicao, id_opcao_voto");
                while (rs.next())
                {
                	//Capturando os valores do result set
                	int codigo = rs.getInt("id_opcao_voto") ;
                	String descricao = rs.getString("descricao");
                	String caminhoImagem = rs.getString("caminho_imagem");
                	int idEleicao = rs.getInt("eleicao_id_eleicao");
                	
					//Criando uma nova instância de Usuario com os parâmetros capturados
					opcaoVoto = new OpcaoVoto(codigo, idEleicao ,descricao, caminhoImagem);
					
					colecaoOpcaoVoto.add(opcaoVoto);                               
                }

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
		return colecaoOpcaoVoto;
	}
} 
