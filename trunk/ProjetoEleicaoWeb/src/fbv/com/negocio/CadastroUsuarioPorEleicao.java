package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.IRepositorioBD;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public class CadastroUsuarioPorEleicao {
 
	private IRepositorioBD repositorioBD;
	
	public CadastroUsuarioPorEleicao(IRepositorioBD repositorio){
		if(repositorio != null){
			this.repositorioBD = repositorio;
		}
	}
	
	public void incluir(UsuarioPorEleicao pUsuarioPorEleicao) throws SQLException, ExcecaoRegistroJaExistente{
		repositorioBD.incluir(pUsuarioPorEleicao);
	}
	
	public void alterar(UsuarioPorEleicao pUsuarioPorEleicao) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.alterar(pUsuarioPorEleicao);
	}
	
	public void excluir(UsuarioPorEleicao pUsuarioPorEleicao) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.excluir(pUsuarioPorEleicao);
	}
	
	public UsuarioPorEleicao consultarPelaChave(UsuarioPorEleicao pUsuarioPorEleicao) throws SQLException, ExcecaoRegistroNaoExistente{
		return (UsuarioPorEleicao) repositorioBD.consultarPelaChave(pUsuarioPorEleicao);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<UsuarioPorEleicao> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente{
		return repositorioBD.consultarTodos();
	}
	 
}
 
