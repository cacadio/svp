package fbv.com.dados;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public interface IRepositorioBD {
 
	/*public ControladorLogin controladorLogin;
	 
	public ControladorEleicao controladorEleicao;
	 
	public CadastroUsuario cadastroUsuario;
	 
	public CadastroEleicao cadastroEleicao;
	 
	public CadastroOpcaoVoto cadastroOpcaoVoto;
	 
	public CadastroTipoEleicao cadastroTipoEleicao;
	 
	public CadastroEstadoEleicao cadastroEstadoEleicao;
	 
	public CadastroTipoUsuario cadastroTipoUsuario;
	 
	public CadastroPessoa cadastroPessoa;*/
	 
	public void incluir(Object pObjeto) throws SQLException, ExcecaoRegistroJaExistente;
	public void alterar(Object pObjeto) throws SQLException, ExcecaoAcessoRepositorio;
	public void excluir(Object pObject) throws SQLException, ExcecaoAcessoRepositorio;
	public Object consultarPelaChave(Object pChave) throws SQLException, ExcecaoRegistroNaoExistente;
	public ArrayList consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente;
}
 
