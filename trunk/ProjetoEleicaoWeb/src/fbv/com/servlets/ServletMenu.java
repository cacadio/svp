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
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.PerfilUsuario;
import fbv.com.negocio.Usuario;
import fbv.com.util.InterfacePrincipal;
import fbv.com.util.TipoEleicao;

/**
 * Servlet implementation class ServletPerfilUsuario
 */
public class ServletMenu extends HttpServlet implements
		InterfacePrincipal {
	private static final long serialVersionUID = 1L;
	private Fachada fachada = null;
	

	/**
	 * @throws SQLException 
	 * @throws ExcecaoAcessoRepositorio 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletMenu() throws ExcecaoAcessoRepositorio, SQLException {
		super();
		fachada = Fachada.getInstancia();
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

			String idEvento = request.getParameter(ServletMenu.ID_REQ_EVENTO);

			if (idEvento != null && !idEvento.equals("")) {

				if (idEvento.equals(ServletMenu.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
				}else if (idEvento.equals(ServletMenu.ID_REQ_EVENTO_EXIBIR_INCLUSAO)) {
					exibirInclusao(request, response);
				}else if (idEvento.equals(ServletMenu.ID_REQ_EVENTO_PROCESSAR_INCLUSAO)) {
					processarInclusao(request, response);
				}else if (idEvento.equals(ServletMenu.ID_REQ_EVENTO_EXIBIR_ALTERACAO)) {
					exibirAlteracao(request, response);
				}else if(idEvento.equals(ServletMenu.ID_REQ_EVENTO_PROCESSAR_ALTERACAO)){
					processarAlteracao(request,response);
				}else if(idEvento.equals(ServletMenu.ID_REQ_EVENTO_EXIBIR_EXCLUSAO)){
					exibirExclusao(request,response);
				}else if(idEvento.equals(ServletMenu.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO)){
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

	private void exibirFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ArrayList<Eleicao> arrayEleicaoPontuacao = new ArrayList<Eleicao>();
		ArrayList<Eleicao> arrayEleicaoUnica = new ArrayList<Eleicao>();
		ArrayList<Eleicao> arrayEleicaoPublica = new ArrayList<Eleicao>();
		ArrayList<Eleicao> arrayEleicaoPrivada = new ArrayList<Eleicao>();

		Fachada fachada = Fachada.getInstancia();
		arrayEleicaoPontuacao = fachada.consultarTodasEleicoes(TipoEleicao.PONTUACAO);
		arrayEleicaoUnica = fachada.consultarTodasEleicoes(TipoEleicao.ESCOLHA_UNICA);
		
		separarEleicoesPublicaPrivada(arrayEleicaoPontuacao, arrayEleicaoUnica, 
				arrayEleicaoPrivada, arrayEleicaoPublica);

		request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO_PRIVADA, arrayEleicaoPrivada);
		request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO_PUBLICA, arrayEleicaoPublica);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/principal.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void separarEleicoesPublicaPrivada(ArrayList<Eleicao> arrEleiPont, 
			ArrayList<Eleicao> arrEleiUnic,
			ArrayList<Eleicao> arrEleiPriv,
			ArrayList<Eleicao> arreleiPubl){
		
		
		for(Eleicao eleicaoP : arrEleiPont){
			if(eleicaoP.isPublica()){
				arreleiPubl.add(eleicaoP);
			}else if(!eleicaoP.isPublica()){
				arrEleiPriv.add(eleicaoP);
			}
		}
		
		for(Eleicao eleicaoU : arrEleiUnic){
			if(eleicaoU.isPublica()){
				arreleiPubl.add(eleicaoU);
			}else if(!eleicaoU.isPublica()){
				arrEleiPriv.add(eleicaoU);
			}
		}
	}

	private void processarFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Eleicao eleicao = null;
		String idEleicao = request.getParameter(ServletMenu.ID_REQ_ID_ELEICAO);
		String tpEleicao = request.getParameter(ServletMenu.ID_REQ_TIPO_DE_ELEICAO);
		Usuario usuario = null;
		if(tpEleicao.equals(TipoEleicao.ESCOLHA_UNICA.toString())){
			eleicao = new EleicaoEscolhaUnica();
		}else{
			eleicao = new EleicaoPontuacao();
		}
		
		eleicao.setId(Integer.valueOf(idEleicao));

		request.setAttribute(ID_REQ_ID_ELEICAO, idEleicao);
		request.setAttribute(ID_REQ_TIPO_DE_ELEICAO, tpEleicao);
		request.setAttribute(ID_REQ_EVENTO, ID_REQ_EVENTO_EXIBIR_INCLUSAO);
		
		if(((Eleicao)fachada.consultarEleicaoPelaChave(eleicao)).isPublica()){
			usuario = new Usuario();
			usuario.setId(0);
			request.getSession(true).setAttribute("idEleicao", idEleicao);
			request.getSession(true).setAttribute("tpEleicao", tpEleicao);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ServletVoto");
			requestDispatcher.forward(request, response);
		}else if(!((Eleicao)fachada.consultarEleicaoPelaChave(eleicao)).isPublica()){
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
			requestDispatcher.forward(request, response);
		}
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
				.getParameter(ServletMenu.ID_REQ_CODIGO_PERFIL_USUARIO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_PERFIL_USUARIO;
		String descricaoPerfilUsuario = request
				.getParameter(ServletMenu.ID_REQ_DESCRICAO_PERFIL_USUARIO);

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
				.getParameter(ServletMenu.ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {

			PerfilUsuario perfilUsuario = new PerfilUsuario();
			perfilUsuario.setId(Integer.valueOf(chavePrimaria));

			perfilUsuario = fachada
					.consultarPerfilUsuarioPelaChave(perfilUsuario);

			request.setAttribute(
					ServletMenu.ID_REQ_OBJETO_PERFIL_USUARIO,
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
				.getParameter(ServletMenu.ID_REQ_CODIGO_PERFIL_USUARIO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_PERFIL_USUARIO;
		String descricaoPerfilUsuario = request
				.getParameter(ServletMenu.ID_REQ_DESCRICAO_PERFIL_USUARIO);

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
				.getParameter(ServletMenu.ID_REQ_CHAVE_PRIMARIA);

		if (chavePrimaria != null) {

			PerfilUsuario perfilUsuario = new PerfilUsuario();
			perfilUsuario.setId(Integer.valueOf(chavePrimaria));

			perfilUsuario = fachada
					.consultarPerfilUsuarioPelaChave(perfilUsuario);

			request.setAttribute(
					ServletMenu.ID_REQ_OBJETO_PERFIL_USUARIO,
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
				.getParameter(ServletMenu.ID_REQ_CODIGO_PERFIL_USUARIO);
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_PERFIL_USUARIO;
		String descricaoPerfilUsuario = request
				.getParameter(ServletMenu.ID_REQ_DESCRICAO_PERFIL_USUARIO);

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
