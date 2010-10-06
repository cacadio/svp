<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.Eleicao"%>
<%@page import="fbv.com.servlets.ServletEleicao"%>
<%@page import="fbv.com.util.TipoEleicao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fbv.com.util.InterfacePrincipal"%>
<%@page import="fbv.com.negocio.EstadoNova"%>
<%@page import="fbv.com.negocio.EstadoIniciada"%>
<%@page import="fbv.com.negocio.EstadoEmCurso"%>
<%@page import="fbv.com.negocio.EstadoConcluida"%><html>
<head>
	<title>Consulta Eleição</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
	<script type="text/javascript" src="js/biblioteca_funcoes_eleicao.js" ></script>
</head>
<%
	try{
		
	//Obtem Parâmetros do request	
	@SuppressWarnings("unchecked")
	ArrayList<Eleicao> arrayListEleicao = (ArrayList<Eleicao>) request.getAttribute(ServletEleicao.ID_REQ_ARRAY_LIST_ELEICAO);
	
%>

<script type="text/javascript" >

function submeter(evento){
	<%
	if (arrayListEleicao != null && arrayListEleicao.size() > 0) {
	%>
		document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO%>.value = evento;
		document.forms.form_principal.submit();
	<%
	} else {	
	%>
		alert("Nenhum Registro Selecionado!");
		return false;
	<%
	}
	%>
}
function eventoAlterar() {
	submeter("<%=ServletEleicao.ID_REQ_EVENTO_EXIBIR_ALTERACAO%>");
}

function eventoExcluir(){
	submeter("<%=ServletEleicao.ID_REQ_EVENTO_EXIBIR_EXCLUSAO%>");
}

function eventoInicializar(){
	submeter("<%=ServletEleicao.ID_REQ_EVENTO_PROCESSAR_INICIALIZACAO%>");
}

function eventoConcluir(){
	submeter("<%=ServletEleicao.ID_REQ_EVENTO_PROCESSAR_CONCLUSÃO%>");
}

function eventoApurar(){
	submeter("<%=ServletEleicao.ID_REQ_EVENTO_PROCESSAR_APURACAO%>");
}



function desabilitarBotoes(){
	document.forms.form_principal.botaoAlterar.disabled = true;
	document.forms.form_principal.botaoExcluir.disabled = true;
	document.forms.form_principal.botaoInicializar.disabled = true;
	document.forms.form_principal.botaoConcluir.disabled = true;
	document.forms.form_principal.botaoApurar.disabled = true;
}

function carregarBotoes(){
	listaChaves = document.forms.form_principal.<%=ServletEleicao.ID_REQ_CHAVE_PRIMARIA%>
	achou = false;
	if (listaChaves != null && listaChaves.length > 0){
		for (i = 0; i < listaChaves.length; i++){
			chaveLida = listaChaves[i];
			if (chaveLida.checked == true){
				achou = true;
				strValores = chaveLida.value;
				valores = strValores.split("<%=ServletEleicao.ID_REQ_SEPARADOR_PADRAO%>");
				dsEstado = valores[3];
				if (dsEstado == "<%=EstadoNova.getInstancia().getDescricao()%>"){
					document.forms.form_principal.botaoAlterar.disabled = false;
					document.forms.form_principal.botaoInicializar.disabled = false;
					document.forms.form_principal.botaoConcluir.disabled = true;
					document.forms.form_principal.botaoApurar.disabled = true;
				} else if (dsEstado == "<%=EstadoEmCurso.getInstancia().getDescricao()%>"){
					document.forms.form_principal.botaoAlterar.disabled = false;
					document.forms.form_principal.botaoInicializar.disabled = true;
					document.forms.form_principal.botaoConcluir.disabled = false;
					document.forms.form_principal.botaoApurar.disabled = true;
				} else if (dsEstado == "<%=EstadoConcluida.getInstancia().getDescricao()%>"){
					document.forms.form_principal.botaoAlterar.disabled = true;
					document.forms.form_principal.botaoInicializar.disabled = true;
					document.forms.form_principal.botaoConcluir.disabled = true;
					document.forms.form_principal.botaoApurar.disabled = false;
				} else {
					document.forms.form_principal.botaoAlterar.disabled = false;
					document.forms.form_principal.botaoInicializar.disabled = true;
					document.forms.form_principal.botaoConcluir.disabled = true;
					document.forms.form_principal.botaoApurar.disabled = true;
				}
				break;
			}
			
		}
	}

	if (achou == true){
		document.forms.form_principal.botaoExcluir.disabled = false;
	} else {
		desabilitarBotoes();
	}
}
</script>
<body onload="setarFoco(document.forms.form_principal.<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO%>);carregarBotoes();">
<form action="/ProjetoEleicaoWeb/ServletEleicao" method="post" id="form_principal">
<input type="hidden" id="<%=ServletEleicao.ID_REQ_EVENTO%>" name="<%=ServletEleicao.ID_REQ_EVENTO%>" value="">
<div id="header">
	<div id="logo">
		<h1><a href="#">Projeto Eleição</a></h1>
		<p>FBV - Faculdade Boa Viagem</p>
	</div>
	<!-- end #logo -->
	<div id="menu">
		<ul>
			<li class="first"><a href="/ProjetoEleicaoWeb/ServletMenu">Home</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletEleicao">Eleição</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletOpcaoVoto">Opções de Voto</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletVoto">Voto</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletResultado">Resultado</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletUsuario">Usuário</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletPerfilUsuario">Perfis</a></li>
		</ul>
	</div>
	<!-- end #menu -->
</div>
	<table width="80%" border="0" align="center">
		<tr>
			<td colspan="4">
				<div class="post">
					<h1 class="title">Consultar Eleição </h1>
				</div>
			</td>
		</tr>
		<tr>
			<td width="80px" class="td" align="right">
				Tipo:
			</td>
			<td width="100px">
				<select id="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" name="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>">
					<option></option>
					<option value="<%= TipoEleicao.ESCOLHA_UNICA.value() %>" >Escolha Única</option>
					<option value="<%= TipoEleicao.PONTUACAO.value() %>" >Pontuação</option>
				</select>
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="td" align="right">
				Código:
			</td>
			<td>
				<input type="text" id="<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO%>" value="" ></input>
			</td>
			<td><input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
		</tr>
	</table>
	<br/>
	<table width="80%" border="0" align="center">
		<tr>
			<th class="td" width="10%" align="center">#</th>
			<th class="td" width="10%" align="center">Código</th>
			<th class="td" width="40%" align="center">Descrição</th>
			<th class="td" width="10px" align="center">Estado</th>
			<th class="td" width="10px" align="center">Data Abertura</th>
			<th class="td" width="10px" align="center">Data Prevista Encerramento</th>
		</tr>
		<%
		//Exibindo dados
		if(arrayListEleicao != null && !arrayListEleicao.isEmpty()){
			String checked = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for(int i = 0 ; i < arrayListEleicao.size() ; i++){
				
				checked = "";
				Eleicao eleicao = arrayListEleicao.get(i);
				
				String classeLinha;
				if (i % 2 == 0) {
					classeLinha = "linhaimpar";
				} else {
					classeLinha = "linhapar";
				}
				// Obtém os dados da eleição 
				int idEleicao = eleicao.getId();
				String dsEleicao = eleicao.getDescricao();
				String tpEleicao = eleicao.getClass().getSimpleName();
				String idEstadodEleicao = eleicao.getEstado().getDescricao();
				// Monta a chave primária
				String chavePrimaria = idEleicao + ServletEleicao.ID_REQ_SEPARADOR_PADRAO 
									 + dsEleicao + ServletEleicao.ID_REQ_SEPARADOR_PADRAO 
									 + tpEleicao + ServletEleicao.ID_REQ_SEPARADOR_PADRAO 
									 + idEstadodEleicao;
		%>
		<tr>
			<td class="<%= classeLinha %>" align="center">
				<input type="radio" id="<%=ServletEleicao.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletEleicao.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> 
				value="<%=chavePrimaria%>" onclick="carregarBotoes()" onchange="carregarBotoes()"> 
			</td>
			<td class="<%= classeLinha %>" align="left"><%=eleicao.getId()%></td>
			<td class="<%= classeLinha %>" align="left"><%=eleicao.getDescricao()%></td>
			<td class="<%= classeLinha %>" align="left"><%=eleicao.getEstado().getDescricao()%></td>
			<td class="<%= classeLinha %>" align="center"><%= eleicao.getDataAbertura() != null ? sdf.format(eleicao.getDataAbertura()) : "" %></td>
			<td class="<%= classeLinha %>" align="center"><%= eleicao.getDataEncerramento() != null ? sdf.format(eleicao.getDataEncerramento()) : "" %></td>
		</tr>
		<%
			}
		}
		%>
		<tr>
			<td class="tdBottom" colspan="6">&nbsp;</td>
		</tr>	
	</table>
	<table width="80%" border="0" align="center">
		<tr>
			<td class="linhabotao" align="right"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
			<td class="linhabotao" align="center"><input type="button" id="botaoAlterar" name="botaoAlterar" value="Alterar" onclick="eventoAlterar()"> </td>
			<td class="linhabotao" align="left"><input type="button" id="botaoExcluir" name="botaoExcluir" value="Excluir" onclick="eventoExcluir()"> </td>
			<td class="linhabotao" align="right"><input type="button" id="botaoInicializar" name="botaoInicializar" value="Inicializar" onclick="eventoInicializar()"> </td>
			<td class="linhabotao" align="center"><input type="button" id="botaoConcluir" name="botaoConcluir" value="Concluir" onclick="eventoConcluir()"> </td>
			<td class="linhabotao" align="left"><input type="button" id="botaoApurar" name="botaoApurar" value="Apurar" onclick="eventoApurar()"> </td>
		</tr>
	
	</table>
	<div id="footer">
		<p>&copy; 2010. All Rights Reserved.</p>
	</div>
</form>

<%
	}catch(Exception e){
		e.printStackTrace();
	}

%>
</body>
</html>