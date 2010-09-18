package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.RepositorioEleicaoEscolhaUnica;
import fbv.com.dados.RepositorioEleicaoPontuacao;
import fbv.com.dados.RepositorioOpcaoVoto;
import fbv.com.dados.RepositorioResultado;
import fbv.com.dados.RepositorioVoto;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.util.TipoEleicao;


public class ControladorEleicao {
 
	private CadastroEleicaoEscolhaUnica cadastroEleicaoEscolhaUnica;
	
	private CadastroEleicaoPontuacao cadastroEleicaoPontuacao;
	 
	private CadastroOpcaoVoto cadastroOpcaoVoto;
	 
	private CadastroVoto cadastroVoto;
	
	private CadastroResultadoEleicao cadastroResultado;
	
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
		cadastroOpcaoVoto = new CadastroOpcaoVoto(new RepositorioOpcaoVoto() );
		cadastroVoto = new CadastroVoto(new RepositorioVoto());
		cadastroResultado = new CadastroResultadoEleicao(new RepositorioResultado());
	}
	
	/*
	 * Eleicao
	 * **/
	
	public void incluirEleicao(Eleicao pEleicao) throws SQLException, ExcecaoRegistroJaExistente{
		if (pEleicao instanceof EleicaoEscolhaUnica)
			cadastroEleicaoEscolhaUnica.incluir((EleicaoEscolhaUnica)pEleicao);
		else
			cadastroEleicaoPontuacao.incluir((EleicaoPontuacao)pEleicao);
	}
	
	public void alterarEleicao(Eleicao pEleicao) throws SQLException, ExcecaoAcessoRepositorio{
		if (pEleicao instanceof EleicaoEscolhaUnica)
			cadastroEleicaoEscolhaUnica.alterar((EleicaoEscolhaUnica)pEleicao);
		else
			cadastroEleicaoPontuacao.alterar((EleicaoPontuacao)pEleicao);
	}
	
	public void excluirEleicao(Eleicao pEleicao) throws SQLException, ExcecaoAcessoRepositorio{
		if (pEleicao instanceof EleicaoEscolhaUnica)
			cadastroEleicaoEscolhaUnica.excluir((EleicaoEscolhaUnica)pEleicao);
		else
			cadastroEleicaoPontuacao.excluir((EleicaoPontuacao)pEleicao);
	}
	
	public Object consultarEleicaoPelaChave(Eleicao eleicao) throws SQLException, ExcecaoRegistroNaoExistente{
		if (eleicao instanceof EleicaoEscolhaUnica)
			return cadastroEleicaoEscolhaUnica.consultarPelaChave(eleicao);
		else if(eleicao instanceof EleicaoPontuacao)
			return cadastroEleicaoPontuacao.consultarPelaChave(eleicao);
		else{
			Eleicao eleicaoAux = cadastroEleicaoEscolhaUnica.consultarPelaChave(eleicao);
			if(eleicaoAux == null)
				eleicaoAux = cadastroEleicaoPontuacao.consultarPelaChave(eleicao);
			
			return eleicaoAux;
		}
			
	}
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> consultarTodasEleicoes(TipoEleicao tipo) throws SQLException, ExcecaoRegistroNaoExistente{
		if (tipo != null){
			if (tipo == TipoEleicao.ESCOLHA_UNICA)
				return (ArrayList<T>) cadastroEleicaoEscolhaUnica.consultarTodos();
			else
				return (ArrayList<T>) cadastroEleicaoPontuacao.consultarTodos();
		} else {
			// Monta uma coleção com todas as eleições
			ArrayList<T> eleicoesEscolhaUnica = (ArrayList<T>) cadastroEleicaoEscolhaUnica.consultarTodos();
			ArrayList<T> eleicoesPontuacao = (ArrayList<T>) cadastroEleicaoPontuacao.consultarTodos();
			
			ArrayList<T> todasEleicoes = new ArrayList<T>();
			todasEleicoes.addAll(eleicoesEscolhaUnica);
			todasEleicoes.addAll(eleicoesPontuacao);
			
			return todasEleicoes;
		}

	}
	

	/*
	 * Opcao de voto
	 * **/
	
	//consulta opï¿½â€¹o voto pelo tipo de eleiï¿½â€¹o
	public  ArrayList<OpcaoVoto> consultarPeloIDEleicao(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroOpcaoVoto.consultarPeloIDEleicao(pOpcaoVoto);
	}
	
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
	
	public ArrayList<Voto> consultarVotoPorUsuarioEleicao(int idUsuario, int idEleicao) throws SQLException, ExcecaoRegistroNaoExistente{
		ArrayList<Voto> arrRetornoVotos = new ArrayList<Voto>();
		ArrayList<Voto> arrVotos = cadastroVoto.consultarTodos();
		
		for(Voto vt : arrVotos){
			if(vt.getEleicao().getId() == idEleicao && vt.getUsuario().getId() == idUsuario){
				arrRetornoVotos.add(vt);
			}
		}
		
		return arrRetornoVotos;
	}
	
	public ArrayList<ResultadoEleicao> consultarResultadoEleicao(int idEleicao) throws Exception{
		return cadastroResultado.consultar(idEleicao);
	}
}