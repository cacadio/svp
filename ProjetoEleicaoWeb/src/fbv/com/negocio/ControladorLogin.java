package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.RepositorioPerfilUsuario;
import fbv.com.dados.RepositorioUsuario;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public class ControladorLogin {
 
	private CadastroUsuario cadastroUsuario;
	 
	private CadastroPerfilUsuario cadastroPerfilUsuario;
	
	private static ControladorLogin controladorLogin = null;
	
	public static ControladorLogin getInstancia() throws ExcecaoAcessoRepositorio, SQLException{
		if(controladorLogin == null){
			controladorLogin = new ControladorLogin();
		}
		return controladorLogin;
	}
	
	public ControladorLogin() throws ExcecaoAcessoRepositorio, SQLException{
		cadastroPerfilUsuario = new CadastroPerfilUsuario(new RepositorioPerfilUsuario());
		cadastroUsuario = new CadastroUsuario(new RepositorioUsuario());
	}
	
	/*
	 * Tipo de Usuario
	 * */
	
	public void incluirPerfilUsuario(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoRegistroJaExistente{
		cadastroPerfilUsuario.incluir(pPerfilUsuario);
	}
	
	public void alterarPerfilUsuario(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroPerfilUsuario.alterar(pPerfilUsuario);
	}
	
	public void excluirPerfilUsuario(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroPerfilUsuario.excluir(pPerfilUsuario);
	}
	
	public PerfilUsuario consultarPerfilUsuarioPelaChave(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroPerfilUsuario.consultarPelaChave(pPerfilUsuario);
	}
	
	public ArrayList<PerfilUsuario> consultarTodosPerfilUsuario() throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroPerfilUsuario.consultarTodos();
	}
	
	/**
	 * Usuario
	 * @throws SQLException 
	 * @throws ExcecaoRegistroJaExistente 
	 * **/
	
	public void incluirUsuario(Usuario pUsuario) throws SQLException, ExcecaoRegistroJaExistente{
		cadastroUsuario.incluir(pUsuario);
	}
	
	public void alterarUsuario(Usuario pUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroUsuario.alterar(pUsuario);
	}
	
	public void excluirUsuario(Usuario pUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroUsuario.excluir(pUsuario);
	}
	
	public Usuario consultarUsuarioPelaChave(Usuario pUsuario) throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroUsuario.consultarPelaChave(pUsuario);
	}
	
	public ArrayList<Usuario> consultarTodosUsuario() throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroUsuario.consultarTodos();
	}
	 
}
 
