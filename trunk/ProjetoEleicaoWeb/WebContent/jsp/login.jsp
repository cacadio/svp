<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="fbv.com.servlets.ServletLogin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Efetuar login</title>
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
	<script type="text/javascript" src="js/biblioteca_funcoes_eleicao.js" ></script>
</head>
<%
try{
	String idEleicao = (String)request.getAttribute(ServletLogin.ID_REQ_ID_ELEICAO);
	String tpElicao = (String)request.getAttribute(ServletLogin.ID_REQ_TIPO_DE_ELEICAO);
%>
<script type="text/javascript" >

function eventoLogar() {
	document.forms.form_principal.<%=ServletLogin.ID_REQ_EVENTO%>.value = "<%=ServletLogin.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA%>";
	document.forms.form_principal.submit();
}

</script>
<body vlink="center" onload="setarFoco(document.forms.form_principal.<%=ServletLogin.ID_REQ_LOGIN %>)">
<form action="/ProjetoEleicaoWeb/ServletLogin" method="post" id="form_principal"> 
<input type="hidden" id="<%=ServletLogin.ID_REQ_EVENTO%>" name="<%=ServletLogin.ID_REQ_EVENTO%>" value="">
<input type="hidden" id="<%=ServletLogin.ID_REQ_ID_ELEICAO%>" name="<%=ServletLogin.ID_REQ_ID_ELEICAO%>" value="<%=idEleicao%>">
<input type="hidden" id="<%=ServletLogin.ID_REQ_TIPO_DE_ELEICAO%>" name="<%=ServletLogin.ID_REQ_TIPO_DE_ELEICAO%>" value="<%=tpElicao%>">
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
	<table width="80%" border="0" align="center">
		<tr>
			<td colspan="2">
				<div class="post">
					<h1 class="title">Login</h1>
				</div>
			</td>
		</tr>
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<td class="td" width="20%" align="right">Login: </td>
			<td>
				<input type="text" id="<%=ServletLogin.ID_REQ_LOGIN%>" name="<%=ServletLogin.ID_REQ_LOGIN%>" value="">
			</td>
		</tr>
		<tr>
			<td class="td" width="20%" align="right">Senha: </td>
			<td>
				<input type="password" id="<%=ServletLogin.ID_REQ_SENHA_USUARIO%>" name="<%=ServletLogin.ID_REQ_SENHA_USUARIO%>" value="">
			</td>
		</tr>
		<tr>
			<td align="center"><input type="button" id="botaoLogar" name="botaoLogar" onclick="eventoLogar()" value="Logar" a ></td>
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