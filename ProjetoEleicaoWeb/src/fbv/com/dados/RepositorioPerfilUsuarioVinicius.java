package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.PerfilUsuario;
import fbv.com.util.DBConnectionManager;

/*
Gerador Maurício Veiga ----------------------------------------------
Tipousuario = Nome da classe de entidade
pTipousuario = Parâmetro da entidade
*/

public class RepositorioPerfilUsuarioVinicius implements IRepositorioBD {
	
	private DBConnectionManager conMang;
	
	public RepositorioPerfilUsuarioVinicius() throws ExcecaoAcessoRepositorio, SQLException{
		conMang = DBConnectionManager.getInstance();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pPerfilUsuario) throws ExcecaoRegistroJaExistente {
		PerfilUsuario perfilUsuario = (PerfilUsuario)pPerfilUsuario;
		Connection con = conMang.getConnection("access");
        try
        {
        	if (con == null) {
				JOptionPane.showMessageDialog(null,
						"Erro ao se conectar com o banco");
				return;
			}
			Statement stmt = con.createStatement();
			con.setAutoCommit(false);
			//Armazenando os valores do código e a Descrição do TipoUsuario para passar para o statement
			int codigo = perfilUsuario.getId();
			String desc = perfilUsuario.getDescricao();			
			
           
            try
            {
            	//TODO: Alterar da maneira mais adequada
            	stmt.executeUpdate("INSERT INTO perfil_usuario VALUES(" + codigo + ",'" + desc + "');");
            	
            	con.commit();
    			stmt.close();
            }
            catch (Exception e)
            {
            	con.rollback();
                throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
            }finally{
            	conMang.freeConnection("access", con);
            }
            
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+perfilUsuario.getId());
        }

		
	}

	public void alterar(Object pPerfilUsuario) throws ExcecaoAcessoRepositorio {
		PerfilUsuario perfilUsuario = (PerfilUsuario)pPerfilUsuario;
		Connection con = conMang.getConnection("access");
        try
        {
        	if (con == null) {
				JOptionPane.showMessageDialog(null,
						"Erro ao se conectar com o banco");
				return;
			}
			Statement stmt = con.createStatement();
			con.setAutoCommit(false);
			//Armazenando os valores do código e a Descrição do TipoUsuario para passar para o statement
			int codigo = perfilUsuario.getId();
			String desc = perfilUsuario.getDescricao();			
			
            if (consultarPelaChave(perfilUsuario) != null)
            {
            	
                try
                {
                	//TODO: Alterar da maneira mais adequada
                	stmt.executeUpdate("UPDATE perfil_usuario SET DESCRICAO = '"+ desc +"' WHERE ID_PERFIL_USUARIO = " + codigo + ";");
                	con.commit();
        			stmt.close();
                }
                catch (Exception e)
                {
                	con.rollback();
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }finally{
                	conMang.freeConnection("access", con);
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
		Connection con = conMang.getConnection("access");
        try
        {
        	if (con == null) {
				JOptionPane.showMessageDialog(null,
						"Erro ao se conectar com o banco");
				return;
			}
			Statement stmt = con.createStatement();
			con.setAutoCommit(false);
			
            if (consultarPelaChave(perfilUsuario) != null)
            {
                try
                {
                   	stmt.executeUpdate("DELETE FROM perfil_usuario WHERE ID_PERFIL_USUARIO = " + codigo + ";");
                	con.commit();
        			stmt.close();
                }
                catch (Exception e)
                {
                	con.rollback();
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }finally{
                	conMang.freeConnection("access", con);
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
		Connection con = conMang.getConnection("access");
		try
        {
			ResultSet rs = null;
            try
            {
            	if (con == null) {
    				JOptionPane.showMessageDialog(null,
    						"Erro ao se conectar com o banco");
    				return null;
    			}
    			Statement stmt = con.createStatement();
    			con.setAutoCommit(false);
            	PerfilUsuario retorno = null;
            	rs = stmt.executeQuery("SELECT  * from perfil_usuario WHERE ID_PERFIL_USUARIO = " + codigo + ";");
                while (rs.next())
                {
                	//Capturando os valores do result set
                	int codTipoUsuario = rs.getInt ("ID_PERFIL_USUARIO");
					String dsTipoUsuario = rs.getString ("DESCRICAO");
					//Criando uma nova instância de TipoUsuario com os parâmetros capturados
                    //armanenando a instância em retorno
					retorno = new PerfilUsuario(codTipoUsuario,dsTipoUsuario);
                }
                con.commit();
    			stmt.close();

                return retorno;                               

            }
            catch (Exception e)
            {
            	con.rollback();
                throw new ExcecaoAcessoRepositorio("Erro ao buscar no banco de dados!" /*+ "/n" + e.toString()*/);
            }
            
			finally
			{
//				if (rs != null)
//				{
//					try {rs.close();}catch(Exception e) {}
//				}
//				if (conexao != null)
//				{
//					try {statement.close();}catch(Exception e) {}
//				}		
//				if (conexao != null)
//				{
//					try {conexao.close();}catch(Exception e) {}
//				}	
				conMang.freeConnection("access", con);
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
		Connection con = conMang.getConnection("access");
		try
        {
			ResultSet rs = null;
            try
            {
            	if (con == null) {
    				JOptionPane.showMessageDialog(null,
    						"Erro ao se conectar com o banco");
    				return null;
    			}
    			Statement stmt = con.createStatement();
    			con.setAutoCommit(false);
            	rs = stmt.executeQuery("SELECT  * from perfil_usuario ;");
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
                con.commit();
    			stmt.close();

            }
            catch (Exception e)
            {
            	con.rollback();
                throw new ExcecaoAcessoRepositorio("Erro ao buscar no banco de dados!" /*+ "/n" + e.toString()*/);
            }
            
			finally
			{
//				if (rs != null)
//				{
//					try {rs.close();}catch(Exception e) {}
//				}
//				if (conexao != null)
//				{
//					try {statement.close();}catch(Exception e) {}
//				}		
//				if (conexao != null)
//				{
//					try {conexao.close();}catch(Exception e) {}
//				}
            	conMang.freeConnection("access", con);
			}
       }
        catch (Exception e)
        {
            throw new ExcecaoRegistroNaoExistente(e.getMessage() /*+ "/n" + e.toString()*/);
        }
		return colecaoPerfilUsuario;
	}
	
} 
