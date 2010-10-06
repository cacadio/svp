package fbv.com.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.ResultadoEleicao;
import fbv.com.util.InterfacePrincipal;
import fbv.com.util.TipoEleicao;

/**
 * Servlet implementation class ServletResultado
 */
public class ServletResultado extends HttpServlet implements InterfacePrincipal{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletResultado() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			String idEvento = request
					.getParameter(ServletEleicao.ID_REQ_EVENTO);

			if (idEvento != null && !idEvento.equals("")) {

				if (idEvento.equals(ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
				}
			} else {
				if ((request.getParameter(ID_REQ_ID_ELEICAO) != null) && (!
						request.getParameter(ID_REQ_ID_ELEICAO).equals("")))
					processarFiltroConsulta(request, response);
				else
					prepararTelaConsulta(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void prepararTelaConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO, listarEleicoesApuradas());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consultaresultado.jsp");
		requestDispatcher.forward(request, response);
		
	}

	private void processarFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ResultadoEleicao resultado = null;
		
		Fachada fachada = Fachada.getInstancia();

		String idEleicao = request.getParameter(ID_REQ_ID_ELEICAO);
		
		if (idEleicao != null && !idEleicao.equals("")) {
			Object eleicao = fachada.consultarEleicaoPelaChave(new Eleicao(Integer.parseInt(idEleicao)));
			resultado = fachada.consultarResultadoEleicao((Eleicao)eleicao);
		}
		request.setAttribute(ID_REQ_RESULTADO, resultado);

		request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO, listarEleicoesApuradas());
		request.setAttribute(ID_REQ_ID_ELEICAO, idEleicao);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consultaresultado.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private ArrayList<Eleicao> listarEleicoesApuradas(){
		ArrayList<Eleicao> arrayEleicao = new ArrayList<Eleicao>();
		ArrayList<EleicaoEscolhaUnica> arrayEleicaoEU = null;
		ArrayList<EleicaoPontuacao> arrayEleicaoP = null;
		
		try {
			arrayEleicaoEU = Fachada.getInstancia().consultarTodasEleicoes(TipoEleicao.ESCOLHA_UNICA);
			arrayEleicaoP = Fachada.getInstancia().consultarTodasEleicoes(TipoEleicao.PONTUACAO);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ExcecaoRegistroNaoExistente e) {
			e.printStackTrace();
		} catch (ExcecaoAcessoRepositorio e) {
			e.printStackTrace();
		}
		
		if (arrayEleicaoEU != null)
			for(Eleicao eleicao : arrayEleicaoEU){
				if (eleicao.getEstado().equals(Eleicao.APURADA)){
					arrayEleicao.add(eleicao);
				}
			}
		
		if (arrayEleicaoP != null)
			for(Eleicao eleicao : arrayEleicaoP){
				if (eleicao.getEstado().equals(Eleicao.APURADA)){
					arrayEleicao.add(eleicao);
				}
			}
		
		return arrayEleicao;
		
	}
	
}
