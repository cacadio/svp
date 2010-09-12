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
import fbv.com.negocio.Usuario;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Usuario = Nome da classe de entidade
pUsuario = Parâmetro da entidade
*/

public class RepositorioUsuario implements IRepositorioBD {
	
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioUsuario() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pUsuario) throws ExcecaoRegistroJaExistente {
		Usuario usuario = (Usuario)pUsuario;
        try
        {
			//Armazenando os valores do código e a Descrição do Usuario para passar para o statement
			int codigo = 0;
			String nome = usuario.getNome();
			String login = usuario.getLogin();
			String senha = usuario.getSenha();
			String cpf = usuario.getCpf();
			PerfilUsuario perfilUsuario = usuario.getPerfilUsuario();
			int idPerfilUsuario = perfilUsuario.getId();
			
            if (consultarPelaChave(usuario) != null)
            {
            	throw new ExcecaoRegistroJaExistente(""+usuario.getCpf());

            }
            else
            {
                try
                {
                	ResultSet rs = statement.executeQuery("select max(id_usuario) as maximo from usuario;");
                	
                	if(rs.next()){
                		codigo = rs.getInt("maximo") + 1;                		
                	}else{
                		codigo = 1;
                	}
                	
                	statement.executeUpdate("INSERT INTO USUARIO VALUES(" + codigo 
                			+ ",'" 
                			+ login 
                			+ "' "
                			+ ",'" 
                			+ senha 
                			+ "' "
                			+ ",'" 
                			+ cpf 
                			+ "' "
                			+ ",'" 
                			+ nome 
                			+ "' "
                			+ "," 
                			+ idPerfilUsuario 
                			+ " "                			
                			+ ");");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
                } 
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+usuario.getCpf());
        }

		
	}
	
	public void alterar(Object pUsuario) throws ExcecaoAcessoRepositorio {
		Usuario usuario = (Usuario)pUsuario;
        try
        {
            if (consultarPelaChave(usuario) != null){
            	
            	//Armazenando os valores do código e a Descrição do Usuario para passar para o statement
            	int codigo = usuario.getId();
            	String nome = usuario.getNome();
            	String login = usuario.getLogin();
            	String senha = usuario.getSenha();
            	String cpf = usuario.getCpf();
            	PerfilUsuario perfilUsuario = usuario.getPerfilUsuario();
            	int idPerfilUsuario = perfilUsuario.getId();		
            	
                try
                {
                	statement.executeUpdate("UPDATE Usuario SET LOGIN = '"+ login +"' " 
                			+ ","
                			+ " SENHA = '"
                			+ senha +"'"
                			+ ","
                			+ " CPF_PESSOA = '"
                			+ cpf +"'"
                			+ ","
                			+ " NOME = '"
                			+ nome +"'"
                			+ ","
                			+ " PERFIL_USUARIO_ID_PERFIL_USUARIO = "
                			+ idPerfilUsuario 
                			+ " WHERE ID_USUARIO = " + codigo + ";");
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
	
	public void excluir(Object pUsuario) throws ExcecaoAcessoRepositorio {
		Usuario usuario = (Usuario)pUsuario;
        try
        {
            if (consultarPelaChave(usuario) != null){
            	int codigo = usuario.getId();
            	String cpf = usuario.getCpf();
            	
                try
                {
                   	statement.executeUpdate("DELETE FROM Usuario WHERE id_usuario = " + codigo + " OR CPF_PESSOA = '"+ cpf +"';");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Não é possível excluir pois" + '\n' + " não existe este Usuario !");
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
	public Usuario consultarPelaChave(Object pUsuario) throws ExcecaoRegistroNaoExistente {
		Usuario usuario = (Usuario)pUsuario;;
		String cpf = usuario.getCpf();
		try
        {
			ResultSet rs = null;
            try
            {
            	Usuario retorno = null;
            	rs = statement.executeQuery("SELECT  * from Usuario WHERE CPF_PESSOA like '" + cpf + "';");
                while (rs.next())
                {
                	//Capturando os valores do result set
                	int codUsuario = rs.getInt ("ID_USUARIO");
					String dsUsuario = rs.getString ("NOME");
                	String login = rs.getString("LOGIN");
                	String senha = rs.getString("SENHA");
                	String perfil = rs.getString("PERFIL_USUARIO_ID_PERFIL_USUARIO");
                	PerfilUsuario perfilUsuario = new PerfilUsuario();
                	
                	perfilUsuario.setId(Integer.valueOf(perfil.trim()));
                	//perfilUsuario.setDescricao(rs.getString ("DESCRICAO"));
					//Criando uma nova instância de Usuario com os parâmetros capturados
                    //armanenando a instância em retorno
					retorno = new Usuario();
					retorno.setId(codUsuario);
					retorno.setNome(dsUsuario);
					retorno.setCpf(cpf);
					retorno.setLogin(login);
					retorno.setSenha(senha);
					retorno.setPerfilUsuario(perfilUsuario);
					
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

	public ArrayList<Usuario> consultarTodos() throws ExcecaoRegistroNaoExistente {
		ArrayList<Usuario> colecaoUsuario = new ArrayList<Usuario>();
		Usuario usuario = null;
		try
        {
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT * from usuario inner join perfil_usuario on " 
            			+ "usuario.PERFIL_USUARIO_ID_PERFIL_USUARIO = perfil_usuario.id_perfil_usuario"
            			+ ";");
                while (rs.next())
                {
                	//Capturando os valores do result set
                	int codigo = rs.getInt("ID_USUARIO") ;
                	String nome = rs.getString("NOME");
                	String login = rs.getString("LOGIN");
                	String senha = rs.getString("SENHA");
                	String cpf = rs.getString("CPF_PESSOA");
                	PerfilUsuario perfilUsuario = new PerfilUsuario();
                	perfilUsuario.setId(rs.getInt ("ID_PERFIL_USUARIO"));
                	perfilUsuario.setDescricao(rs.getString ("DESCRICAO"));
                	
					//Criando uma nova instância de Usuario com os parâmetros capturados
					usuario = new Usuario(codigo, login, senha, nome, cpf, perfilUsuario);
					
					colecaoUsuario.add(usuario);                               
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
		return colecaoUsuario;
	}
} 
