<%@page import="fbv.com.servlets.ServletVoto"%>
<%@page import="fbv.com.servlets.ServletEleicao"%>
<%@page import="fbv.com.servlets.ServletUsuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletPerfilUsuario"%>


<%@page import="fbv.com.util.InterfacePrincipal"%><html>
<%
try {
		
	String Mensagem = (String) request.getAttribute(ServletPerfilUsuario.ID_REQ_MENSAGEM);
	String nomeServlet = (String) request.getAttribute(ServletPerfilUsuario.ID_REQ_NOME_SERVLET);
	
	//Monta o action do formulário dinamicamente
	String actionFormulario = "/ProjetoEleicaoWeb/" + nomeServlet;
	String tituloTela = "";
	
	if (nomeServlet.equals(ServletPerfilUsuario.ID_REQ_NOME_SERVLET_PERFIL_USUARIO)) {
		tituloTela = "Perfil de Usuário";
	} else if (nomeServlet.equals(ServletPerfilUsuario.ID_REQ_NOME_SERVLET_OPCAO_VOTO)) {
		tituloTela = "Opção de Voto";
	} else if (nomeServlet.equals(ServletPerfilUsuario.ID_REQ_NOME_SERVLET_LOGIN)) {
		tituloTela = "Login";
	} else if (nomeServlet.equals(ServletUsuario.ID_REQ_NOME_SERVLET_USUARIO)) {
		tituloTela = "Usuário";
	} else if (nomeServlet.equals(ServletEleicao.ID_REQ_NOME_SERVLET_ELEICAO)) {
		tituloTela = "Eleição";
	} else if (nomeServlet.equals(ServletPerfilUsuario.ID_REQ_NOME_SERVLET_PERFIL_USUARIO)) {
		tituloTela = "Perfil de Usuário";
	} else if (nomeServlet.equals(ServletVoto.ID_REQ_NOME_SERVLET_VOTO)) {
		tituloTela = "Voto";
	}
	else
		tituloTela = request.getAttribute(InterfacePrincipal.ID_REQ_TITULO_PAGINA).toString();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<title><%=tituloTela%></title>
</head>
<body>
<form action="<%=actionFormulario%>" id="form_principal" method="post">
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
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<td>
				<div class="post">
					<h1 class="title"><%=tituloTela%></h1>
				</div>
			</td>
		</tr>
		<tr>
			<td class="mensagem"><%=Mensagem%></td>
		</tr>
		<tr>
			<td class="linhabotao"><input type="submit" id="botaoProsseguir" name="botaoProsseguir" value="Prosseguir" ></td>
		</tr>
		
		<div id="sidebar-bgbtm"></div>
		</div>
	</table>
	<div id="footer">
		<p>&copy; 2010. All Rights Reserved.</p>
	</div>
</form>
</body>
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>
</html>