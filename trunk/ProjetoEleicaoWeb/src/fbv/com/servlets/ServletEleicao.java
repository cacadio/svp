package fbv.com.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.negocio.EstadoNova;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.Eleicao;
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

		String idEleicao = request.getParameter(ID_REQ_CODIGO_ELEICAO);
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
		request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO, arrayEleicao);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consultaEleicao.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		processarFiltroConsulta(request, response);

	}

	private void exibirInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1")){
			ArrayList<EleicaoEscolhaUnica> eleicoes = Fachada.getInstancia().consultarTodasEleicoes(TipoEleicao.ESCOLHA_UNICA);
			
			request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO, eleicoes);
		}
		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		request.setAttribute(ID_REQ_TIPO_ELEICAO, Integer.parseInt(request.getParameter(ID_REQ_TIPO_ELEICAO)));
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/inclusaoEleicao.jsp");
		requestDispatcher.forward(request, response);
	}

	private void processarInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
		
		//eleicao.setId(Integer.valueOf(idEleicao.trim()));

		if (descricaoEleicao != null
				&& !descricaoEleicao.equals("")) {
			eleicao.setDescricao(descricaoEleicao);
		}
		eleicao.setEstado(new EstadoNova());
		eleicao.setPublica(request.getParameter(ID_REQ_IN_PUBLICA_ELEICAO).equals("1"));
		eleicao.setVisibilidadeVoto(request.getParameter(ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO).equals("1"));
		eleicao.setMultiplosVotos(request.getParameter(ID_REQ_IN_VOTO_MULTIPLO_ELEICAO).equals("1"));
		//eleicao.setDataAbertura(sdt.parse(request.getParameter(ID_REQ_DATA_INICIO_ELEICAO)));
		eleicao.setDataAbertura(new Date(System.currentTimeMillis()));
		if (request.getParameter(ID_REQ_DATA_FIM_ELEICAO) != null && !request.getParameter(ID_REQ_DATA_FIM_ELEICAO).equals(""))
			eleicao.setDataEncerramento(sdt.parse(request.getParameter(ID_REQ_DATA_FIM_ELEICAO)));

		fachada.incluirEleicao(eleicao);
		mensagem = "Eleição Cadastrada com Sucesso";

		request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		request.setAttribute(ID_REQ_TITULO_PAGINA, "Eleição");
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Fachada fachada = Fachada.getInstancia();

		String chavePrimaria = request
				.getParameter(ServletEleicao.ID_REQ_CHAVE_PRIMARIA);

		TipoEleicao tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
		if (chavePrimaria != null) {
			
			Eleicao eleicao = null;
			
			if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1"))
				tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
			else
				tipoEleicao = TipoEleicao.PONTUACAO;

			if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA){
				eleicao = new EleicaoEscolhaUnica();
				ArrayList<EleicaoEscolhaUnica> eleicoes = Fachada.getInstancia().consultarTodasEleicoes(TipoEleicao.ESCOLHA_UNICA);
				
				request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO, eleicoes);
			}
			else
				eleicao = new EleicaoPontuacao();

			eleicao.setId(Integer.valueOf(chavePrimaria));

			if (eleicao instanceof EleicaoEscolhaUnica)
				eleicao = (EleicaoEscolhaUnica)fachada.consultarEleicaoPelaChave(eleicao);
			else
				eleicao = (EleicaoPontuacao)fachada.consultarEleicaoPelaChave(eleicao);

			request.setAttribute(
					ServletEleicao.ID_REQ_OBJETO_ELEICAO,
					eleicao); 

		}
		
		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/alteracaoEleicao.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		
		Fachada fachada = Fachada.getInstancia();
		
		String idEleicao = request.getParameter(ServletEleicao.ID_REQ_CODIGO_ELEICAO);
		
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

		if (descricaoEleicao != null
				&& !descricaoEleicao.equals("")) {
			eleicao.setDescricao(descricaoEleicao);
		}
		eleicao.setPublica(request.getParameter(ID_REQ_IN_PUBLICA_ELEICAO).equals("1"));
		eleicao.setVisibilidadeVoto(request.getParameter(ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO).equals("1"));
		eleicao.setMultiplosVotos(request.getParameter(ID_REQ_IN_VOTO_MULTIPLO_ELEICAO).equals("1"));
		if (request.getParameter(ID_REQ_DATA_INICIO_ELEICAO) != null && !request.getParameter(ID_REQ_DATA_INICIO_ELEICAO).equals(""))
			eleicao.setDataAbertura(sdt.parse(request.getParameter(ID_REQ_DATA_INICIO_ELEICAO)));
		if (request.getParameter(ID_REQ_DATA_FIM_ELEICAO) != null && !request.getParameter(ID_REQ_DATA_FIM_ELEICAO).equals(""))
			eleicao.setDataEncerramento(sdt.parse(request.getParameter(ID_REQ_DATA_FIM_ELEICAO)));

		fachada.alterarEleicao(eleicao);

		mensagem = "Eleição Alterada com Sucesso";		

		request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		request.setAttribute(ID_REQ_TITULO_PAGINA, "Eleição");
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

			Eleicao eleicao = null;
			
			TipoEleicao tipoEleicao;
			if (request.getParameter(ID_REQ_TIPO_ELEICAO).equals("1"))
				tipoEleicao = TipoEleicao.ESCOLHA_UNICA;
			else
				tipoEleicao = TipoEleicao.PONTUACAO;

			if (tipoEleicao == TipoEleicao.ESCOLHA_UNICA)
				eleicao = new EleicaoEscolhaUnica();
			else
				eleicao = new EleicaoPontuacao();

			eleicao.setId(Integer.valueOf(chavePrimaria));

			if (eleicao instanceof EleicaoEscolhaUnica)
				eleicao = (EleicaoEscolhaUnica)fachada.consultarEleicaoPelaChave(eleicao);
			else
				eleicao = (EleicaoPontuacao)fachada.consultarEleicaoPelaChave(eleicao);

			request.setAttribute(
					ID_REQ_OBJETO_ELEICAO,
					eleicao);

		}

		String nomeServlet = ID_REQ_NOME_SERVLET_ELEICAO;
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/exclusaoEleicao.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarExclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();
		
		String idEleicao = request
				.getParameter(ID_REQ_CODIGO_ELEICAO);
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

		mensagem = "Eleição Excluida com Sucesso";		

		request.setAttribute(ID_REQ_TIPO_ELEICAO, tipoEleicao.value());
		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		request.setAttribute(ID_REQ_TITULO_PAGINA, "Eleição");
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}

}