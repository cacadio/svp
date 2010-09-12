package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.PerfilUsuario;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Tipousuario = Nome da classe de entidade
pTipousuario = Parâmetro da entidade
*/

public class RepositorioPerfilUsuario implements IRepositorioBD {
	
	private Connection conexao;
	private Statement statement;
	
	public RepositorioPerfilUsuario() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pPerfilUsuario) throws ExcecaoRegistroJaExistente {
		PerfilUsuario perfilUsuario = (PerfilUsuario)pPerfilUsuario;
        try
        {
			//Armazenando os valores do código e a Descrição do TipoUsuario para passar para o statement
			int codigo = perfilUsuario.getId();
			String desc = perfilUsuario.getDescricao();			
			
            if (consultarPelaChave(perfilUsuario) != null)
            {
            	throw new ExcecaoRegistroJaExistente(""+perfilUsuario.getId());

            }
            else
            {
                try
                {
                	//TODO: Alterar da maneira mais adequada
                	statement.executeUpdate("INSERT INTO perfil_usuario VALUES(" + codigo + ",'" + desc + "');");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
                } 
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+perfilUsuario.getId());
        }

		
	}

	public void alterar(Object pPerfilUsuario) throws ExcecaoAcessoRepositorio {
		PerfilUsuario perfilUsuario = (PerfilUsuario)pPerfilUsuario;
        try
        {

			//Armazenando os valores do código e a Descrição do TipoUsuario para passar para o statement
			int codigo = perfilUsuario.getId();
			String desc = perfilUsuario.getDescricao();			
			
            if (consultarPelaChave(perfilUsuario) != null)
            {
            	
                try
                {
                	//TODO: Alterar da maneira mais adequada
                	statement.executeUpdate("UPDATE perfil_usuario SET DESCRICAO = '"+ desc +"' WHERE ID_PERFIL_USUARIO = " + codigo + ";");
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
	
	public void excluir(Object pPerfilUsuario) throws ExcecaoAcessoRepositorio {
		PerfilUsuario perfilUsuario = (PerfilUsuario)pPerfilUsuario;
		int codigo = perfilUsuario.getId();
        try
        {
			
            if (consultarPelaChave(perfilUsuario) != null)
            {
                try
                {
                   	statement.executeUpdate("DELETE FROM perfil_usuario WHERE ID_PERFIL_USUARIO = " + codigo + ";");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Não é possível excluir pois" + '\n' + " não existe este TipoUsuario !");
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
	public PerfilUsuario consultarPelaChave(Object pPerfilUsuario) throws ExcecaoRegistroNaoExistente {
		PerfilUsuario perfilUsuario = (PerfilUsuario)pPerfilUsuario;
		int codigo = perfilUsuario.getId();
	try
        {
			ResultSet rs = null;
            try
            {
            	PerfilUsuario retorno = null;
            	rs = statement.executeQuery("SELECT  * from perfil_usuario WHERE ID_PERFIL_USUARIO = " + codigo + ";");
                while (rs.next())
                {
                	//Capturando os valores do result set
                	int codTipoUsuario = rs.getInt ("ID_PERFIL_USUARIO");
					String dsTipoUsuario = rs.getString ("DESCRICAO");
					//Criando uma nova instância de TipoUsuario com os parâmetros capturados
                    //armanenando a instância em retorno
					retorno = new PerfilUsuario(codTipoUsuario,dsTipoUsuario);
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

	public ArrayList<PerfilUsuario> consultarTodos() throws ExcecaoRegistroNaoExistente {
		ArrayList<PerfilUsuario> colecaoPerfilUsuario = new ArrayList<PerfilUsuario>();
		PerfilUsuario perfilUsuario = null;
		try
        {
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT  * from perfil_usuario ;");
                while (rs.next())
                {
                	//Capturando os valores do result set
                	int codTipoUsuario = rs.getInt ("ID_PERFIL_USUARIO");
					String dsTipoUsuario = rs.getString ("DESCRICAO");
					//Criando uma nova instância de TipoUsuario com os parâmetros capturados
                    //armanenando a instância em retorno
					perfilUsuario = new PerfilUsuario(codTipoUsuario,dsTipoUsuario);
					colecaoPerfilUsuario.add(perfilUsuario);                               
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
		return colecaoPerfilUsuario;
	}
	
} 
