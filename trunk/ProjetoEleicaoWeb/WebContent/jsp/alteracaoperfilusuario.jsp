<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletPerfilUsuario"%>
<%@page import="fbv.com.negocio.PerfilUsuario"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<title>Alteração Perfil Usuário</title>
</head>
<%
try {
	//Pegando parâmetros do request
	PerfilUsuario perfilUsuario =(PerfilUsuario) request.getAttribute(ServletPerfilUsuario.ID_REQ_OBJETO_PERFIL_USUARIO);
	
%>
<script type="text/javascript">

function eventoProcessarAlteracao() {
	document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_EVENTO%>.value = "<%=ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_ALTERACAO%>";
	document.forms.form_principal.submit();
}

</script>
<body>
<form action="/ProjetoEleicaoWeb/ServletPerfilUsuario" method="post" id="form_principal">
	<input type="hidden" id="<%=ServletPerfilUsuario.ID_REQ_EVENTO%>" name="<%=ServletPerfilUsuario.ID_REQ_EVENTO%>" value="">
	<table width="100%">
		<tr>
			<th class="titulopagina">Alteração Perfil de Usuário</th>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<th class="rotulodado" width="12%">Código:</th>
			<td class="valordado"><input type="text" id="<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>" name="<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>" class="camporeadonly" value="<%=perfilUsuario.getId()%>" readonly="readonly" size="8"></td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">Descrição:</th>
			<td class="valordado"><input type="text" id="<%=ServletPerfilUsuario.ID_REQ_DESCRICAO_PERFIL_USUARIO%>" name="<%=ServletPerfilUsuario.ID_REQ_DESCRICAO_PERFIL_USUARIO%>" value="<%=perfilUsuario.getDescricao()%>" size="45" maxlength="45"></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<th class="footer" colspan="2">&nbsp;</th>
		</tr>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td class="linhabotao"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarAlteracao()" value="Confirmar"> </td>
		</tr>
	</table>
</form>
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>
</body>
</html>