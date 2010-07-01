<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletOpcaoVoto"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<title>Inclusão de Opção de Voto</title>
</head>
<%
try {
%>
<script type="text/javascript">

function eventoProcessarInclusao() {
	if (document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>.value == "") {
		alert("Campo Obrigatório - Descrição da Opção de Voto!");
		return false;
	}

	if (document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_CODIGO_ELEICAO%>.value == "") {
		alert("Campo Obrigatório - Código da Eleição!");
		return false;
	}
	
	document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_EVENTO%>.value = "<%=ServletOpcaoVoto.ID_REQ_EVENTO_PROCESSAR_INCLUSAO%>";
	document.forms.form_principal.submit();
}

</script>
<body>
<form action="/ProjetoEleicaoWeb/ServletOpcaoVoto" method="post" id="form_principal">
	<input type="hidden" id="<%=ServletOpcaoVoto.ID_REQ_EVENTO%>" name="<%=ServletOpcaoVoto.ID_REQ_EVENTO%>" value="">
	<table width="100%">
		<tr>
			<th class="titulopagina">Inclusão de Opção de Voto</th>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<th class="rotulodado" width="12%">Descrição:</th>
			<td class="valordado"><input type="text" id="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" value="" size="50" maxlength="45"></td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">Código da Eleição:</th>
			<td class="valordado"><input type="text" id="<%=ServletOpcaoVoto.ID_REQ_CODIGO_ELEICAO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_ELEICAO%>" value="" size="8" maxlength="10"></td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">Foto:</th>
			<td class="valordado"><input type="file" id="<%=ServletOpcaoVoto.ID_REQ_PATH_FOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_PATH_FOTO%>" value="" size="40" maxlength="45"></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<th class="footer" colspan="2">&nbsp;</th>
		</tr>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td class="linhabotao"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarInclusao()" value="Confirmar"> </td>
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