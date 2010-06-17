<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletOpcaoVoto"%>
<%@page import="fbv.com.negocio.OpcaoVoto"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alteração de Opção de Voto</title>
</head>
<%
try {
	//Pegando parâmetros do request
	OpcaoVoto opcaoVoto =(OpcaoVoto) request.getAttribute(ServletOpcaoVoto.ID_REQ_OBJETO_OPCAO_VOTO);
		
%>
<script type="text/javascript">

function eventoProcessarAlteracao(){
	if (document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>.value == "") {
		alert("Campo Obrigatório - Descrição da Opção de Voto!");
		return false;
	}

	if (document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_CODIGO_ELEICAO%>.value == "") {
		alert("Campo Obrigatório - Código da Eleição!");
		return false;
	}
	
	document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_EVENTO%>.value = "<%=ServletOpcaoVoto.ID_REQ_EVENTO_PROCESSAR_ALTERACAO%>";
	document.forms.form_principal.submit();
}

</script>

<body>
	<form action="/ProjetoEleicaoWeb/ServletOpcaoVoto" method="post" id="form_principal">
	<table width="100%">
		<tr>
			<td>
				<input type="hidden" id="<%=ServletOpcaoVoto.ID_REQ_EVENTO%>" name="<%=ServletOpcaoVoto.ID_REQ_EVENTO%>" value="">
				Alteração de Opção de Voto
			</td>
		</tr>
		<tr>
			<td width="25%">
				Código da Opção de Voto:
			</td>
			<td>
				<input type="text" id="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" value="<%=opcaoVoto.getId()%>" readonly="readonly" ></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Descrição da Opção de Voto:
			</td>
			<td>
				<input type="text" id="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" value="<%=opcaoVoto.getDescricao()%>" ></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Código da Eleição:
			</td>
			<td>
				<input type="text" id="<%=ServletOpcaoVoto.ID_REQ_CODIGO_ELEICAO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_ELEICAO%>" value="<%=opcaoVoto.getIdEleicao()%>" ></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Pasta da Foto:
			</td>
			<td>
				<% 
					String pathFoto = "";
				
				    if ((opcaoVoto.getPath_foto() != null) && (!opcaoVoto.getPath_foto().equals("null"))) {
				        pathFoto = opcaoVoto.getPath_foto();
				    }
				%>
				<input type="text" id="<%=ServletOpcaoVoto.ID_REQ_PATH_FOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_PATH_FOTO%>" value="<%=pathFoto%>" ></input>
			</td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td align="center"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td align="center"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarAlteracao()" value="Confirmar"> </td>
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