package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.IRepositorioBD;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public class CadastroUsuario {
 
	private IRepositorioBD repositorioBD;
	
	public CadastroUsuario(IRepositorioBD repositorio){
		if(repositorio != null){
			this.repositorioBD = repositorio;
		}
	}
	
	public void incluir(Usuario pUsuario) throws SQLException, ExcecaoRegistroJaExistente{
		repositorioBD.incluir(pUsuario);
	}
	
	public void alterar(Usuario pUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.alterar(pUsuario);
	}
	
	public void excluir(Usuario pUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.excluir(pUsuario);
	}
	
	public Usuario consultarPelaChave(Usuario pUsuario) throws SQLException, ExcecaoRegistroNaoExistente{
		return (Usuario) repositorioBD.consultarPelaChave(pUsuario);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Usuario> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente{
		return repositorioBD.consultarTodos();
	}
	 
}
 
