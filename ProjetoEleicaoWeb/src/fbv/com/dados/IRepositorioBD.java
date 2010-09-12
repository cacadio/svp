package fbv.com.dados;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public interface IRepositorioBD {
	 
	public void incluir(Object pObjeto) throws SQLException, ExcecaoRegistroJaExistente;
	public void alterar(Object pObjeto) throws SQLException, ExcecaoAcessoRepositorio;
	public void excluir(Object pObject) throws SQLException, ExcecaoAcessoRepositorio;
	public Object consultarPelaChave(Object pChave) throws SQLException, ExcecaoRegistroNaoExistente;
	@SuppressWarnings("unchecked")
	public ArrayList consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente;
}
 
