package fbv.com.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fbv.com.negocio.Fachada;
import fbv.com.negocio.PerfilUsuario;
import fbv.com.util.InterfacePrincipal;

/**
 * Servlet implementation class ServletPerfilUsuario
 */
public class ServletPerfilUsuario extends HttpServlet implements
		InterfacePrincipal {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPerfilUsuario() {
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
					.getParameter(ServletPerfilUsuario.ID_REQ_EVENTO);

			if (idEvento != null && !idEvento.equals("")) {

				if (idEvento.equals(ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
				} else if (idEvento.equals(ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_INCLUSAO)) {
					exibirInclusao(request, response);
				} else if (idEvento.equals(ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_INCLUSAO)) {
					processarInclusao(request, response);
				} else if (idEvento.equals(ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_ALTERACAO)) {
					exibirAlteracao(request, response);
				}else if(idEvento.equals(ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_ALTERACAO)){
					processarAlteracao(request,response);
				}else if(idEvento.equals(ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_EXCLUSAO)){
					exibirExclusao(request,response);
				}
				else if(idEvento.equals(ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO)){
					processarExclusao(request,response);
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

		ArrayList<PerfilUsuario> arrayPerfilUsuario = new ArrayList<PerfilUsuario>();

		Fachada fachada = Fachada.getInstancia();

		String idPerfilUsuario = request
				.getParameter(ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO);

		if (idPerfilUsuario != null && !idPerfilUsuario.equals("")) {

			PerfilUsuario perfilUsuario = new PerfilUsuario();
			perfilUsuario.setId(Integer.valueOf(idPerfilUsuario));

			perfilUsuario = fachada
					.consultarPerfilUsuarioPelaChave(perfilUsuario);

			if (perfilUsuario != null) {
				arrayPerfilUsuario.add(perfilUsuario);
			}

		} else {
			arrayPerfilUsuario = fachada.consultarTodosPerfilUsuario();
		}
		request.setAttribute(ID_REQ_ARRAY_LIST_PERFIL_USUARIO,
				arrayPerfilUsuario);

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/consultaperfilusuario.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/inclusaoperfilusuario.jsp");
		requestDispatcher.forward(request, response);
	}

	private void processarInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Fachada fachada = Fachada.getInstancia();
		PerfilUsuario perfilUsuarioBase = null;
		String idPerfilUsuario = request
				.getParameter(ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_PERFIL_USUARIO;
		String descricaoPerfilUsuario = request
				.getParameter(ServletPerfilUsuario.ID_REQ_DESCRICAO_PERFIL_USUARIO);

		PerfilUsuario perfilUsuario = new PerfilUsuario();
		perfilUsuario.setId(Integer.valueOf(idPerfilUsuario.trim()));

		if (descricaoPerfilUsuario != null
				&& !descricaoPerfilUsuario.equals("")) {
			perfilUsuario.setDescricao(descricaoPerfilUsuario);
		}

		perfilUsuarioBase = fachada
				.consultarPerfilUsuarioPelaChave(perfilUsuario);

		// já existe registro cadastrado com esse código
		if (perfilUsuarioBase != null) {
			mensagem = "Perfil de Usuário já Cadastrado!";
		} else {
			fachada.incluirPerfilUsuario(perfilUsuario);
			mensagem = "Perfil de Usuário Cadastrado com Sucesso";
		}

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
				.getParameter(ServletPerfilUsuario.ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {

			PerfilUsuario perfilUsuario = new PerfilUsuario();
			perfilUsuario.setId(Integer.valueOf(chavePrimaria));

			perfilUsuario = fachada
					.consultarPerfilUsuarioPelaChave(perfilUsuario);

			request.setAttribute(
					ServletPerfilUsuario.ID_REQ_OBJETO_PERFIL_USUARIO,
					perfilUsuario);

		}

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/alteracaoperfilusuario.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();
		
		String idPerfilUsuario = request
				.getParameter(ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_PERFIL_USUARIO;
		String descricaoPerfilUsuario = request
				.getParameter(ServletPerfilUsuario.ID_REQ_DESCRICAO_PERFIL_USUARIO);

		PerfilUsuario perfilUsuario = new PerfilUsuario();
		perfilUsuario.setId(Integer.valueOf(idPerfilUsuario.trim()));

		if (descricaoPerfilUsuario != null
				&& !descricaoPerfilUsuario.equals("")) {
			perfilUsuario.setDescricao(descricaoPerfilUsuario);
		}

		fachada.alterarPerfilUsuario(perfilUsuario);

		mensagem = "Perfil de Usuário Alterado com Sucesso";
		

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
				.getParameter(ServletPerfilUsuario.ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {

			PerfilUsuario perfilUsuario = new PerfilUsuario();
			perfilUsuario.setId(Integer.valueOf(chavePrimaria));

			perfilUsuario = fachada
					.consultarPerfilUsuarioPelaChave(perfilUsuario);

			request.setAttribute(
					ServletPerfilUsuario.ID_REQ_OBJETO_PERFIL_USUARIO,
					perfilUsuario);

		}

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/exclusaoperfilusuario.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarExclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();
		
		String idPerfilUsuario = request
				.getParameter(ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_PERFIL_USUARIO;
		String descricaoPerfilUsuario = request
				.getParameter(ServletPerfilUsuario.ID_REQ_DESCRICAO_PERFIL_USUARIO);

		PerfilUsuario perfilUsuario = new PerfilUsuario();
		perfilUsuario.setId(Integer.valueOf(idPerfilUsuario.trim()));

		if (descricaoPerfilUsuario != null
				&& !descricaoPerfilUsuario.equals("")) {
			perfilUsuario.setDescricao(descricaoPerfilUsuario);
		}

		fachada.excluirPerfilUsuario(perfilUsuario);

		mensagem = "Perfil de Usuário Excluido com Sucesso";
		

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}
}
