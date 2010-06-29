package fbv.com.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.Eleicao;
import fbv.com.util.InterfacePrincipal;
import fbv.com.util.TipoEleicao;

public class ServletEleicao extends HttpServlet implements InterfacePrincipal {

	private static final long serialVersionUID = 1L;
	//identificadores caso de uso Perfil de usuário
	public static final String ID_REQ_ARRAY_LIST_ELEICAO = "arrayListEleicao";
	public static final String ID_REQ_TIPO_ELEICAO = "tipoEleicao";
	public static final String ID_REQ_CODIGO_ELEICAO = "codigoEleicao";
	public static final String ID_REQ_DESCRICAO_ELEICAO = "descricaoEleicao";
	public static final String ID_REQ_IN_PUBLICA_ELEICAO = "inPublicaEleicao";
	public static final String ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO = "inVisibilidadeAbertaEleicao";
	public static final String ID_REQ_IN_VOTO_MULTIPLO_ELEICAO = "inVotoMultiploEleicao";
	public static final String ID_REQ_DATA_INICIO_ELEICAO = "dataInicioEleicao";
	public static final String ID_REQ_DATA_FIM_ELEICAO = "dataFimEleicao";
	/*
	 * Escolha Única
	 */
	public static final String ID_REQ_IN_CAMPO_NULO_ELEICAO = "inCampoNuloEleicao";
	public static final String ID_REQ_PERCENTUAL_VITORIA_ELEICAO = "percentualVitoriaEleicao";
	/*
	 * Pontuação
	 */
	public static final String ID_REQ_PONTUACAO_MINIMA_ELEICAO = "pontuacaoMinimaEleicao";
	public static final String ID_REQ_PONTUACAO_MAXIMA_ELEICAO = "pontuacaoMaximaEleicao";
	public static final String ID_REQ_INTERVALO_PONTUACAO_ELEICAO = "intervaloPontuacaoEleicao";
	
	public static final String ID_REQ_NOME_SERVLET_ELEICAO = "Eleicao";
	public static final String ID_REQ_OBJETO_ELEICAO = "objetoEleicao";
	
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

