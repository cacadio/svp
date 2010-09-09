<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.PerfilUsuario"%>
<%@page import="fbv.com.servlets.ServletPerfilUsuario"%>


<%@page import="fbv.com.negocio.Usuario"%>
<%@page import="fbv.com.servlets.ServletLogin"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<script type="text/javascript" src="js/biblioteca_funcoes_eleicao.js" ></script>
<title>Consulta Perfil de Usuário</title>
</head>
<%
	try{
	//Obtem Parâmetros do request	
	ArrayList<PerfilUsuario> arrayListPerfilUsuario = (ArrayList<PerfilUsuario>) request.getAttribute(ServletPerfilUsuario.ID_REQ_ARRAY_LIST_PERFIL_USUARIO);
	
	Object idUsuario = request.getAttribute(ServletLogin.ID_REQ_ID_USUARIO);
	
	/*if(idUsuario == null || idUsuario.equals("")){
		out.println("nao tem nada");
	}else{
		out.println(idUsuario);
	}*/
%>

<script type="text/javascript" >

function eventoAlterar() {
	<%
	if (arrayListPerfilUsuario != null && arrayListPerfilUsuario.size() > 0) {
	%>

		document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_EVENTO%>.value = "<%=ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_ALTERACAO%>";
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

function eventoExcluir(){
	<%
	if(arrayListPerfilUsuario != null && arrayListPerfilUsuario.size() > 0){
	%>

		document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_EVENTO%>.value = "<%=ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_EXCLUSAO%>";
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
<body onload="setarFoco(document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>)">
<form action="/ProjetoEleicaoWeb/ServletPerfilUsuario" method="post" id="form_principal">
	<input type="hidden" id="<%=ServletPerfilUsuario.ID_REQ_EVENTO%>" name="<%=ServletPerfilUsuario.ID_REQ_EVENTO%>" value="">
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
		</ul>
	</div>
	<!-- end #menu -->
</div>
	<table width="80%" border="0" align="center">
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<td colspan="4">
				<div class="post">
					<h1 class="title">Consulta Perfil de Usuário</h1>
				</div>
			</td>
		</tr>
		<tr>
			<th class="td" width="22%">Código:</th>
			<td><input type="text" id="<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>" name="<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>" value="" size="8" maxlength="10">
								  <input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
		</tr>
		<tr>
			<th class="td" width="3%">&nbsp;&nbsp;#</th>
			<th class="td" width="27%">Código</th>
			<th class="td" width="70%">Descrição</th>
		</tr>
		<%
		//Exibindo dados
		if(arrayListPerfilUsuario != null && !arrayListPerfilUsuario.isEmpty()){
			String checked = null;
			String classeLinha = "";
			
			for(int i = 0 ; i < arrayListPerfilUsuario.size() ; i++){
				
				checked = "";
				
				PerfilUsuario perfilUsuario = arrayListPerfilUsuario.get(i);
				
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
				<td class="<%=classeLinha%>"><input type="radio" id="<%=ServletPerfilUsuario.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletPerfilUsuario.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=perfilUsuario.getId()%>"> </td>
				<td class="<%=classeLinha%>"><%=perfilUsuario.getId()%></td>
				<td class="<%=classeLinha%>"><%=perfilUsuario.getDescricao()%></td>
			</tr>
		<%
			}
		} else {
		%>
		<%
		}
		%>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
			<td class="linhabotao"><input type="button" id="botaoAlterar" name="botaoAlterar" value="Alterar" onclick="eventoAlterar()"> </td>
			<td class="linhabotao"><input type="button" id="botaoExcluir" name="botaoExcluir" value="Excluir" onclick="eventoExcluir()"> </td>
		</tr>
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