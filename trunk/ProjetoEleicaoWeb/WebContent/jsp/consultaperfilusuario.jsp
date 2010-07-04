<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.PerfilUsuario"%>
<%@page import="fbv.com.servlets.ServletPerfilUsuario"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<title>Consulta Perfil de Usuário</title>
</head>
<%
	try{
		
	//Obtem Parâmetros do request	
	ArrayList<PerfilUsuario> arrayListPerfilUsuario = (ArrayList<PerfilUsuario>) request.getAttribute(ServletPerfilUsuario.ID_REQ_ARRAY_LIST_PERFIL_USUARIO);
	
%>

<script type="text/javascript" >

function eventoConsultar() {
	document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_EVENTO%>.value = "<%=ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA%>";
	document.forms.form_principal.submit();
}

function eventoIncluir() {
	document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_EVENTO%>.value = "<%=ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_INCLUSAO%>";
	document.forms.form_principal.submit();
}

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
<body>
<form action="/ProjetoEleicaoWeb/ServletPerfilUsuario" method="post" id="form_principal">
	<input type="hidden" id="<%=ServletPerfilUsuario.ID_REQ_EVENTO%>" name="<%=ServletPerfilUsuario.ID_REQ_EVENTO%>" value="">
	<table width="100%">
		<tr>
			<th class="titulopagina">Consulta Perfil de Usuário</th>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<th class="rotulodado" width="12%">Código:</th>
			<td class="valordado"><input type="text" id="<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>" name="<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>" value="" size="8" maxlength="10">
								  <input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<th class="rotulodado" width="3%">&nbsp;&nbsp;#</th>
			<th class="rotulodado" width="27%">Código</th>
			<th class="rotulodado" width="70%">Descrição</th>
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
				<td><input type="radio" id="<%=ServletPerfilUsuario.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletPerfilUsuario.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=perfilUsuario.getId()%>"> </td>
				<td class="<%=classeLinha%>"><%=perfilUsuario.getId()%></td>
				<td class="<%=classeLinha%>"><%=perfilUsuario.getDescricao()%></td>
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
			<th class="footer" colspan="3">&nbsp;</th>
		</tr>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
			<td class="linhabotao"><input type="button" id="botaoAlterar" name="botaoAlterar" value="Alterar" onclick="eventoAlterar()"> </td>
			<td class="linhabotao"><input type="button" id="botaoExcluir" name="botaoExcluir" value="Excluir" onclick="eventoExcluir()"> </td>
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