				if (idEvento
						.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
				} else if (idEvento
						.equals(ServletEleicao.ID_REQ_EVENTO_EXIBIR_INCLUSAO)) {
					exibirInclusao(request, response);
				} else if (idEvento
						.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_INCLUSAO)) {
					processarInclusao(request, response);
				} else if (idEvento
						.equals(ServletEleicao.ID_REQ_EVENTO_EXIBIR_ALTERACAO)) {
					exibirAlteracao(request, response);
				}else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_ALTERACAO)){
					processarAlteracao(request,response);
				}else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_EXIBIR_EXCLUSAO)){
					exibirExclusao(request,response);
				}
				else if(idEvento.equals(ServletEleicao.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO)){
					processarExclusao(request,response);
				}

			} else {
				exibirFiltroConsulta(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processarFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ArrayList<Eleicao> arrayEleicao = new ArrayList<Eleicao>();

		Fachada fachada = Fachada.getInstancia();

		String idEleicao = request
				.getParameter(ID_REQ_CODIGO_ELEICAO);
		TipoEleicao tipoEleicao;
		if (request.getParameter(ID_REQ_TIPO_ELEICAO) != null){
			if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1"))
				tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
			else
				tipoEleicao = TipoEleicao.PONTUACAO;
		}
		else
			tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
		
		if (idEleicao != null && !idEleicao.equals("")) {
			Eleicao eleicao = null;
			if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA)
				eleicao = new EleicaoEscolhaUnica();
			else
				eleicao = new EleicaoPontuacao();
			
			eleicao.setId(Integer.valueOf(idEleicao));

			eleicao = fachada
					.consultarEleicaoPelaChave(eleicao);

			if (eleicao != null) {
				arrayEleicao.add(eleicao);
			}

		} else {
			arrayEleicao = fachada.consultarTodasEleicoes(tipoEleicao);
		}
		request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO,
				arrayEleicao);

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/consultaEleicao.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		processarFiltroConsulta(request, response);

	}

	private void exibirInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/inclusaoEleicao.jsp");
		requestDispatcher.forward(request, response);
	}

	private void processarInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		
		Fachada fachada = Fachada.getInstancia();
		//Eleicao EleicaoBase = null;
		//String idEleicao = request
				//.getParameter(ServletEleicao.ID_REQ_CODIGO_ELEICAO);
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
			eleicaoEU.setCampoNulo(request.getParameter(ID_REQ_IN_CAMPO_NULO_ELEICAO) != null);
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
		
		//eleicao.setId(Integer.valueOf(idEleicao.trim()));

		if (descricaoEleicao != null
				&& !descricaoEleicao.equals("")) {
			eleicao.setDescricao(descricaoEleicao);
		}
		eleicao.setEstado(1);
		eleicao.setPublica(request.getParameter(ID_REQ_IN_PUBLICA_ELEICAO) != null);
		eleicao.setVisibilidadeVoto(request.getParameter(ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO) != null);
		eleicao.setMultiplosVotos(request.getParameter(ID_REQ_IN_VOTO_MULTIPLO_ELEICAO) != null);
		eleicao.setDataAbertura(sdt.parse(request.getParameter(ID_REQ_DATA_INICIO_ELEICAO)));
		eleicao.setDataEncerramento(sdt.parse(request.getParameter(ID_REQ_DATA_FIM_ELEICAO)));

		fachada.incluirEleicao(eleicao);
		mensagem = "Eleição Cadastrada com Sucesso";

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Fachada fachada = Fachada.getInstancia();

		String chavePrimaria = request
				.getParameter(ServletEleicao.ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {

			Eleicao eleicao = new Eleicao();
			eleicao.setId(Integer.valueOf(chavePrimaria));

			eleicao = fachada
					.consultarEleicaoPelaChave(eleicao);

			request.setAttribute(
					ServletEleicao.ID_REQ_OBJETO_ELEICAO,
					eleicao);

		}

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/alteracaoeleicao.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();
		
		String idEleicao = request
				.getParameter(ID_REQ_CODIGO_ELEICAO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		String descricaoEleicao = request
				.getParameter(ID_REQ_DESCRICAO_ELEICAO);

		TipoEleicao tipoEleicao;
		if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1"))
			tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
		else
			tipoEleicao = TipoEleicao.PONTUACAO;

		Eleicao eleicao = null;
		
		if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA){
			EleicaoEscolhaUnica eleicaoEU = new EleicaoEscolhaUnica();
			eleicaoEU.setCampoNulo(request.getParameter(ID_REQ_IN_CAMPO_NULO_ELEICAO) != null);
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
		
		eleicao.setId(Integer.valueOf(idEleicao.trim()));

		if (descricaoEleicao != null
				&& !descricaoEleicao.equals("")) {
			eleicao.setDescricao(descricaoEleicao);
		}
		eleicao.setPublica(request.getParameter(ID_REQ_IN_PUBLICA_ELEICAO) != null);
		eleicao.setVisibilidadeVoto(request.getParameter(ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO) != null);
		eleicao.setMultiplosVotos(request.getParameter(ID_REQ_IN_VOTO_MULTIPLO_ELEICAO) != null);

		fachada.alterarEleicao(eleicao);

		mensagem = "Eleição Alterado com Sucesso";		

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void exibirExclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Fachada fachada = Fachada.getInstancia();

		String chavePrimaria = request
				.getParameter(ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {

			Eleicao eleicao = new Eleicao();
			eleicao.setId(Integer.valueOf(chavePrimaria));

			eleicao = fachada
					.consultarEleicaoPelaChave(eleicao);

			request.setAttribute(
					ID_REQ_OBJETO_ELEICAO,
					eleicao);

		}

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/exclusaoeleicao.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarExclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();
		
		String idEleicao = request
				.getParameter(ID_REQ_CODIGO_ELEICAO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		String descricaoEleicao = request
				.getParameter(ID_REQ_DESCRICAO_ELEICAO);

		Eleicao eleicao = new Eleicao();
		eleicao.setId(Integer.valueOf(idEleicao.trim()));

		if (descricaoEleicao != null
				&& !descricaoEleicao.equals("")) {
			eleicao.setDescricao(descricaoEleicao);
		}

		fachada.excluirEleicao(eleicao);

		mensagem = "Eleição Excluido com Sucesso";		

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}

}
