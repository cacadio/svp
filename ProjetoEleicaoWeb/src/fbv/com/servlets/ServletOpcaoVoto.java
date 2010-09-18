package fbv.com.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.OpcaoVoto;
import fbv.com.util.InterfacePrincipal;
 
/**
 * Servlet implementation class ServletOpcaoVoto
 */
public class ServletOpcaoVoto extends HttpServlet implements
		InterfacePrincipal {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletOpcaoVoto config) throws ServletException{
		super.init(config);
	}	

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String idOpcaoVoto = "";
		String formulario = "";
		String dsOpcaoVoto = "";
		String cdEleicao = "";
		//Codigo que salva link no banco
		boolean isMultiPart = FileUpload.isMultipartContent(request);
		String idEvento = request.getParameter(ServletOpcaoVoto.ID_REQ_EVENTO);
		if (isMultiPart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			try {
				List<FileItem> items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				//aqui iremos saber os nomes dos campos que 
				//vieram do form
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.getFieldName().equals("pathFoto")) {
						formulario = item.getString();
					}
					if (item.getFieldName().equals(ID_REQ_DESCRICAO_OPCAO_VOTO)) {
						dsOpcaoVoto = item.getString();
					}
					if (item.getFieldName().equals(ID_REQ_CODIGO_ELEICAO)) {
						cdEleicao = item.getString();
					}
					if (item.getFieldName().equals(ID_REQ_EVENTO)) {
						idEvento = item.getString();
					}
					if (item.getFieldName().equals(ID_REQ_CODIGO_OPCAO_VOTO)) {
						idOpcaoVoto = item.getString();
					}
					if(item.getFieldName().equals(ID_REQ_SELECT_ELEICOES)){
						cdEleicao = item.getString();
					}
					if (!item.isFormField()) {
						this.inserirImagemDiretorio(dsOpcaoVoto, cdEleicao, item, idOpcaoVoto, idEvento);
					}
				}
			} catch (FileUploadException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		try {
			
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
					processarAlteracao(request,response, idOpcaoVoto, formulario, cdEleicao, dsOpcaoVoto);
					
				} else if(idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_EXIBIR_EXCLUSAO)) {
					exibirExclusao(request,response);
					
				} else if(idEvento.equals(ServletOpcaoVoto.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO)) {
					processarExclusao(request,response);
				}
			} else {
				processarFiltroConsulta(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void inserirImagemDiretorio(String pDsOpcaoVoto, String pCdEleicao, FileItem item, String idOpcaoVoto, String cdEvento) throws IOException, SQLException {  
		
		String caminho = "";
		
		if(item.getName().length() > 0){
			// Cria o diretorio caso ele nao exista
			File diretorio = new File(this.getServletContext().getRealPath("") + "\\img\\");
			if (!diretorio.exists()) {
				diretorio.mkdir();
			}

			// Mandar o arquivo para o diretorio informado
			String nome = item.getName();
			String arq[] = nome.split("\\\\");
			for (int i = 0; i < arq.length; i++) {
				nome = arq[i];
			}

			File file = new File(diretorio, nome);
			FileOutputStream output = new FileOutputStream(file);
			InputStream is = item.getInputStream();
			byte[] buffer = new byte[2048];
			int nLidos;
			while ((nLidos = is.read(buffer)) >= 0) {
				output.write(buffer, 0, nLidos);
			}

			output.flush();
			output.close();

			caminho =  "./img/" + nome;
		}
		
		OpcaoVoto op = null;
		try {
			if(cdEvento.equalsIgnoreCase(ID_REQ_EVENTO_PROCESSAR_INCLUSAO)){
				op = new OpcaoVoto(0, Integer.parseInt(pCdEleicao), pDsOpcaoVoto, caminho);
				Fachada.getInstancia().incluirOpcaoVoto(op);
			}else if(cdEvento.equalsIgnoreCase(ID_REQ_EVENTO_PROCESSAR_ALTERACAO)){
				op = new OpcaoVoto(Integer.parseInt(idOpcaoVoto), Integer.parseInt(pCdEleicao), pDsOpcaoVoto, caminho);
				Fachada.getInstancia().alterarOpcaoVoto(op);
			}
		} catch (ExcecaoRegistroJaExistente e) {
			e.printStackTrace();
		} catch (ExcecaoAcessoRepositorio e) {
			e.printStackTrace();
		}

	}  

	private void processarFiltroConsulta(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		ArrayList<OpcaoVoto> arrayOpcaoVoto = new ArrayList<OpcaoVoto>();
		Fachada fachada = Fachada.getInstancia();
		Integer idEleicao = null;
		
		String strIDEleicao = request.getParameter(ID_REQ_SELECT_ELEICOES);
		if(strIDEleicao != null && !strIDEleicao.equals(""))
			idEleicao  = new Integer(strIDEleicao);
		
		String idOpcaoVoto = request.getParameter(ID_REQ_CODIGO_OPCAO_VOTO);
		
		if(idEleicao != null && idEleicao > 0){
			OpcaoVoto opcaoVoto = new OpcaoVoto();
			opcaoVoto.setIdEleicao(idEleicao);

			arrayOpcaoVoto = fachada.consultarPeloIDEleicao(opcaoVoto);
		} else if ((idOpcaoVoto != null) && (!idOpcaoVoto.equals(""))) {
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
		request.setAttribute(ID_REQ_ID_ELEICAO, idEleicao);
		
		carregarComboEleicao(request, response);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/consulta_opcao_voto.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void carregarComboEleicao(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Fachada fachada = Fachada.getInstancia();
		ArrayList<OpcaoVoto> arrayOpcaoVoto = new ArrayList<OpcaoVoto>();
		TreeSet<Integer> listaCodigosEleicoes = new TreeSet<Integer>();
		ArrayList<Eleicao> eleicoes = null;
		
		// consulta todas as opções de voto
		arrayOpcaoVoto = fachada.consultarTodosOpcaoVoto();
		
		// Obtém os códigos das eleições
		if(arrayOpcaoVoto != null){
			// Monta um lista com os códigos das eleições sem repetir valores.
			for (OpcaoVoto opcaoVoto : arrayOpcaoVoto) {
				listaCodigosEleicoes.add(opcaoVoto.getIdEleicao());
			}
			// Criando a lista de eleições com base na lista de códigos obtidas
			eleicoes = new ArrayList<Eleicao>();
			Eleicao eleicaoAux;
			for (Integer codigoLido : listaCodigosEleicoes) {
				eleicaoAux = new Eleicao(codigoLido);
				eleicaoAux = (Eleicao)fachada.consultarEleicaoPelaChave(eleicaoAux);
				if(eleicaoAux != null)
					eleicoes.add(eleicaoAux);
			}	
		}
		
		// Seta a coleção no request
		request.setAttribute(ID_REQ_ARRAY_LIST_ELEICAO, eleicoes);
	}

	private void exibirInclusao(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		carregarComboEleicao(request, response);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/inclusao_opcao_voto.jsp");
		requestDispatcher.forward(request, response);
	}

	private void processarInclusao(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_OPCAO_VOTO;
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

		carregarComboEleicao(request, response);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/alteracao_opcao_voto.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void processarAlteracao(HttpServletRequest request, HttpServletResponse response, String pIdOpcaoVoto, String formulario, String pcdEleicao, String dsOpcaoVoto) 
			throws Exception {
		String mensagem = "";
		String nomeServlet = ID_REQ_NOME_SERVLET_OPCAO_VOTO;

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
