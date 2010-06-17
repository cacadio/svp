<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.PerfilUsuario"%>
<%@page import="fbv.com.servlets.ServletPerfilUsuario"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta Perfil de Usuário</title>
</head>
<%
	try{
		
	//Obtem Parâmetros do request	
	ArrayList<PerfilUsuario> arrayListPerfilUsuario = (ArrayList<PerfilUsuario>) request.getAttribute(ServletPerfilUsuario.ID_REQ_ARRAY_LIST_PERFIL_USUARIO);
	
%>

<script type="text/javascript" >

function eventoConsultar(){
	document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_EVENTO%>.value = "<%=ServletPerfilUsuario.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA%>";
	document.forms.form_principal.submit();
}

function eventoIncluir(){
	document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_EVENTO%>.value = "<%=ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_INCLUSAO%>";
	document.forms.form_principal.submit();
}

function eventoAlterar(){
	<%
	if(arrayListPerfilUsuario != null && arrayListPerfilUsuario.size() > 0){
	%>

		document.forms.form_principal.<%=ServletPerfilUsuario.ID_REQ_EVENTO%>.value = "<%=ServletPerfilUsuario.ID_REQ_EVENTO_EXIBIR_ALTERACAO%>";
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
	<table width="100%" border="1">
		
		<tr>
			<td>
				<input type="hidden" id="<%=ServletPerfilUsuario.ID_REQ_EVENTO%>" name="<%=ServletPerfilUsuario.ID_REQ_EVENTO%>" value="">
				Consulta Perfil de Usuário
			</td>
		</tr>
		<tr>
			<td>
				Código do Perfil:
			</td>
			<td>
				<input type="text" id="<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>" name="<%=ServletPerfilUsuario.ID_REQ_CODIGO_PERFIL_USUARIO%>" value="" ></input>
			</td>
			<td align="center"><input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
		</tr>
	</table>
	<table width="100%">
		<%
		//Exibindo dados
		if(arrayListPerfilUsuario != null && !arrayListPerfilUsuario.isEmpty()){
			String checked = null;
			for(int i = 0 ; i < arrayListPerfilUsuario.size() ; i++){
				
				checked = "";
				PerfilUsuario perfilUsuario = arrayListPerfilUsuario.get(i);
				
				if(i == 0){
					checked="checked";
				}
				
		%>
			<tr>
				<td><input type="radio" id="<%=ServletPerfilUsuario.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletPerfilUsuario.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=perfilUsuario.getId()%>"> </td>
				<td>Código Perfil:</td>
				<td align="left"><%=perfilUsuario.getId()%></td>
				<td>Descrição do Perfil</td>
				<td align="left"><%=perfilUsuario.getDescricao()%></td>
			</tr>
		<%
			}
		}else{
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