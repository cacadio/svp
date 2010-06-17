package fbv.com.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fbv.com.negocio.Fachada;
import fbv.com.negocio.OpcaoVoto;
import fbv.com.util.InterfacePrincipal;

/**
 * Servlet implementation class ServletOpcaoVoto
 */
public class ServletOpcaoVoto extends HttpServlet implements
		InterfacePrincipal {
	private static final long serialVersionUID = 1L;

	public ServletOpcaoVoto() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			String idEvento = request.getParameter(ServletOpcaoVoto.ID_REQ_EVENTO);

			if (idEvento != null && !idEvento.equals("")) {
				if (idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
					
				} else if (idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_EXIBIR_INCLUSAO)) {
					exibirInclusao(request, response);
					
				} else if (idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_PROCESSAR_INCLUSAO)) {
					processarInclusao(request, response);
					
				} else if (idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_EXIBIR_ALTERACAO)) {
					exibirAlteracao(request, response);
					
				} else if(idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_PROCESSAR_ALTERACAO)) {
					processarAlteracao(request,response);
					
				} else if(idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_EXIBIR_EXCLUSAO)) {
					exibirExclusao(request,response);
					
				} else if(idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO)) {
					processarExclusao(request,response);
				}
			} else {
				exibirFiltroConsulta(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processarFiltroConsulta(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		ArrayList<OpcaoVoto> arrayOpcaoVoto = new ArrayList<OpcaoVoto>();
		Fachada fachada = Fachada.getInstancia();

		String idOpcaoVoto = request.getParameter(ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO);

		if ((idOpcaoVoto != null) && (!idOpcaoVoto.equals(""))) {
			OpcaoVoto opcaoVoto = new OpcaoVoto();
			opcaoVoto.setId(Integer.valueOf(idOpcaoVoto));

			opcaoVoto = fachada.consultarOpcaoVotoPelaChave(opcaoVoto);

			if (opcaoVoto != null) {
				arrayOpcaoVoto.add(opcaoVoto);
			}
		} else {
			arrayOpcaoVoto = fachada.consultarTodosOpcaoVoto();
		}
		
		request.setAttribute(ID_REQ_ARRAY_LIST_OPCAO_VOTO, arrayOpcaoVoto);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consulta_opcao_voto.jsp");
		requestDispatcher.forward(request, response);
	}

	private void exibirFiltroConsulta(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consulta_opcao_voto.jsp");
		requestDispatcher.forward(request, response);
	}

	private void exibirInclusao(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/inclusao_opcao_voto.jsp");
		requestDispatcher.forward(request, response);
	}

	private void processarInclusao(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Fachada fachada = Fachada.getInstancia();
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_OPCAO_VOTO;
		String descricaoOpcaoVoto = request.getParameter(ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO);
		String cdEleicao = request.getParameter(ID_REQ_CODIGO_ELEICAO);
		String pathFoto = request.getParameter(ID_REQ_PATH_FOTO);
		OpcaoVoto opcaoVoto = new OpcaoVoto();

		if ((descricaoOpcaoVoto != null) && (!descricaoOpcaoVoto.equals(""))) {
			opcaoVoto.setDescricao(descricaoOpcaoVoto);
		}
		
		if ((cdEleicao != null) && (!cdEleicao.equals(""))) {
			opcaoVoto.setIdEleicao(Integer.valueOf(cdEleicao.trim()));
		}
		
		if ((pathFoto != null) && (!pathFoto.equals(""))) {
			opcaoVoto.setPath_foto(pathFoto);
		}

		fachada.incluirOpcaoVoto(opcaoVoto);
		mensagem = "Opção de Voto Cadastrada com Sucesso";

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirAlteracao(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Fachada fachada = Fachada.getInstancia();

		String chavePrimaria = request.getParameter(ServletOpcaoVoto.ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {
			OpcaoVoto opcaoVoto = new OpcaoVoto();
			opcaoVoto.setId(Integer.valueOf(chavePrimaria));

			opcaoVoto = fachada.consultarOpcaoVotoPelaChave(opcaoVoto);

			request.setAttribute(ServletOpcaoVoto.ID_REQ_OBJETO_OPCAO_VOTO, opcaoVoto);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/alteracao_opcao_voto.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void processarAlteracao(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Fachada fachada = Fachada.getInstancia();
		
		String idOpcaoVoto = request.getParameter(ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_OPCAO_VOTO;
		String descricaoOpcaoVoto = request.getParameter(ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO);
		String cdEleicao = request.getParameter(ID_REQ_CODIGO_ELEICAO);
		String pathFoto = request.getParameter(ID_REQ_PATH_FOTO);

		OpcaoVoto opcaoVoto = new OpcaoVoto();
		opcaoVoto.setId(Integer.valueOf(idOpcaoVoto.trim()));

		if ((descricaoOpcaoVoto != null) && (!descricaoOpcaoVoto.equals(""))) {
			opcaoVoto.setDescricao(descricaoOpcaoVoto);
		}
		
		if ((cdEleicao != null) && (!cdEleicao.equals(""))) {
			opcaoVoto.setIdEleicao(Integer.valueOf(cdEleicao.trim()));
		}
		
		if ((pathFoto != null) && (!pathFoto.equals(""))) {
			opcaoVoto.setPath_foto(pathFoto);
		}

		fachada.alterarOpcaoVoto(opcaoVoto);

		mensagem = "Opção de Voto Alterada com Sucesso";

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void exibirExclusao(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Fachada fachada = Fachada.getInstancia();

		String chavePrimaria = request.getParameter(ServletOpcaoVoto.ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {
			OpcaoVoto opcaoVoto = new OpcaoVoto();
			opcaoVoto.setId(Integer.valueOf(chavePrimaria));

			opcaoVoto = fachada.consultarOpcaoVotoPelaChave(opcaoVoto);

			request.setAttribute(ServletOpcaoVoto.ID_REQ_OBJETO_OPCAO_VOTO, opcaoVoto);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/exclusao_opcao_voto.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void processarExclusao(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Fachada fachada = Fachada.getInstancia();
		
		String idOpcaoVoto = request.getParameter(ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_OPCAO_VOTO;
		String descricaoOpcaoVoto = request.getParameter(ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO);

		OpcaoVoto opcaoVoto = new OpcaoVoto();
		opcaoVoto.setId(Integer.valueOf(idOpcaoVoto.trim()));

		if ((descricaoOpcaoVoto != null) && (!descricaoOpcaoVoto.equals(""))) {
			opcaoVoto.setDescricao(descricaoOpcaoVoto);
		}

		fachada.excluirOpcaoVoto(opcaoVoto);

		mensagem = "Opção de Voto Excluida com Sucesso";

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);
	}
}
