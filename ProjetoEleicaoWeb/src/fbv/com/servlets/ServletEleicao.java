package fbv.com.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.negocio.EstadoConcluida;
import fbv.com.negocio.EstadoIniciada;
import fbv.com.negocio.EstadoNova;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.Usuario;
import fbv.com.negocio.UsuarioPorEleicao;
import fbv.com.util.InterfacePrincipal;
import fbv.com.util.TipoEleicao;

public class ServletEleicao extends HttpServlet implements InterfacePrincipal {

	private static final long serialVersionUID = 1L;
	
	public ServletEleicao(){
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			String idEvento = request
					.getParameter(ServletEleicao.ID_REQ_EVENTO);

			if (idEvento != null && !idEvento.equals("")) {

				if (idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
				} else if (idEvento.equals(ServletEleicao.ID_REQ_EVENTO_EXIBIR_INCLUSAO)) {
					exibirInclusao(request, response);
				} else if (idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_INCLUSAO)) {
					processarInclusao(request, response);
				} else if (idEvento.equals(ServletEleicao.ID_REQ_EVENTO_EXIBIR_ALTERACAO)) {
					exibirAlteracao(request, response);
				} else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_ALTERACAO)){
					processarAlteracao(request,response);
				} else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_EXIBIR_EXCLUSAO)){
					exibirExclusao(request,response);
				} else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO)){
					processarExclusao(request,response);
				} else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_INICIALIZACAO)){
					processarInicializacao(request, response);
				} else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_CONCLUS�O)){
					processarConclusao(request, response);
				} else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_APURACAO)){
					processarApuracao(request, response);
				}

			} else {
				processarFiltroConsulta(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processarFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ArrayList<Eleicao> arrayEleicao = new ArrayList<Eleicao>();

		Fachada fachada = Fachada.getInstancia();

		String idEleicao = request.getParameter(ID_REQ_CODIGO_ELEICAO);
		TipoEleicao tipoEleicao = null;
		if (request.getParameter(ID_REQ_TIPO_ELEICAO) != null && !request.getParameter(ID_REQ_TIPO_ELEICAO).equals("")){
			int idTipoEleicao = new Integer(request.getParameter(ID_REQ_TIPO_ELEICAO)).intValue();
			if (idTipoEleicao == TipoEleicao.ESCOLHA_UNICA.value())
				tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
			else
				tipoEleicao = TipoEleicao.PONTUACAO;
		}
		
		if (tipoEleicao != null && idEleicao != null && !idEleicao.equals("")) {
			Eleicao eleicao = null;
			if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA)
				eleicao = new EleicaoEscolhaUnica();
			else
				eleicao = new EleicaoPontuacao();
			
			eleicao.setId(Integer.valueOf(idEleicao));
			if (eleicao instanceof EleicaoEscolhaUnica)
				eleicao = (EleicaoEscolhaUnica)fachada.consultarEleicaoPelaChave(eleicao);
			else
				eleicao = (EleicaoPontuacao)fachada.consultarEleicaoPelaChave(eleicao);

			if (eleicao != null) {
				arrayEleicao.add(eleicao);
			}

		} else {
			arrayEleicao = fachada.consultarTodasEleicoes(tipoEleicao);
		}
		if(tipoEleicao != null)
			request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		
		request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO, arrayEleicao);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consultaEleicao.jsp");
		requestDispatcher.forward(request, response);

	}


	private void exibirInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (request.getParameter(ID_REQ_TIPO_ELEICAO) != null && !request.getParameter(ID_REQ_TIPO_ELEICAO).equals("")){
			if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1")){
				ArrayList<EleicaoEscolhaUnica> eleicoes = Fachada.getInstancia().consultarTodasEleicoes(TipoEleicao.ESCOLHA_UNICA);
				
				request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO, eleicoes);
			}
			request.setAttribute(ID_REQ_TIPO_ELEICAO, Integer.parseInt(request.getParameter(ID_REQ_TIPO_ELEICAO)));
		} else {
			request.setAttribute(ID_REQ_TIPO_ELEICAO, TipoEleicao.ESCOLHA_UNICA.value());
		}

		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		// Monta um array com todos os usu�rios e e seta no request
		carregarComboUsuarios(request);
		// Redireciona para a tela de inclus�o
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/inclusaoEleicao.jsp");
		requestDispatcher.forward(request, response);
	}

	private void processarInclusao(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		
		Fachada fachada = Fachada.getInstancia();
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		String descricaoEleicao = request
				.getParameter(ServletEleicao.ID_REQ_DESCRICAO_ELEICAO);
		
		TipoEleicao tipoEleicao;
		if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1"))
			tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
		else
			tipoEleicao = TipoEleicao.PONTUACAO;

		Eleicao eleicao = null;
		
		if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA){
			EleicaoEscolhaUnica eleicaoEU = new EleicaoEscolhaUnica();
			if ((request.getParameter(ID_REQ_CODIGO_ELEICAO_PAI) != null) && (!request.getParameter(ID_REQ_CODIGO_ELEICAO_PAI).equals("0")))
				eleicaoEU.setEleicaoPai(new EleicaoEscolhaUnica(Integer.parseInt(request.getParameter(ID_REQ_CODIGO_ELEICAO_PAI))));
			eleicaoEU.setCampoNulo(request.getParameter(ID_REQ_IN_CAMPO_NULO_ELEICAO).equals("1"));
			eleicaoEU.setPercentualVitoria(Double.valueOf(request.getParameter(ID_REQ_PERCENTUAL_VITORIA_ELEICAO)));
			
			eleicao = eleicaoEU;
		}
		else{
			EleicaoPontuacao eleicaoP = new EleicaoPontuacao();
			eleicaoP.setPontuacaoMinima(Integer.valueOf(request.getParameter(ID_REQ_PONTUACAO_MINIMA_ELEICAO)));
			eleicaoP.setPontuacaoMaxima(Integer.valueOf(request.getParameter(ID_REQ_PONTUACAO_MAXIMA_ELEICAO)));
			eleicaoP.setIntervaloPontuacao(Integer.valueOf(request.getParameter(ID_REQ_INTERVALO_PONTUACAO_ELEICAO)));
			
			eleicao = eleicaoP;
		}
		
		if (descricaoEleicao != null
				&& !descricaoEleicao.equals("")) {
			eleicao.setDescricao(descricaoEleicao);
		}
		eleicao.setEstado(EstadoNova.getInstancia());
		eleicao.setPublica(request.getParameter(ID_REQ_IN_PUBLICA_ELEICAO).equals("1"));
		eleicao.setVisibilidadeVoto(request.getParameter(ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO).equals("1"));
		eleicao.setMultiplosVotos(request.getParameter(ID_REQ_IN_VOTO_MULTIPLO_ELEICAO).equals("1"));
		//eleicao.setDataAbertura(sdt.parse(request.getParameter(ID_REQ_DATA_INICIO_ELEICAO)));
		eleicao.setDataAbertura(new Date(System.currentTimeMillis()));
		if (request.getParameter(ID_REQ_DATA_FIM_ELEICAO) != null && !request.getParameter(ID_REQ_DATA_FIM_ELEICAO).equals(""))
			eleicao.setDataEncerramento(sdt.parse(request.getParameter(ID_REQ_DATA_FIM_ELEICAO)));

		fachada.incluirEleicao(eleicao);
		mensagem = "Elei��o Cadastrada com Sucesso";

		String[] usuariosDaEleicao = request.getParameterValues(ID_REQ_ARRAY_LIST_USUARIO);
		incluirUsuariosPorEleicao(usuariosDaEleicao);
		
		request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		request.setAttribute(ID_REQ_TITULO_PAGINA, "Elei��o");
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirAlteracao(HttpServletRequest request, HttpServletResponse response) throws Exception {

		obterDadosDaChavePrimaria(request, response);		
		
		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/alteracaoEleicao.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarAlteracao(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String mensagem = "Elei��o Alterada com Sucesso";
		Eleicao eleicao = montarEleicaoParaAlteracao(request, response);
		
		realizarAlteracao(request, response, eleicao, mensagem);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private Eleicao montarEleicaoParaAlteracao(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		
		Fachada fachada = Fachada.getInstancia();
		
		String idEleicao = request.getParameter(ServletEleicao.ID_REQ_CODIGO_ELEICAO);
		String descricaoEleicao = request.getParameter(ID_REQ_DESCRICAO_ELEICAO);

		TipoEleicao tipoEleicao;
		if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1"))
			tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
		else
			tipoEleicao = TipoEleicao.PONTUACAO;

		Eleicao eleicao = null;
		if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA)
			eleicao = new EleicaoEscolhaUnica();
		else
			eleicao = new EleicaoPontuacao();
		
		eleicao.setId(new Integer(idEleicao));
		
		if (eleicao instanceof EleicaoEscolhaUnica)
			eleicao = (EleicaoEscolhaUnica)fachada.consultarEleicaoPelaChave(eleicao);
		else
			eleicao = (EleicaoPontuacao)fachada.consultarEleicaoPelaChave(eleicao);
				
		if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA){
			if ((request.getParameter(ID_REQ_CODIGO_ELEICAO_PAI) != null) && (!request.getParameter(ID_REQ_CODIGO_ELEICAO_PAI).equals("0")))
				((EleicaoEscolhaUnica)eleicao).setEleicaoPai(new EleicaoEscolhaUnica(Integer.parseInt(request.getParameter(ID_REQ_CODIGO_ELEICAO_PAI))));
			((EleicaoEscolhaUnica)eleicao).setCampoNulo(request.getParameter(ID_REQ_IN_CAMPO_NULO_ELEICAO).equals("1"));
			((EleicaoEscolhaUnica)eleicao).setPercentualVitoria(Double.valueOf(request.getParameter(ID_REQ_PERCENTUAL_VITORIA_ELEICAO)));
		}
		else{
			((EleicaoPontuacao)eleicao).setPontuacaoMinima(Integer.valueOf(request.getParameter(ID_REQ_PONTUACAO_MINIMA_ELEICAO)));
			((EleicaoPontuacao)eleicao).setPontuacaoMaxima(Integer.valueOf(request.getParameter(ID_REQ_PONTUACAO_MAXIMA_ELEICAO)));
			((EleicaoPontuacao)eleicao).setIntervaloPontuacao(Integer.valueOf(request.getParameter(ID_REQ_INTERVALO_PONTUACAO_ELEICAO)));
		}

		if (descricaoEleicao != null && !descricaoEleicao.equals("")) {
			eleicao.setDescricao(descricaoEleicao);
		}
		eleicao.setPublica(request.getParameter(ID_REQ_IN_PUBLICA_ELEICAO).equals("1"));
		eleicao.setVisibilidadeVoto(request.getParameter(ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO).equals("1"));
		eleicao.setMultiplosVotos(request.getParameter(ID_REQ_IN_VOTO_MULTIPLO_ELEICAO).equals("1"));
		if (request.getParameter(ID_REQ_DATA_INICIO_ELEICAO) != null && !request.getParameter(ID_REQ_DATA_INICIO_ELEICAO).equals(""))
			eleicao.setDataAbertura(sdt.parse(request.getParameter(ID_REQ_DATA_INICIO_ELEICAO)));
		if (request.getParameter(ID_REQ_DATA_FIM_ELEICAO) != null && !request.getParameter(ID_REQ_DATA_FIM_ELEICAO).equals(""))
			eleicao.setDataEncerramento(sdt.parse(request.getParameter(ID_REQ_DATA_FIM_ELEICAO)));
		
		request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		
		return eleicao;
	}
	
	private void exibirExclusao(HttpServletRequest request, HttpServletResponse response) throws Exception {

		obterDadosDaChavePrimaria(request, response);

		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/exclusaoEleicao.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarExclusao(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();
		
		String idEleicao = request.getParameter(ID_REQ_CODIGO_ELEICAO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;

		TipoEleicao tipoEleicao;
		if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1"))
			tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
		else
			tipoEleicao = TipoEleicao.PONTUACAO;

		Eleicao eleicao = null;
		if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA)
			eleicao = new EleicaoEscolhaUnica();
		else
			eleicao = new EleicaoPontuacao();

		eleicao.setId(Integer.valueOf(idEleicao.trim()));

		fachada.excluirEleicao(eleicao);

		mensagem = "Elei��o Excluida com Sucesso";		

		request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		request.setAttribute(ID_REQ_TITULO_PAGINA, "Elei��o");
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void processarInicializacao(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String mensagem = "Elei��o Inicializada com Sucesso";
		obterDadosDaChavePrimaria(request, response);
		Eleicao eleicao = (Eleicao) request.getAttribute(ID_REQ_OBJETO_ELEICAO);
		eleicao.setEstado(EstadoIniciada.getInstancia());
		eleicao.setDataAbertura(new Date());
		realizarAlteracao(request, response, eleicao, mensagem);

	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void processarConclusao(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mensagem = "Elei��o Conclu�da com Sucesso";
		obterDadosDaChavePrimaria(request, response);
		Eleicao eleicao = (Eleicao) request.getAttribute(ID_REQ_OBJETO_ELEICAO);
		eleicao.setEstado(EstadoConcluida.getInstancia());
		realizarAlteracao(request, response, eleicao, mensagem);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void processarApuracao(HttpServletRequest request, HttpServletResponse response) throws Exception {
		obterDadosDaChavePrimaria(request, response);
		Eleicao eleicao = (Eleicao) request.getAttribute(ID_REQ_OBJETO_ELEICAO);
		
		Fachada.getInstancia().processarResultadoEleicao(eleicao);
		
		//RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consultaresultado.jsp");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("ServletResultado?" + ID_REQ_ID_ELEICAO + "=" + eleicao.getId() + "&" + ID_REQ_EVENTO + "=" + ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA);
		requestDispatcher.forward(request, response);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void realizarAlteracao(HttpServletRequest request, HttpServletResponse response, Eleicao pEleicao, String msg) throws Exception {
		Fachada fachada = Fachada.getInstancia();

		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;

		fachada.alterarEleicao(pEleicao);		

		request.setAttribute(ID_REQ_MENSAGEM, msg);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		request.setAttribute(ID_REQ_TITULO_PAGINA, "Elei��o");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);
	}
	/**
	 * Captura a chave prim�ria do request e monta o evento e seu respectivo tipo apartir dos dados da chave, colocando-os no request.
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void obterDadosDaChavePrimaria(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Fachada fachada = Fachada.getInstancia();
		TipoEleicao tipoEleicao = null;
		// Obt�m chave prim�ria do request
		String chavePrimaria = request.getParameter(ID_REQ_CHAVE_PRIMARIA);
		
		if (chavePrimaria != null) {
			String[] dadosEleicao = chavePrimaria.split(ID_REQ_SEPARADOR_PADRAO, 4);
			
			Eleicao eleicao = null;
			
			int idEleicao = new Integer(dadosEleicao[0]);
			String dsEleicao = dadosEleicao[1];
			String nmTpEleicao = dadosEleicao[2];
			
			if (nmTpEleicao != null && !nmTpEleicao.equals("")){
				if(nmTpEleicao.equals(EleicaoEscolhaUnica.class.getSimpleName()) ){
					eleicao = new EleicaoEscolhaUnica();
					eleicao.setId(idEleicao);
					eleicao.setDescricao(dsEleicao);
					eleicao = (EleicaoEscolhaUnica)fachada.consultarEleicaoPelaChave(eleicao);
					tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
				}else{
					eleicao = new EleicaoPontuacao();
					eleicao.setId(idEleicao);
					eleicao.setDescricao(dsEleicao);
					eleicao = (EleicaoPontuacao)fachada.consultarEleicaoPelaChave(eleicao);
					tipoEleicao = TipoEleicao.PONTUACAO;
				}

			}
			
			request.setAttribute(ID_REQ_OBJETO_ELEICAO, eleicao);
			request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		}
	}
	
	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void carregarComboUsuarios(HttpServletRequest request) throws Exception {
		Fachada fachada = Fachada.getInstancia();
		ArrayList<Usuario> arrayUsuarios = new ArrayList<Usuario>();
		
		// consulta todas as op��es de voto
		arrayUsuarios = fachada.consultarTodosUsuario();
		
		// Seta a cole��o no request
		request.setAttribute(ID_REQ_ARRAY_LIST_USUARIO, arrayUsuarios);
	}
	
	private void incluirUsuariosPorEleicao(String[] usuarios) throws Exception{
		Fachada fachada = Fachada.getInstancia();
		ArrayList<EleicaoEscolhaUnica> arrayEleicaoEU = null;
		ArrayList<EleicaoPontuacao> arrayEleicaoP = null;
		UsuarioPorEleicao usuarioPorEleicao = null;
		int idNovaEleicao = 0;
		
		arrayEleicaoEU = Fachada.getInstancia().consultarTodasEleicoes(TipoEleicao.ESCOLHA_UNICA);
		for (Iterator<EleicaoEscolhaUnica> iterator = arrayEleicaoEU.iterator(); iterator.hasNext();) {
			EleicaoEscolhaUnica eleicaoEscolhaUnica = iterator.next();
			if (eleicaoEscolhaUnica.getId() > idNovaEleicao)
				idNovaEleicao = eleicaoEscolhaUnica.getId();
		}
		
		arrayEleicaoP = Fachada.getInstancia().consultarTodasEleicoes(TipoEleicao.PONTUACAO);
		for (Iterator<EleicaoPontuacao> iterator = arrayEleicaoP.iterator(); iterator.hasNext();) {
			EleicaoPontuacao eleicaoPontuacao = iterator.next();
			if (eleicaoPontuacao.getId() > idNovaEleicao)
				idNovaEleicao = eleicaoPontuacao.getId();
		}
		
		if(usuarios != null && usuarios.length > 0){
			for (int i = 0; i < usuarios.length; i++) {
				if(usuarios[i] != null && !usuarios[i].equals("")){
					int idUsuario = Integer.valueOf(usuarios[i]).intValue();
					usuarioPorEleicao = new UsuarioPorEleicao(idNovaEleicao, idUsuario);
					fachada.incluirUsuarioPorEleicao(usuarioPorEleicao);
					System.out.println("Usu�rio de id = " + usuarios[i] + " incluido na elei��o de id = " + idNovaEleicao);
				}
			}
			
		}
	}
	
}