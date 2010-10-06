package fbv.com.util;

public interface InterfacePrincipal {
	
	public static final String ID_REQ_EVENTO_EXIBIR_FILTRO_CONSULTA = "exibirFiltroConsulta";
	public static final String ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA = "processarFiltroConsulta";
	public static final String ID_REQ_EVENTO_EXIBIR_INCLUSAO = "exibirInclusao";
	public static final String ID_REQ_EVENTO_PROCESSAR_INCLUSAO = "processarInclusao";
	public static final String ID_REQ_EVENTO_EXIBIR_ALTERACAO = "exibirAlteracao";
	public static final String ID_REQ_EVENTO_PROCESSAR_ALTERACAO = "processarAlteracao";
	public static final String ID_REQ_EVENTO_EXIBIR_EXCLUSAO = "exibirExclusao";
	public static final String ID_REQ_EVENTO_PROCESSAR_EXCLUSAO = "processarExclusao";
	// Alterações de estado
	public static final String ID_REQ_EVENTO_PROCESSAR_INICIALIZACAO = "processarInicializacao";
	public static final String ID_REQ_EVENTO_PROCESSAR_CONCLUSÃO = "processarConclusao";
	public static final String ID_REQ_EVENTO_PROCESSAR_APURACAO = "processarApuracao";
	
	public static final String ID_REQ_EVENTO = "idEvento";
	public static final String ID_REQ_MENSAGEM = "idMensagem";
	public static final String ID_REQ_NOME_SERVLET = "nomeServlet";
	public static final String ID_REQ_CHAVE_PRIMARIA = "chavePrimaria";
	
	//identificadores caso de uso Perfil de usuário
	public static final String ID_REQ_ARRAY_LIST_PERFIL_USUARIO = "arrayListPerfilUsuario";
	public static final String ID_REQ_CODIGO_PERFIL_USUARIO = "codigoPerfilUsuario";
	public static final String ID_REQ_DESCRICAO_PERFIL_USUARIO = "descricaoPerfilUsuario";
	public static final String ID_REQ_NOME_SERVLET_PERFIL_USUARIO = "ServletPerfilUsuario";
	public static final String ID_REQ_OBJETO_PERFIL_USUARIO = "objetoPerfilUsuario";
	
	//identificadores caso de uso Voto
	public static final String ID_REQ_ARRAY_LIST_VOTO = "arrayListVoto";
	public static final String ID_REQ_ID_VOTO = "idVoto";
	public static final String ID_REQ_ID_ELEICAO = "idEleicao";
	public static final String ID_REQ_ID_USUARIO = "idUsuario";
	public static final String ID_REQ_DATA_HORA = "dtHora";
	public static final String ID_REQ_VALOR_VOTO = "valorVoto";
	public static final String ID_REQ_OPCAO_VOTO = "opcaoVoto";
	public static final String ID_REQ_NOME_SERVLET_VOTO = "ServletVoto";
	public static final String ID_REQ_OBJETO_VOTO = "objetoVoto";
	
	//identificadores caso de uso usuário
	public static final String ID_REQ_ARRAY_LIST_USUARIO = "arrayListUsuario";
	public static final String ID_REQ_LOGIN_USUARIO = "loginUsuario";
	public static final String ID_REQ_SENHA_USUARIO = "senhaUsuario";
	public static final String ID_REQ_CPF_USUARIO = "cpfUsuario";
	public static final String ID_REQ_NOME_USUARIO = "nomeUsuario";
	public static final String ID_REQ_ID_PERFIL_USUARIO = "idPerfilUsuario";
	public static final String ID_REQ_OBJETO_USUARIO = "objetoUsuario";
	public static final String ID_REQ_NOME_SERVLET_USUARIO = "ServletUsuario";
	
	//identificadores caso de uso opcao voto
	public static final String ID_REQ_NOME_SERVLET_OPCAO_VOTO = "ServletOpcaoVoto";
	public static final String ID_REQ_CODIGO_OPCAO_VOTO = "codigoOpcaoVoto";
	public static final String ID_REQ_DESCRICAO_OPCAO_VOTO = "descricaosOpcaoVoto";
	public static final String ID_REQ_PATH_FOTO = "pathFoto";
	public static final String ID_REQ_ARRAY_LIST_OPCAO_VOTO = "arrayListOpcaoVoto";
	public static final String ID_REQ_OBJETO_OPCAO_VOTO = "objetoOpcaoVoto";


	//identificadores caso de uso Eleição
	public static final String ID_REQ_ARRAY_LIST_ELEICAO = "arrayListEleicao";
	public static final String ID_REQ_TIPO_ELEICAO = "tipoEleicao";
	public static final String ID_REQ_CODIGO_ELEICAO = "codigoEleicao";
	public static final String ID_REQ_DESCRICAO_ELEICAO = "descricaoEleicao";
	public static final String ID_REQ_ESTADO_ELEICAO = "estadoEleicao";
	public static final String ID_REQ_IN_PUBLICA_ELEICAO = "inPublicaEleicao";
	public static final String ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO = "inVisibilidadeAbertaEleicao";
	public static final String ID_REQ_IN_VOTO_MULTIPLO_ELEICAO = "inVotoMultiploEleicao";
	public static final String ID_REQ_DATA_INICIO_ELEICAO = "dataInicioEleicao";
	public static final String ID_REQ_DATA_FIM_ELEICAO = "dataFimEleicao";
	public static final String ID_REQ_TITULO_PAGINA = "tituloPagina";
	public static final String ID_REQ_ARRAY_LIST_ELEICAO_PRIVADA = "ArrayListEleicaoPrivada";
	public static final String ID_REQ_ARRAY_LIST_ELEICAO_PUBLICA = "ArrayListEleicaoPublica";
	public static final String ID_REQ_SELECT_ELEICOES = "cboEleicao";
	
	/*
	 * Escolha Única
	 */
	public static final String ID_REQ_CODIGO_ELEICAO_PAI = "codigoEleicaoPai"; 
	public static final String ID_REQ_IN_CAMPO_NULO_ELEICAO = "inCampoNuloEleicao";
	public static final String ID_REQ_PERCENTUAL_VITORIA_ELEICAO = "percentualVitoriaEleicao";
	/*
	 * Pontuação
	 */
	public static final String ID_REQ_PONTUACAO_TIPO_ELEICAO_PONTUACAO = "PONTUACAO";
	public static final String ID_REQ_PONTUACAO_MINIMA_ELEICAO = "pontuacaoMinimaEleicao";
	public static final String ID_REQ_PONTUACAO_MAXIMA_ELEICAO = "pontuacaoMaximaEleicao";
	public static final String ID_REQ_INTERVALO_PONTUACAO_ELEICAO = "intervaloPontuacaoEleicao";
	public static final String  ID_REQ_TIPO_DE_ELEICAO = "tipoDeEleicao";
	public static final String ID_REQ_NOME_SERVLET_ELEICAO = "ServletEleicao";
	public static final String ID_REQ_OBJETO_ELEICAO = "objetoEleicao";
	
	//identificadores caso de uso login
	public static final String ID_REQ_NOME_SERVLET_LOGIN = "ServletLogin";
	public static final String ID_REQ_LOGIN = "login";
	public static final String ID_REQ_PASSWORD = "password";
	
	//Resultado Eleição
	public static final String ID_REQ_RESULTADO = "Resultado";
	
	//Separador da chave primária
	public static final String ID_REQ_SEPARADOR_PADRAO = "/";

}
