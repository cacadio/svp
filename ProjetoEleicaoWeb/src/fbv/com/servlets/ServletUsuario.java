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
import fbv.com.negocio.Usuario;
import fbv.com.util.InterfacePrincipal;

/**
 * Servlet implementation class for Servlet: ServletUsuario
 *
 */
 public class ServletUsuario extends HttpServlet implements
	InterfacePrincipal {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ServletUsuario() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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

				if (idEvento
						.equals(ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
				} else if (idEvento
						.equals(ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_INCLUSAO)) {
					exibirInclusao(request, response);
				} else if (idEvento
						.equals(ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_INCLUSAO)) {
					processarInclusao(request, response);
				} else if (idEvento
						.equals(ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_ALTERACAO)) {
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
				exibirFiltroConsulta(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processarFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ArrayList<Usuario> arrayUsuario = new ArrayList<Usuario>();

		Fachada fachada = Fachada.getInstancia();
		
		String cpfUsuario = request.getParameter(ServletUsuario.ID_REQ_CPF_USUARIO);

		if (cpfUsuario != null && !cpfUsuario.equals("")) {

			Usuario usuario = new Usuario();
			usuario.setCpf(cpfUsuario);

			usuario = fachada
					.consultarUsuarioPelaChave(usuario);

			if (usuario != null) {
				
				PerfilUsuario perfil = new PerfilUsuario();
				perfil = fachada
						.consultarPerfilUsuarioPelaChave(usuario.getPerfilUsuario());
				
				usuario.setPerfilUsuario(perfil);
				
				arrayUsuario.add(usuario);
			}

		} else {
			arrayUsuario = fachada.consultarTodosUsuario();
		}
		request.setAttribute(ID_REQ_ARRAY_LIST_USUARIO,
				arrayUsuario);

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/consulta_usuario.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/consulta_usuario.jsp");
		requestDispatcher.forward(request, response);
	}

	private void exibirInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();

		ArrayList<PerfilUsuario> arrayPerfilUsuario = new ArrayList<PerfilUsuario>();
		
		arrayPerfilUsuario = fachada.consultarTodosPerfilUsuario();
		
		request.setAttribute(ID_REQ_OBJETO_PERFIL_USUARIO, arrayPerfilUsuario);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/inclusao_usuario.jsp");
		requestDispatcher.forward(request, response);
	}

	private void processarInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Fachada fachada = Fachada.getInstancia();
		Usuario usuarioBase = null;
		String mensagem = "";
		String cpfUsuario = "";
		String nomeUsuario = "";
		String loginUsuario = "";
		String senhaUsuario = "";
		String perfilUsuario = "";
		String nomeServlet = "";
		
		nomeServlet = ID_REQ_NOME_SERVLET_USUARIO;
		cpfUsuario = request.getParameter(ServletUsuario.ID_REQ_CPF_USUARIO);
		nomeUsuario = request.getParameter(ServletUsuario.ID_REQ_NOME_USUARIO);
		loginUsuario = request.getParameter(ServletUsuario.ID_REQ_LOGIN_USUARIO);
		senhaUsuario = request.getParameter(ServletUsuario.ID_REQ_SENHA_USUARIO);
		perfilUsuario = request.getParameter(ServletUsuario.ID_REQ_ID_PERFIL_USUARIO);
		

		Usuario usuario = new Usuario();
		usuario.setCpf(cpfUsuario); 
		usuario.setNome(nomeUsuario);
		usuario.setLogin(loginUsuario);
		usuario.setSenha(senhaUsuario);
		
		PerfilUsuario objperfilUsuario = new PerfilUsuario();
		objperfilUsuario.setId(Integer.valueOf(perfilUsuario.trim()));
		
		usuario.setPerfilUsuario(objperfilUsuario);

		usuarioBase = fachada.consultarUsuarioPelaChave(usuario);

		// já existe registro cadastrado com esse código
		if (usuarioBase != null) {
			mensagem = "Usuário já Cadastrado!";
		} else {
			fachada.incluirUsuario(usuario);
			mensagem = "Usuário Cadastrado com Sucesso";
		}

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Fachada fachada = Fachada.getInstancia();
		ArrayList<PerfilUsuario> arrayPerfilUsuario = new ArrayList<PerfilUsuario>();
		
		String chavePrimaria = request
				.getParameter(ServletUsuario.ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {

			Usuario usuario = new Usuario();
			usuario.setCpf(chavePrimaria);

			usuario = fachada.consultarUsuarioPelaChave(usuario);
			
			if (usuario != null) {
				
				PerfilUsuario perfil = new PerfilUsuario();
				perfil = fachada
						.consultarPerfilUsuarioPelaChave(usuario.getPerfilUsuario());
				
				usuario.setPerfilUsuario(perfil);
				
			}
			
			request.setAttribute(ServletUsuario.ID_REQ_OBJETO_USUARIO,usuario);

		}
		
		arrayPerfilUsuario = fachada.consultarTodosPerfilUsuario();
		
		request.setAttribute(ID_REQ_OBJETO_PERFIL_USUARIO, arrayPerfilUsuario);

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("jsp/alteracao_usuario.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarAlteracao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();
		String cpfUsuario = "";
		String nomeUsuario = "";
		String loginUsuario = "";
		String senhaUsuario = "";
		String perfilUsuario = "";
		String nomeServlet = "";
		String mensagem = "";
		Usuario usuario = null;
		Usuario usuarioAntigo = null;
		
		nomeServlet = ID_REQ_NOME_SERVLET_USUARIO;
		cpfUsuario = request.getParameter(ServletUsuario.ID_REQ_CPF_USUARIO);
		nomeUsuario = request.getParameter(ServletUsuario.ID_REQ_NOME_USUARIO);
		loginUsuario = request.getParameter(ServletUsuario.ID_REQ_LOGIN_USUARIO);
		senhaUsuario = request.getParameter(ServletUsuario.ID_REQ_SENHA_USUARIO);
		perfilUsuario = request.getParameter(ServletUsuario.ID_REQ_ID_PERFIL_USUARIO);
		
		PerfilUsuario perfil = null;
		perfil = new PerfilUsuario();
		perfil.setId(Integer.valueOf(perfilUsuario.trim()));
		
		usuarioAntigo = new Usuario();
		usuarioAntigo.setCpf(cpfUsuario);
		usuarioAntigo = fachada.consultarUsuarioPelaChave(usuarioAntigo);
		
		usuario = new Usuario();
		usuario.setCpf(cpfUsuario); 
		usuario.setNome(nomeUsuario);
		usuario.setLogin(loginUsuario);
		usuario.setSenha(senhaUsuario);
		usuario.setId(usuarioAntigo.getId());
		usuario.setPerfilUsuario(perfil);
		
		

		fachada.alterarUsuario(usuario);

		mensagem = "Usuário Alterado com Sucesso";
		

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void exibirExclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Fachada fachada = Fachada.getInstancia();
		Usuario usuario = null;

		ArrayList<PerfilUsuario> arrayPerfilUsuario = new ArrayList<PerfilUsuario>();
		
		String chavePrimaria = request.getParameter(ServletUsuario.ID_REQ_CHAVE_PRIMARIA);
		
		if (chavePrimaria != null) {

			usuario = new Usuario();
			usuario.setCpf(chavePrimaria);

			usuario = fachada.consultarUsuarioPelaChave(usuario);
			
			if (usuario != null) {
				
				PerfilUsuario perfil = new PerfilUsuario();
				perfil = fachada
						.consultarPerfilUsuarioPelaChave(usuario.getPerfilUsuario());
				
				usuario.setPerfilUsuario(perfil);
				
			}

			request.setAttribute(ServletUsuario.ID_REQ_OBJETO_USUARIO,usuario);
		}
		
		arrayPerfilUsuario = fachada.consultarTodosPerfilUsuario();
		
		request.setAttribute(ID_REQ_OBJETO_PERFIL_USUARIO, arrayPerfilUsuario);
		

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/exclusao_usuario.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void processarExclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Fachada fachada = Fachada.getInstancia();
		String cpfUsuario = "";
		String nomeUsuario = "";
		String loginUsuario = "";
		String senhaUsuario = "";
		String nomeServlet = "";
		String mensagem = "";
		
		nomeServlet = ID_REQ_NOME_SERVLET_USUARIO;
		cpfUsuario = request.getParameter(ServletUsuario.ID_REQ_CPF_USUARIO);
		nomeUsuario = request.getParameter(ServletUsuario.ID_REQ_NOME_USUARIO);
		loginUsuario = request.getParameter(ServletUsuario.ID_REQ_LOGIN_USUARIO);
		senhaUsuario = request.getParameter(ServletUsuario.ID_REQ_SENHA_USUARIO);		

		Usuario usuario = new Usuario();
		usuario.setCpf(cpfUsuario); 
		usuario.setNome(nomeUsuario);
		usuario.setLogin(loginUsuario);
		usuario.setSenha(senhaUsuario);

		fachada.excluirUsuario(usuario);

		mensagem = "Usuário Excluido com Sucesso";

		request.setAttribute(ID_REQ_MENSAGEM, mensagem);
		request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
		requestDispatcher.forward(request, response);

	}
}