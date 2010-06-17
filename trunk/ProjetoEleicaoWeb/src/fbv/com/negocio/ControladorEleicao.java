package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.RepositorioEleicaoEscolhaUnica;
import fbv.com.dados.RepositorioEleicaoPontuacao;
import fbv.com.dados.RepositorioOpcaoVoto;
import fbv.com.dados.RepositorioVoto;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;


public class ControladorEleicao {
 
	private CadastroEleicaoEscolhaUnica cadastroEleicaoEscolhaUnica;
	
	private CadastroEleicaoPontuacao cadastroEleicaoPontuacao;
	 
	private CadastroOpcaoVoto cadastroOpcaoVoto;
	 
	private CadastroVoto cadastroVoto;
	
	private static ControladorEleicao controladorEleicao = null;
	
	public static ControladorEleicao getInstancia() throws ExcecaoAcessoRepositorio, SQLException{
		if(controladorEleicao == null){
			controladorEleicao = new ControladorEleicao();
		}
		return controladorEleicao;
	}
	
	private ControladorEleicao() throws ExcecaoAcessoRepositorio, SQLException{
		cadastroEleicaoEscolhaUnica = new CadastroEleicaoEscolhaUnica(new RepositorioEleicaoEscolhaUnica());
		cadastroEleicaoPontuacao = new CadastroEleicaoPontuacao(new RepositorioEleicaoPontuacao());
		cadastroOpcaoVoto = new CadastroOpcaoVoto(new RepositorioOpcaoVoto());
		cadastroVoto = new CadastroVoto(new RepositorioVoto());
		
	}
	
	/*
	 * EleicaoEscolhaUnica
	 * **/
	
	public void incluirEleicaoEscolhaUnica(EleicaoEscolhaUnica pEleicaoEscolhaUnica) throws SQLException, ExcecaoRegistroJaExistente{
		cadastroEleicaoEscolhaUnica.incluir(pEleicaoEscolhaUnica);
	}
	
	public void alterarEleicaoEscolhaUnica(EleicaoEscolhaUnica pEleicaoEscolhaUnica) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroEleicaoEscolhaUnica.alterar(pEleicaoEscolhaUnica);
	}
	
	public void excluirEleicaoEscolhaUnica(EleicaoEscolhaUnica pEleicaoEscolhaUnica) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroEleicaoEscolhaUnica.excluir(pEleicaoEscolhaUnica);
	}
	
	public EleicaoEscolhaUnica consultarEleicaoPelaChave(EleicaoEscolhaUnica pEleicaoEscolhaUnica) throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroEleicaoEscolhaUnica.consultarPelaChave(pEleicaoEscolhaUnica);
	}
	
	public ArrayList<EleicaoEscolhaUnica> consultarTodosEleicaoEscolhaUnicas() throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroEleicaoEscolhaUnica.consultarTodos();
	}
	
	/*
	 * EleicaoPontuacao
	 * **/
	
	public void incluirEleicaoPontuacao(EleicaoPontuacao pEleicaoPontuacao) throws SQLException, ExcecaoRegistroJaExistente{
		cadastroEleicaoPontuacao.incluir(pEleicaoPontuacao);
	}
	
	public void alterarEleicaoPontuacao(EleicaoPontuacao pEleicaoPontuacao) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroEleicaoPontuacao.alterar(pEleicaoPontuacao);
	}
	
	public void excluirEleicaoPontuacao(EleicaoPontuacao pEleicaoPontuacao) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroEleicaoPontuacao.excluir(pEleicaoPontuacao);
	}
	
	public EleicaoPontuacao consultarEleicaoPelaChave(EleicaoPontuacao pEleicaoPontuacao) throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroEleicaoPontuacao.consultarPelaChave(pEleicaoPontuacao);
	}
	
	public ArrayList<EleicaoPontuacao> consultarTodosEleicaoPontuacao() throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroEleicaoPontuacao.consultarTodos();
	}
	
	/*
	 * Opcao de voto
	 * **/
	
	public void incluirOpcaoVoto(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroJaExistente{
		cadastroOpcaoVoto.incluir(pOpcaoVoto);
	}
	
	public void alterarOpcaoVoto(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroOpcaoVoto.alterar(pOpcaoVoto);
	}
	
	public void excluirOpcaoVoto(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroOpcaoVoto.excluir(pOpcaoVoto);
	}
	
	public OpcaoVoto consultarOpcaoVotoPelaChave(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroOpcaoVoto.consultarPelaChave(pOpcaoVoto);
	}
	
	public ArrayList<OpcaoVoto> consultarTodosOpcaoVoto() throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroOpcaoVoto.consultarTodos();
	}
	
	/*
	 * Voto
	 * **/
	
	public void incluirVoto(Voto pVoto) throws SQLException, ExcecaoRegistroJaExistente{
		cadastroVoto.incluir(pVoto);
	}
	
	public void alterarVoto(Voto pVoto) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroVoto.alterar(pVoto);
	}
	
	public void excluirVoto(Voto pVoto) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroVoto.excluir(pVoto);
	}
	
	public Voto consultarVotoPelaChave(Voto pVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroVoto.consultarPelaChave(pVoto);
	}
	
	public ArrayList<Voto> consultarTodosVoto() throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroVoto.consultarTodos();
	}
}