package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.util.TipoEleicao;

public class Fachada {
 
	private ControladorEleicao controladorEleicao;
	
	private ControladorLogin controladorLogin;
	
	private static Fachada fachada = null;
	
	public static Fachada getInstancia() throws ExcecaoAcessoRepositorio, SQLException{
		
		if(fachada == null){
			fachada = new Fachada();
		}
		
		return fachada;
	}
	
	private Fachada() throws ExcecaoAcessoRepositorio, SQLException{
		controladorEleicao = ControladorEleicao.getInstancia();
		controladorLogin = ControladorLogin.getInstancia();
	}
	
	/*
	 * Eleicao
	 * **/
	
	public void incluirEleicao(Eleicao pEleicao) throws SQLException, ExcecaoRegistroJaExistente{
		controladorEleicao.incluirEleicao(pEleicao);
	}
	
	public void alterarEleicao(Eleicao pEleicao) throws SQLException, ExcecaoAcessoRepositorio{
		controladorEleicao.alterarEleicao(pEleicao);
	}
	
	public void excluirEleicao(Eleicao pEleicao) throws SQLException, ExcecaoAcessoRepositorio{
		controladorEleicao.excluirEleicao(pEleicao);
	}
	
	public Object consultarEleicaoPelaChave(Eleicao eleicao) throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorEleicao.consultarEleicaoPelaChave(eleicao);
	}
	
	public <T> ArrayList<T> consultarTodasEleicoes(TipoEleicao tipo) throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorEleicao.consultarTodasEleicoes(tipo);
	}
	
	/*
	 * Opcao de voto
	 * **/
	
	public void incluirOpcaoVoto(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroJaExistente{
		controladorEleicao.incluirOpcaoVoto(pOpcaoVoto);
	}
	
	public void alterarOpcaoVoto(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoAcessoRepositorio{
		controladorEleicao.alterarOpcaoVoto(pOpcaoVoto);
	}
	
	public void excluirOpcaoVoto(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoAcessoRepositorio{
		controladorEleicao.excluirOpcaoVoto(pOpcaoVoto);
	}
	
	public OpcaoVoto consultarOpcaoVotoPelaChave(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorEleicao.consultarOpcaoVotoPelaChave(pOpcaoVoto);
	}
	
	public ArrayList<OpcaoVoto> consultarTodosOpcaoVoto() throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorEleicao.consultarTodosOpcaoVoto();
	}
	
	//@Rodrigo
	public  ArrayList<OpcaoVoto> consultarPeloIDEleicao(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorEleicao.consultarPeloIDEleicao(pOpcaoVoto);
	}
	
	
	/*
	 * Perfil de Usuario
	 * */
	
	public void incluirPerfilUsuario(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoRegistroJaExistente{
		controladorLogin.incluirPerfilUsuario(pPerfilUsuario);
	}
	
	public void alterarPerfilUsuario(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		controladorLogin.alterarPerfilUsuario(pPerfilUsuario);
	}
	
	public void excluirPerfilUsuario(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		controladorLogin.excluirPerfilUsuario(pPerfilUsuario);
	}
	
	public PerfilUsuario consultarPerfilUsuarioPelaChave(PerfilUsuario pPerfilUsuario) throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorLogin.consultarPerfilUsuarioPelaChave(pPerfilUsuario);
	}
	
	public ArrayList<PerfilUsuario> consultarTodosPerfilUsuario() throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorLogin.consultarTodosPerfilUsuario();
	}
	
	/**
	 * Usuario
	 * @throws SQLException 
	 * @throws ExcecaoRegistroJaExistente 
	 * **/
	
	public void incluirUsuario(Usuario pUsuario) throws SQLException, ExcecaoRegistroJaExistente{
		controladorLogin.incluirUsuario(pUsuario);
	}
	
	public void alterarUsuario(Usuario pUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		controladorLogin.alterarUsuario(pUsuario);
	}
	
	public void excluirUsuario(Usuario pUsuario) throws SQLException, ExcecaoAcessoRepositorio{
		controladorLogin.excluirUsuario(pUsuario);
	}
	
	public Usuario consultarUsuarioPelaChave(Usuario pUsuario) throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorLogin.consultarUsuarioPelaChave(pUsuario);
	}
	
	public ArrayList<Usuario> consultarTodosUsuario() throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorLogin.consultarTodosUsuario();
	}
	
	/*
	 * Voto
	 * **/
	
	public void incluirVoto(Voto pVoto) throws SQLException, ExcecaoRegistroJaExistente{
		controladorEleicao.incluirVoto(pVoto);
	}
	
	public void alterarVoto(Voto pVoto) throws SQLException, ExcecaoAcessoRepositorio{
		controladorEleicao.alterarVoto(pVoto);
	}
	
	public void excluirVoto(Voto pVoto) throws SQLException, ExcecaoAcessoRepositorio{
		controladorEleicao.excluirVoto(pVoto);
	}
	
	public Voto consultarVotoPelaChave(Voto pVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorEleicao.consultarVotoPelaChave(pVoto);
	}
	
	public ArrayList<Voto> consultarTodosVoto() throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorEleicao.consultarTodosVoto();
	}
	
	public ArrayList<Voto> consultarVotoPorUsuarioEleicao(int idUsuario, int idEleicao) throws SQLException, ExcecaoRegistroNaoExistente{
		return controladorEleicao.consultarVotoPorUsuarioEleicao(idUsuario, idEleicao);
	}
	
	public ArrayList<ResultadoEleicao> consultarResultadoEleicao(int idEleicao) throws Exception{
		return controladorEleicao.consultarResultadoEleicao(idEleicao);
	}
}
 
