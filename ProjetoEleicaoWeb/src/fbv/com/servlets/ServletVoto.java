package fbv.com.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.OpcaoVoto;
import fbv.com.negocio.PerfilUsuario;
import fbv.com.negocio.Usuario;
import fbv.com.negocio.Voto;
import fbv.com.util.InterfacePrincipal;
import fbv.com.util.TipoEleicao;

/**
 * Servlet implementation class for Servlet: ServletVoto
 *
 */
 public class ServletVoto extends HttpServlet implements InterfacePrincipal {
    static final long serialVersionUID = 1L;
   
	public ServletVoto() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			String idEvento = this.getAtributoOuParametroStringOpcional(ID_REQ_EVENTO, request);

			if (idEvento != null && !idEvento.equals("")) {

				if (idEvento
						.equals(ServletVoto.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA)) {
					processarFiltroConsulta(request, response);
				} else if (idEvento
						.equals(ServletVoto.ID_REQ_EVENTO_EXIBIR_INCLUSAO)) {
					exibirInclusao(request, response);
				} else if (idEvento
						.equals(ServletVoto.ID_REQ_EVENTO_PROCESSAR_INCLUSAO)) {
					processarInclusao(request, response);
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

		ArrayList<Voto> arrayVoto = new ArrayList<Voto>();
		Voto objVoto = null;
		ArrayList<Usuario> arrayUsuario = new ArrayList<Usuario>();
		Integer idUsuario = null;
		Integer idVotoUsuario = null;
		Voto voto = null;
		Usuario usuario = null;

		Fachada fachada = Fachada.getInstancia();

		String idVoto = request.getParameter(ServletVoto.ID_REQ_ID_VOTO);
		
		//Se o c�digo do voto for passado como parametro, consultar o voto, se n�o consultar todos os votos
		if (idVoto != null && !idVoto.equals("")) {

			objVoto = new Voto();
			objVoto.setIdVoto(Integer.valueOf(idVoto));

			objVoto = fachada.consultarVotoPelaChave(objVoto);

			if (objVoto != null) {
				arrayVoto.add(objVoto);
			}

		} else {
			arrayVoto = fachada.consultarTodosVoto();
		}
		
		//Consulta os usuarios
		arrayUsuario = fachada.consultarTodosUsuario();
		
		//itera para pegar o nome do usuario
		for(int i=0; arrayVoto.size() > i; i++){
		 voto = arrayVoto.get(i);
			
			 idVotoUsuario = voto.getIdUsuario();
			
			for(int j=0; arrayUsuario.size() > j; j++ ){
				
				 usuario = arrayUsuario.get(j);
				
				 idUsuario = usuario.getId();
				
				if(idVotoUsuario.equals(idUsuario)){
					
					String nomeUsuario = usuario.getNome();
					voto.setNomeUsuario(nomeUsuario);
					break;
				}
			}						
		}
		
		ArrayList<OpcaoVoto> arrayOpcaoVoto = new ArrayList<OpcaoVoto>();
		Integer idOpcaoVoto= null;
		Integer idVotoOpcaoVoto = null;
		OpcaoVoto opcaoVoto = null;
		
		//Consulta as opcoes de voto
		arrayOpcaoVoto = fachada.consultarTodosOpcaoVoto();
		
		//itera para pegar o nome da opcao de voto
		for(int i=0; arrayVoto.size() > i; i++){
			 voto = arrayVoto.get(i);
				
			 idOpcaoVoto = voto.getIdOpcaoVoto();
			
			for(int j=0; arrayOpcaoVoto.size() > j; j++ ){
				
				opcaoVoto = arrayOpcaoVoto.get(j);
				
				idVotoOpcaoVoto = opcaoVoto.getId();
				
				if(idVotoOpcaoVoto.equals(idOpcaoVoto)){
					
					String descricaoOpcaoVoto = opcaoVoto.getDescricao();
					voto.setDescricaoOpcaoVoto(descricaoOpcaoVoto);
					break;
				}
			}						
		}
		
		request.setAttribute(ID_REQ_ARRAY_LIST_VOTO,
				arrayVoto);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consulta_voto.jsp");
		requestDispatcher.forward(request, response);

	}

	private void exibirFiltroConsulta(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consulta_voto.jsp");
		requestDispatcher.forward(request, response);
	}

	private void exibirInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Fachada fachada = Fachada.getInstancia();
		ArrayList<OpcaoVoto> colecaoOpcaoVoto = new ArrayList<OpcaoVoto>();
		OpcaoVoto opcaoVoto = null;
		Usuario usuario = null;
		String mensagem = "";
		String nomeServlet = "";
		String tipoDeEleicao = "";
		String descEleicao = "";
		String idEleicao = "";
		Integer intervaloPontuacao = null;
		Integer valorMaximoPontuacao = null;
		Integer valorMinimoPontuacao = null;
		boolean usuarioJaVotou = false;
		Eleicao eleicaoVoto = null;
		
		nomeServlet = ID_REQ_NOME_SERVLET_VOTO;
		
		//Pegando o tipo de elei��o da sessao
		tipoDeEleicao = (String)request.getSession().getAttribute("tpEleicao");
		idEleicao = (String)request.getSession().getAttribute("idEleicao");
		usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		eleicaoVoto = new Eleicao();
		if (tipoDeEleicao.equals("ESCOLHA_UNICA"))
			eleicaoVoto = new EleicaoEscolhaUnica();
		else
			eleicaoVoto = new EleicaoPontuacao();

		eleicaoVoto.setId(new Integer(idEleicao).intValue());

		eleicaoVoto = (Eleicao) fachada.consultarEleicaoPelaChave(eleicaoVoto);
		descEleicao = eleicaoVoto.getDescricao();
		
		//Verifica pelo tipo de usuario se o mesmo pode votar mais de uma vez, se não puder voltar para tela de login
		if(!eleicaoVoto.isPublica() && eleicaoVoto.isMultiplosVotos()){
			ArrayList respostaVotoUsuario = null;
			respostaVotoUsuario = fachada.consultarVotoPorUsuarioEleicao(new Integer(usuario.getId()).intValue(),new Integer(idEleicao).intValue());
		
			if(respostaVotoUsuario != null && respostaVotoUsuario.size() != 0){
				usuarioJaVotou = true;
			}
		}
		
		//TODO: Quando for pegar as opcoes de voto para a eleicao,
		//dá um join e pega as informações dela tb p mostrar
		opcaoVoto = new OpcaoVoto();
		opcaoVoto.setIdEleicao(new Integer(idEleicao));
		colecaoOpcaoVoto = fachada.consultarPeloIDEleicao(opcaoVoto);
		if(colecaoOpcaoVoto != null && colecaoOpcaoVoto.isEmpty()){
			
			mensagem = "Não existe opções de voto cadastrado para este tipo de eleição";
			request.setAttribute(ID_REQ_MENSAGEM, mensagem);
			request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
			requestDispatcher.forward(request, response);	
		}
		
		Eleicao eleicao = new Eleicao();
		
		if (tipoDeEleicao.equals("PONTUACAO")){
			eleicao.setId(Integer.valueOf(idEleicao));
			EleicaoPontuacao eleicaoPontuacao = new EleicaoPontuacao();			
			 eleicaoPontuacao = (EleicaoPontuacao)fachada.consultarEleicaoPelaChave(eleicao);
			
			 intervaloPontuacao = eleicaoPontuacao.getIntervaloPontuacao();
			 valorMaximoPontuacao = eleicaoPontuacao.getPontuacaoMaxima();
			 valorMinimoPontuacao = eleicaoPontuacao.getPontuacaoMinima();
		}
		
		request.setAttribute(ID_REQ_INTERVALO_PONTUACAO_ELEICAO, intervaloPontuacao);
		request.setAttribute(ID_REQ_PONTUACAO_MAXIMA_ELEICAO, valorMaximoPontuacao);
		request.setAttribute(ID_REQ_PONTUACAO_MINIMA_ELEICAO, valorMinimoPontuacao);
		request.setAttribute(ID_REQ_OBJETO_VOTO, colecaoOpcaoVoto);
		request.setAttribute(ID_REQ_TIPO_DE_ELEICAO, tipoDeEleicao);
		request.setAttribute(ID_REQ_OBJETO_ELEICAO, eleicao);
		request.setAttribute(ID_REQ_ID_ELEICAO, idEleicao);
		request.setAttribute(ID_REQ_DESCRICAO_ELEICAO, descEleicao);
		//Se multiplos votos for falso e usuario já tiver votado, voltar para tela principal
		if(usuarioJaVotou){
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/principal.jsp");
				requestDispatcher.forward(request, response);
				

		}else{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/inclusao_voto.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	private void processarInclusao(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Fachada fachada = Fachada.getInstancia();
		String mensagem = "";
		Usuario usuario = null;
		String idEleicao = "";
		String opcaoVoto = "";
		String valorVoto = "";
		String nomeServlet = "";
		String tipoDeEleicao = "";
		Voto voto = null;	
		OpcaoVoto opcaoVotoEleicao = null;
		
		//Obtendo dados da tela via request
		nomeServlet = ID_REQ_NOME_SERVLET_VOTO;
		
		tipoDeEleicao = this.getAtributoOuParametroStringOpcional(ServletLogin.ID_REQ_TIPO_DE_ELEICAO, request);
		idEleicao = this.getAtributoOuParametroStringOpcional(ServletLogin.ID_REQ_ID_ELEICAO, request);
		usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		if(usuario == null){
			usuario = new Usuario();
			usuario.setId(0);
		}
		
		if(!tipoDeEleicao.equals("PONTUACAO")){
		opcaoVoto = request.getParameter(ServletVoto.ID_REQ_CODIGO_OPCAO_VOTO);
		
		if(usuario == null){
			
			usuario.setId(0);
		}
		
		valorVoto = request.getParameter(ServletVoto.ID_REQ_VALOR_VOTO + opcaoVoto);
		
		//Montando o Objeto Voto
		voto = new Voto();
		voto.setIdUsuario(Integer.valueOf(usuario.getId()));
		voto.setIdEleicao(new Integer(idEleicao));
		voto.setIdOpcaoVoto(Integer.valueOf(opcaoVoto.trim()));
	
		if(valorVoto != null && !valorVoto.equals("")){
			voto.setValorVoto(Integer.valueOf(valorVoto.trim()));
		}
		
			fachada.incluirVoto(voto);
			mensagem = "Voto Cadastrado com Sucesso";
		
		}else{
				ArrayList<OpcaoVoto> colecaoOpcaoVoto = new ArrayList<OpcaoVoto>();
				
				opcaoVotoEleicao = new OpcaoVoto();
				opcaoVotoEleicao.setIdEleicao(new Integer(idEleicao));
				OpcaoVoto opcaoVotoCheck = null;
				
				//Consultando as op��es de voto 
				colecaoOpcaoVoto = fachada.consultarPeloIDEleicao(opcaoVotoEleicao);
				
				for(int i=0; colecaoOpcaoVoto.size() > i; i++){
					 
					opcaoVotoCheck = colecaoOpcaoVoto.get(i);
				
				opcaoVoto = request.getParameter(ServletVoto.ID_REQ_CODIGO_OPCAO_VOTO + opcaoVotoCheck.getId());
				if(usuario == null){
					
					usuario.setId(0);
				}

				valorVoto = request.getParameter(ServletVoto.ID_REQ_VALOR_VOTO + opcaoVotoCheck.getId());
				
				//Montando o Objeto Voto
				voto = new Voto();
				voto.setIdUsuario(Integer.valueOf(usuario.getId()));
				voto.setIdEleicao(new Integer(idEleicao));
				voto.setIdOpcaoVoto(Integer.valueOf(opcaoVoto.trim()));
			
				if(valorVoto != null && !valorVoto.equals("")){
					voto.setValorVoto(Integer.valueOf(valorVoto.trim()));
				}
				
					fachada.incluirVoto(voto);
					mensagem = "Voto Cadastrado com Sucesso";
				}
			}
		
			request.setAttribute(ID_REQ_MENSAGEM, mensagem);
			request.setAttribute(ID_REQ_NOME_SERVLET, nomeServlet);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/mensagens.jsp");
			requestDispatcher.forward(request, response);	

	}
	
	private String getAtributoOuParametroStringOpcional(String pNmAtributo, HttpServletRequest pRequest){
		String strAux = null;
	
		strAux = (String) pRequest.getAttribute(pNmAtributo);
	
		if (strAux == null) {
			strAux = pRequest.getParameter(pNmAtributo);
		}
	
		return strAux;
	}

	
}