<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.OpcaoVoto"%>
<%@page import="fbv.com.servlets.ServletOpcaoVoto"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<script type="text/javascript" src="js/biblioteca_funcoes_eleicao.js" ></script>
<title>Consulta Opção de Voto</title>
</head>
<%
try{
	//Obtem Parâmetros do request	
	ArrayList<OpcaoVoto> arrayListOpcaoVoto = (ArrayList<OpcaoVoto>) request.getAttribute(ServletOpcaoVoto.ID_REQ_ARRAY_LIST_OPCAO_VOTO);
	
%>

<script type="text/javascript" >

function eventoAlterar() {
<%
	if (arrayListOpcaoVoto != null && arrayListOpcaoVoto.size() > 0) {
%>

		document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_EVENTO%>.value = "<%=ServletOpcaoVoto.ID_REQ_EVENTO_EXIBIR_ALTERACAO%>";
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

function eventoExcluir() {
<%
	if(arrayListOpcaoVoto != null && arrayListOpcaoVoto.size() > 0){
%>
		document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_EVENTO%>.value = "<%=ServletOpcaoVoto.ID_REQ_EVENTO_EXIBIR_EXCLUSAO%>";
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

</script>
<body onload="setarFoco(document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>)">
<form action="/ProjetoEleicaoWeb/ServletOpcaoVoto" method="post" id="form_principal">
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
			<li><a href="/ProjetoEleicaoWeb/ServletUsuario">Usuário</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletPerfilUsuario">Perfil de Usuário</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletLogin">Login</a></li>
		</ul>
	</div>
	<!-- end #menu -->
</div>
	<input type="hidden" id="<%=ServletOpcaoVoto.ID_REQ_EVENTO%>" name="<%=ServletOpcaoVoto.ID_REQ_EVENTO%>" value="">
	<table width="80%" align="center">
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<td colspan="8">
				<div class="post">
					<h1 class="title">Consulta Opção de Voto</h1>
				</div>
			</td>
		</tr>
		<tr>
			<td class="td" width="12%">Código:</th>
			<td colspan="4"><input type="text" id="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" value="" size="8" maxlength="10">
								  <input type="button" id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
		</tr>
		<tr>
			<th class="td" width="3%">&nbsp;&nbsp;&nbsp;#</th>
			<th class="td" width="12%">Código</th>
			<th class="td" width="40%">Descrição</th>
			<th class="td" width="10">Eleição</th>
			<th class="td" width="35%">Foto</th>
		</tr>
	<%
		//Exibindo dados
		if ((arrayListOpcaoVoto != null) && (!arrayListOpcaoVoto.isEmpty())) {
			String checked = null;
			String classeLinha = "";
			
			for(int i = 0 ; i < arrayListOpcaoVoto.size() ; i++) {
				checked = "";
				OpcaoVoto opcaoVoto = arrayListOpcaoVoto.get(i);
				
				if (i == 0) {
					checked="checked";
				}
				
				if (i % 2 == 0) {
					classeLinha = "linhaimpar";
				} else {
					classeLinha = "linhapar";
				}
	%>
				<tr>
					<td class="<%=classeLinha%>"><input type="radio" id="<%=ServletOpcaoVoto.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletOpcaoVoto.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=opcaoVoto.getId()%>"> </td>
					<td class="<%=classeLinha%>"><%=opcaoVoto.getId()%></td>
					<td class="<%=classeLinha%>"><%=opcaoVoto.getDescricao()%></td>
					<td class="<%=classeLinha%>"><%=opcaoVoto.getIdEleicao()%></td>
				<% 
					String pathFoto = "";
				
				    if ((opcaoVoto.getPath_foto() != null) && (!opcaoVoto.getPath_foto().equals("null"))) {
				        pathFoto = opcaoVoto.getPath_foto();
				    }
				%>
					<td class="<%=classeLinha%>"><%=pathFoto%></td>
				</tr>
	<%
			}
		} else {
	%>
		<tr>
			<td colspan="4">Nenhum Registro Encontrado</td>
		</tr>	
	<%
		}
	%>
	</div>
		<div id="sidebar-bgbtm"></div>
		</div>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
			<td class="linhabotao"><input type="button" id="botaoAlterar" name="botaoAlterar" value="Alterar" onclick="eventoAlterar()"> </td>
			<td class="linhabotao"><input type="button" id="botaoExcluir" name="botaoExcluir" value="Excluir" onclick="eventoExcluir()"> </td>
		</tr>
		</div>
		<div id="sidebar-bgbtm"></div>
		</div>
	</table>
	<div id="footer">
		<p>&copy; 2008. All Rights Reserved.</p>
	</div>
	
</form>
<%
	}catch(Exception e){
		e.printStackTrace();
	}
%>
</body>
</html>