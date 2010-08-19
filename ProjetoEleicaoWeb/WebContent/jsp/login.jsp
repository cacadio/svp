<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="fbv.com.servlets.ServletLogin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Efetuar login</title>
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
<body vlink="center">
<form action="/ProjetoEleicaoWeb/ServletLogin" method="post" id="form_principal"> 
<input type="hidden" id="<%=ServletLogin.ID_REQ_EVENTO%>" name="<%=ServletLogin.ID_REQ_EVENTO%>" value="">
<input type="hidden" id="<%=ServletLogin.ID_REQ_ID_ELEICAO%>" name="<%=ServletLogin.ID_REQ_ID_ELEICAO%>" value="<%=idEleicao%>">
<input type="hidden" id="<%=ServletLogin.ID_REQ_TIPO_DE_ELEICAO%>" name="<%=ServletLogin.ID_REQ_TIPO_DE_ELEICAO%>" value="<%=tpElicao%>">
	<table width="100%" border="1" align="center">
		<tr>
			<td>Login: </td>
			<td>
				<input type="text" id="<%=ServletLogin.ID_REQ_LOGIN%>" name="<%=ServletLogin.ID_REQ_LOGIN%>" value="">
			</td>
		</tr>
		<tr>
			<td>Senha: </td>
			<td>
				<input type="password" id="<%=ServletLogin.ID_REQ_SENHA_USUARIO%>" name="<%=ServletLogin.ID_REQ_SENHA_USUARIO%>" value="">
			</td>
		</tr>
		<tr>
			<td align="center"><input type="button" id="botaoLogar" name="botaoLogar" onclick="eventoLogar()" value="Logar" a ></td>
		</tr>
	</table>
</form>
<%
	}catch(Exception e){
		e.printStackTrace();
	}
%>
</body>
</html>