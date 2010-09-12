<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.Usuario"%>
<%@page import="fbv.com.servlets.ServletUsuario"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<script type="text/javascript" src="js/biblioteca_funcoes_eleicao.js" ></script>

<title>Consulta Usuário</title>
</head>
<%
	try{
		String checked = null;
	//Obtem Parâmetros do request	
	ArrayList<Usuario> arrayListUsuario = (ArrayList<Usuario>) request.getAttribute(ServletUsuario.ID_REQ_ARRAY_LIST_USUARIO);
	
%>

<script type="text/javascript" >

function eventoAlterar(){
	<%
	if(arrayListUsuario != null && arrayListUsuario.size() > 0){
	%>

		document.forms.form_principal.<%=ServletUsuario.ID_REQ_EVENTO%>.value = "<%=ServletUsuario.ID_REQ_EVENTO_EXIBIR_ALTERACAO%>";
		document.forms.form_principal.submit();
	<%
	}else{	
	%>
		alert("Nenhum Registro Selecionado!");
		return false;
	<%
	}
	%>
}

function eventoExcluir(){
	<%
	if(arrayListUsuario != null && arrayListUsuario.size() > 0){
	%>

		document.forms.form_principal.<%=ServletUsuario.ID_REQ_EVENTO%>.value = "<%=ServletUsuario.ID_REQ_EVENTO_EXIBIR_EXCLUSAO%>";
		document.forms.form_principal.submit();
	<%
	}else{	
	%>
		alert("Nenhum Registro Selecionado!");
		return false;
	<%
	}
	%>
}
</script>
<body onload="setarFoco(document.forms.form_principal.<%=ServletUsuario.ID_REQ_CPF_USUARIO%>)">
<form action="/ProjetoEleicaoWeb/ServletUsuario" method="post" id="form_principal">
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
			<li><a href="/ProjetoEleicaoWeb/ServletUsuario">Usuário</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletPerfilUsuario">Perfil de Usuário</a></li>
		</ul>
	</div>
	<!-- end #menu -->
</div>
<input type="hidden" id="<%=ServletUsuario.ID_REQ_EVENTO%>" name="<%=ServletUsuario.ID_REQ_EVENTO%>" value="">
				
	<table width="80%" align="center" border="0">
		<tr>
			<td colspan="4">
				<div class="post">
					<h1 class="title">Consultar Usuário </h1>
				</div>
			</td>
		</tr>
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
			<tr>
				<th class="td" width="30%" colspan="2">CPF do Usuário:</th>
				<td><input type="text" id="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" name="cpfName" value="" size="16" maxlength="10">
					<input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
			</tr>
		<tr>
		</table>
		<table width="80%" align="center" border="0">
				<th class="td" align="center"><b>#</b></th>
				<th class="td"><b>CPF</b></td>
				<th class="td"><b>Login</b></td>
				<th class="td"><b>Nome</b></td>
				<th class="td"><b>Perfil</b></td>
			</tr>
		<%
		//Exibindo dados
		if(arrayListUsuario != null && !arrayListUsuario.isEmpty()){
			
			for(int i = 0 ; i < arrayListUsuario.size() ; i++){
				
				checked = "";
				String classeLinha = "";
				Usuario Usuario = arrayListUsuario.get(i);
				
				if(i == 0){
					checked="checked";
				}
				if (i % 2 == 0) {
					classeLinha = "linhaimpar";
				} else {
					classeLinha = "linhapar";
				}
				
		%>
			
			<tr>
				<td class="<%=classeLinha%>"><input type="radio" id="<%=ServletUsuario.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletUsuario.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=Usuario.getCpf()%>"> </td>
				<td class="<%=classeLinha%>"><%=Usuario.getCpf()%></td>
				<td class="<%=classeLinha%>"><%=Usuario.getLogin()%></td>
				<td class="<%=classeLinha%>"><%=Usuario.getNome()%></td>
				<td class="<%=classeLinha%>"><%=Usuario.getPerfilUsuario().getDescricao()%></td>
			</tr>
		<%
			}
		}else{
		%>
		
		<%
		}
		%>
	</div>
		<div id="sidebar-bgbtm"></div>
		</div>		
	</table>
	<table width="80%" border="0" align="center">
		<tr>
			<td class="linhabotao" align="right"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
			<td class="linhabotao" align="center"><input type="button" id="botaoAlterar" name="botaoAlterar" value="Alterar" onclick="eventoAlterar()"> </td>
			<td class="linhabotao" align="left"><input type="button" id="botaoExcluir" name="botaoExcluir" value="Excluir" onclick="eventoExcluir()"> </td>
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