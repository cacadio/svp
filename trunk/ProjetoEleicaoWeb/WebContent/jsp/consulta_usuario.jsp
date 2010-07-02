<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.Usuario"%>
<%@page import="fbv.com.servlets.ServletUsuario"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">

<title>Consulta Usuário</title>
</head>
<%
	try{
		String checked = null;
	//Obtem Parâmetros do request	
	ArrayList<Usuario> arrayListUsuario = (ArrayList<Usuario>) request.getAttribute(ServletUsuario.ID_REQ_ARRAY_LIST_USUARIO);
	
%>

<script type="text/javascript" >

function eventoConsultar(){
	document.forms.form_principal.<%=ServletUsuario.ID_REQ_EVENTO%>.value = "<%=ServletUsuario.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA%>";
	document.forms.form_principal.submit();
}

function eventoIncluir(){
	document.forms.form_principal.<%=ServletUsuario.ID_REQ_EVENTO%>.value = "<%=ServletUsuario.ID_REQ_EVENTO_EXIBIR_INCLUSAO%>";
	document.forms.form_principal.submit();
}

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
<body>
<form action="/ProjetoEleicaoWeb/ServletUsuario" method="post" id="form_principal">
<input type="hidden" id="<%=ServletUsuario.ID_REQ_EVENTO%>" name="<%=ServletUsuario.ID_REQ_EVENTO%>" value="">
				
	<table width="100%">
		<tr>
			<th class="titulopagina">Consulta Usuário</th>
		</tr>
	</table>
		<table>
			<tr>
				<th class="rotulodado" width="40%">CPF do Usuário:</th>
				<td><input type="text" id="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" name="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" value="" size="16" maxlength="10">
					<input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
			</tr>
		</table>
	<table width="100%">
	<tr>
				<th class="rotulodado" width="3%">&nbsp;&nbsp;&nbsp;#</th>
				<th class="rotulodado">CPF:</td>
				<th class="rotulodado">Login:</td>
				<th class="rotulodado">Nome:</td>
				<th class="rotulodado">Perfil:</td>
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
				<td><input type="radio" id="<%=ServletUsuario.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletUsuario.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=Usuario.getCpf()%>"> </td>
				<td class="<%=classeLinha%>"><%=Usuario.getCpf()%></td>
				<td class="<%=classeLinha%>"><%=Usuario.getLogin()%></td>
				<td class="<%=classeLinha%>"><%=Usuario.getNome()%></td>
				<td class="<%=classeLinha%>"><%=Usuario.getPerfilUsuario().getDescricao()%></td>
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