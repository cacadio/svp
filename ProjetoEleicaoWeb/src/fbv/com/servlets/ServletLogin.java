package fbv.com.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fbv.com.negocio.Fachada;
import fbv.com.negocio.Usuario;
import fbv.com.util.InterfacePrincipal;

/**
 * Servlet implementation class ServletPerfilUsuario
 */
public class ServletLogin extends HttpServlet implements
		InterfacePrincipal {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletLogin() {
		super();
		// TODO Auto-generated constructor stub
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
					.getParameter(ServletLogin.ID_REQ_EVENTO);

			if (idEvento != null && !idEvento.equals("")) {

				if (idEvento
						.equals(ServletLogin.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
				} else if (idEvento
						.equals(ServletLogin.ID_REQ_EVENTO_EXIBIR_INCLUSAO)) {
					exibirInclusao(request, response);
				} else if (idEvento
						.equals(ServletLogin.ID_REQ_EVENTO_PROCESSAR_INCLUSAO)) {
					processarInclusao(request, response);
				} else if (idEvento
						.equals(ServletLogin.ID_REQ_EVENTO_EXIBIR_ALTERACAO)) {
					exibirAlteracao(request, response);
				}else if(idEvento.equals(ServletLogin.ID_REQ_EVENTO_PROCESSAR_ALTERACAO)){
					processarAlteracao(request,response);
				}else if(idEvento.equals(ServletLogin.ID_REQ_EVENTO_EXIBIR_EXCLUSAO)){
					exibirExclusao(request,response);
				}
				else if(idEvento.equals(ServletLogin.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO)){
					processarExclusao(request,response);
				}

			} else {
				exibirFiltroConsulta(request, response);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processarFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ArrayList<Usuario> arrayUsuario = new ArrayList<Usuario>();
		Usuario usuario = null;
		String idEleicao = "";
		String login = "";
		String senha = "";
		String tpEleicao = "";
		
		login = request.getParameter(ServletLogin.ID_REQ_LOGIN);
		senha = request.getParameter(ServletLogin.ID_REQ_SENHA_USUARIO);
		tpEleicao = request.getParameter(ID_REQ_TIPO_DE_ELEICAO);
		idEleicao = request.getParameter(ID_REQ_ID_ELEICAO);

		Fachada fachada = Fachada.getInstancia();
		arrayUsuario = fachada.consultarTodosUsuario();
		usuario = validarLoginUsuario(arrayUsuario, login, senha);
		if(usuario != null){
			request.getSession(true).setAttribute("usuario", usuario);
			request.getSession(true).setAttribute("idEleicao", idEleicao);
			request.getSession(true).setAttribute("tpEleicao", tpEleicao);
			
			request.setAttribute(ID_REQ_NOME_USUARIO, usuario.getNome());
			request.setAttribute(ID_REQ_ID_USUARIO, usuario.getId());
			request.setAttribute(ID_REQ_TIPO_DE_ELEICAO, tpEleicao);
			request.setAttribute(ID_REQ_ID_ELEICAO, idEleicao);
			request.setAttribute(ID_REQ_EVENTO, ID_REQ_EVENTO_EXIBIR_INCLUSAO);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ServletVoto");
			requestDispatcher.forward(request, response);
			
		}else{
			
			request.getSession(true).setAttribute("usuario", usuario);
			request.setAttribute(ID_REQ_MENSAGEM, "Erro ao efetuar o login");
			request.setAttribute(ID_REQ_NOME_SERVLET, ID_REQ_NOME_SERVLET_LOGIN);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
			requestDispatcher.forward(request, response);
		}

	}
	
	private Usuario validarLoginUsuario(ArrayList<Usuario> arrUsuario, String login, String senha){
		Usuario usuario = null;
		for(Usuario usu : arrUsuario){
			if(usu.getLogin().equals(login)){
				if(usu.getSenha().equals(senha)){
					usuario = usu;
					break;
				}
			}
		}
		
		return usuario;
	}

	private void exibirFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/login.jsp");
		requestDispatcher.forward(request, response);
	}

	private void exibirInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	private void processarInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	private void exibirAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
	
	private void processarAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
	
	private void exibirExclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
	
	private void processarExclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
}
