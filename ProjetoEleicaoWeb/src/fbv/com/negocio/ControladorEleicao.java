package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import fbv.com.dados.RepositorioEleicaoEscolhaUnica;
import fbv.com.dados.RepositorioEleicaoPontuacao;
import fbv.com.dados.RepositorioOpcaoVoto;
import fbv.com.dados.RepositorioResultado;
import fbv.com.dados.RepositorioUsuarioPorEleicao;
import fbv.com.dados.RepositorioVoto;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.util.TipoEleicao;

public class ControladorEleicao {
	// Instância dos Cadastros
	private CadastroEleicaoEscolhaUnica cadastroEleicaoEscolhaUnica;
	private CadastroEleicaoPontuacao cadastroEleicaoPontuacao;
	private CadastroOpcaoVoto cadastroOpcaoVoto; 
	private CadastroVoto cadastroVoto;
	private CadastroResultadoEleicao cadastroResultado;
	private CadastroUsuarioPorEleicao cadastroUsuarioPorEleicao;
	// Instância do Controlador
	private static ControladorEleicao controladorEleicao = null;
	
	private int qtdEleicoesPontuacaoEmpatadas = 1;
	
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
		cadastroUsuarioPorEleicao = new CadastroUsuarioPorEleicao(new RepositorioUsuarioPorEleicao());
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
	
	public ResultadoEleicao consultarResultadoEleicao(Eleicao eleicao) throws Exception{
		ResultadoEleicao resultado = cadastroResultado.consultar(eleicao);
		return resultado;
	}
	
	public void processarResultadoEleicao(Eleicao eleicao) throws Exception{
		ResultadoEleicao resultado = cadastroResultado.consultar(eleicao);
		
		Collections.sort(resultado.getResultadoOpcoes(), new Comparator<ResultadoOpcaoVoto>() {
			public int compare(ResultadoOpcaoVoto res1, ResultadoOpcaoVoto res2){
				return res1.getTotalVotos() > res2.getTotalVotos()? -1: res1.getTotalVotos() < res2.getTotalVotos()? 1: 0;
			}
		});
		
		if (eleicao instanceof EleicaoEscolhaUnica) {
			EleicaoEscolhaUnica eleicaoEscUnica = (EleicaoEscolhaUnica)eleicao;
			
			if (eleicaoEscUnica.getEleicaoPai() == null){
				//Pega o percentual do mais votado e compara com o percentual de vitoria da eleicao
				if (resultado.getResultadoOpcoes().get(0).getPercentualVotos() < eleicaoEscUnica.getPercentualVitoria()){
					EleicaoEscolhaUnica eleicaoNova = new EleicaoEscolhaUnica();
					eleicaoNova.setDescricao("2o. Turno - " + eleicaoEscUnica.getDescricao());
					eleicaoNova.setEleicaoPai(eleicaoEscUnica);
					eleicaoNova.setCampoNulo(eleicaoEscUnica.isCampoNulo());
					eleicaoNova.setMultiplosVotos(eleicaoEscUnica.isMultiplosVotos());
					eleicaoNova.setPublica(eleicaoEscUnica.isPublica());
					eleicaoNova.setVisibilidadeVoto(eleicaoEscUnica.isVisibilidadeVoto());
					eleicaoNova.setEstado(Eleicao.NOVA);
					incluirEleicao(eleicaoNova);
					resultado.setEleicao2Turno(eleicaoNova);
					
					OpcaoVoto opcao1 = resultado.getResultadoOpcoes().get(0).getOpcaoVoto();
					opcao1.setId(0);
					opcao1.setIdEleicao(eleicaoNova.getId());
					incluirOpcaoVoto(opcao1);
					OpcaoVoto opcao2 = resultado.getResultadoOpcoes().get(1).getOpcaoVoto();
					opcao2.setId(0);
					opcao2.setIdEleicao(eleicaoNova.getId());
					incluirOpcaoVoto(opcao2);
				}
			}
			eleicaoEscUnica.setEstado(Eleicao.APURADA);
			eleicaoEscUnica.setDataEncerramento(new Date());
			cadastroEleicaoEscolhaUnica.alterar(eleicaoEscUnica);
		}
		else{
			EleicaoPontuacao eleicaoPont = (EleicaoPontuacao)eleicao;
			
			if (houveEmpate(resultado)){
				EleicaoPontuacao eleicaoNova = 
					new EleicaoPontuacao(eleicaoPont.getPontuacaoMaxima(), eleicaoPont.getPontuacaoMinima(), eleicaoPont.getIntervaloPontuacao());
				eleicaoNova.setDescricao("2o. Turno - " + eleicaoPont.getDescricao());
				eleicaoNova.setPublica(eleicaoPont.isPublica());
				eleicaoNova.setVisibilidadeVoto(eleicaoPont.isVisibilidadeVoto());
				eleicaoNova.setEstado(Eleicao.NOVA);
				incluirEleicao(eleicaoNova);
				incluirOpcoesDeVoto(eleicaoNova, resultado);	
			} 
			
			eleicaoPont.setEstado(Eleicao.APURADA);
			eleicaoPont.setDataEncerramento(new Date());
			cadastroEleicaoPontuacao.alterar(eleicaoPont);	
		}
	}
	
