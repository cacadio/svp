<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.OpcaoVoto"%>
<%@page import="fbv.com.servlets.ServletOpcaoVoto"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta Opção de Voto</title>
</head>
<%
try{
	//Obtem Parâmetros do request	
	ArrayList<OpcaoVoto> arrayListOpcaoVoto = (ArrayList<OpcaoVoto>) request.getAttribute(ServletOpcaoVoto.ID_REQ_ARRAY_LIST_OPCAO_VOTO);
	
%>

<script type="text/javascript" >

function eventoConsultar() {
	document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_EVENTO%>.value = "<%=ServletOpcaoVoto.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA%>";
	document.forms.form_principal.submit();
}

function eventoIncluir() {
	document.forms.form_principal.<%=ServletOpcaoVoto.ID_REQ_EVENTO%>.value = "<%=ServletOpcaoVoto.ID_REQ_EVENTO_EXIBIR_INCLUSAO%>";
	document.forms.form_principal.submit();
}

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
<body>
<form action="/ProjetoEleicaoWeb/ServletOpcaoVoto" method="post" id="form_principal">
	<table width="100%" border="1">
		<tr>
			<td>
				<input type="hidden" id="<%=ServletOpcaoVoto.ID_REQ_EVENTO%>" name="<%=ServletOpcaoVoto.ID_REQ_EVENTO%>" value="">
				Consulta Opção de Voto
			</td>
		</tr>
		<tr>
			<td>
				Código da Opção de Voto:
			</td>
			<td>
				<input type="text" id="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" value="" ></input>
			</td>
			<td align="center"><input type="button" id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
		</tr>
	</table>
	<table width="100%">
	<%
		//Exibindo dados
		if ((arrayListOpcaoVoto != null) && (!arrayListOpcaoVoto.isEmpty())) {
			String checked = null;
			
			for(int i = 0 ; i < arrayListOpcaoVoto.size() ; i++) {
				checked = "";
				OpcaoVoto opcaoVoto = arrayListOpcaoVoto.get(i);
				
				if (i == 0) {
					checked="checked";
				}
	%>
				<tr>
					<td width="3%"><input type="radio" id="<%=ServletOpcaoVoto.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletOpcaoVoto.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=opcaoVoto.getId()%>"> </td>
					<td width="14%">Código da Opção de Voto:</td>
					<td width="10%"><%=opcaoVoto.getId()%></td>
					<td width="18%">Descrição da Opção de Voto:</td>
					<td width="13%"><%=opcaoVoto.getDescricao()%></td>
					<td width="10%">Código da Eleição:</td>
					<td width="8%"><%=opcaoVoto.getIdEleicao()%></td>
					<td width="8%">Foto:</td>
				<% 
					String pathFoto = "";
				
				    if ((opcaoVoto.getPath_foto() != null) && (!opcaoVoto.getPath_foto().equals("null"))) {
				        pathFoto = opcaoVoto.getPath_foto();
				    }
				%>
					<td width="16%"><%=pathFoto%></td>
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
	</table>
	<table width="100%">
		<tr>
			<td align="center"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
			<td align="center"><input type="button" id="botaoAlterar" name="botaoAlterar" value="Alterar" onclick="eventoAlterar()"> </td>
			<td align="center"><input type="button" id="botaoExcluir" name="botaoExcluir" value="Excluir" onclick="eventoExcluir()"> </td>
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