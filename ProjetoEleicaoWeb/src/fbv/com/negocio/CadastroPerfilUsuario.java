package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.IRepositorioBD;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public class CadastroPerfilUsuario {
 
	private IRepositorioBD repositorioBD;
	
	public CadastroPerfilUsuario(IRepositorioBD repositorio){
		if(repositorio != null){
			this.repositorioBD = repositorio;
		}
	}
	
	public void incluir(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoRegistroJaExistente{
		repositorioBD.incluir(pPerfilUsuario);
	}
	
	public void alterar(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.alterar(pPerfilUsuario);
	}
	
	public void excluir(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.excluir(pPerfilUsuario);
	}
	
	public PerfilUsuario consultarPelaChave(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoRegistroNaoExistente{
		return (PerfilUsuario) repositorioBD.consultarPelaChave(pPerfilUsuario);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<PerfilUsuario> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente{
		return repositorioBD.consultarTodos();
	}
}
 