	private boolean houveEmpate(ResultadoEleicao resultado){
		boolean retorno = false;
		int maiorVotacao = 0;
		ResultadoOpcaoVoto opcaoComMaiorPontuacao = null;
		ArrayList<ResultadoOpcaoVoto> opcoes = resultado.getResultadoOpcoes();
		// Armazena a opcao que teve a maior pontuação
		for (ResultadoOpcaoVoto resultadoOpcaoVoto : opcoes) {
			if(resultadoOpcaoVoto.getTotalVotos() > maiorVotacao){
				maiorVotacao = resultadoOpcaoVoto.getTotalVotos();
				opcaoComMaiorPontuacao = resultadoOpcaoVoto;
			}
		}
		// Verifica se houve empate
		for (ResultadoOpcaoVoto resultadoOpcaoVoto : opcoes) {
			if(!resultadoOpcaoVoto.equals(opcaoComMaiorPontuacao) && resultadoOpcaoVoto.getTotalVotos() == maiorVotacao){
				qtdEleicoesPontuacaoEmpatadas = qtdEleicoesPontuacaoEmpatadas + 1;
				retorno = true;
			}
		}
		
		return retorno;
	}
	
	private void incluirOpcoesDeVoto(EleicaoPontuacao eleicaoNova, ResultadoEleicao resultadoEleicaoAntiga) throws Exception{
		for (int i = 0; i < qtdEleicoesPontuacaoEmpatadas; i++) {
			OpcaoVoto opcao = resultadoEleicaoAntiga.getResultadoOpcoes().get(i).getOpcaoVoto();
			opcao.setId(i);
			opcao.setIdEleicao(eleicaoNova.getId());
			incluirOpcaoVoto(opcao);
		}
	}
	/*
	 * Usuário por Eleição
	 * **/
	
	public void incluirUsuarioPorEleicao(UsuarioPorEleicao pUsuarioPorEleicao) throws SQLException, ExcecaoRegistroJaExistente{
		cadastroUsuarioPorEleicao.incluir(pUsuarioPorEleicao);
	}
	/*
	public void alterarUsuarioPorEleicao(UsuarioPorEleicao pUsuarioPorEleicao) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroUsuarioPorEleicao.alterar(pUsuarioPorEleicao);
	} 
	*/
	public void excluirUsuarioPorEleicao(UsuarioPorEleicao pUsuarioPorEleicao) throws SQLException, ExcecaoAcessoRepositorio{
		cadastroUsuarioPorEleicao.excluir(pUsuarioPorEleicao);
	}
	
	public UsuarioPorEleicao consultarUsuarioPorEleicaoPelaChave(UsuarioPorEleicao pUsuarioPorEleicao) throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroUsuarioPorEleicao.consultarPelaChave(pUsuarioPorEleicao);
	}
	
	public ArrayList<UsuarioPorEleicao> consultarTodosUsuarioPorEleicao() throws SQLException, ExcecaoRegistroNaoExistente{
		return cadastroUsuarioPorEleicao.consultarTodos();
	}
}
