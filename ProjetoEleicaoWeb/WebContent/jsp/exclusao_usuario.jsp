<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletPerfilUsuario"%>
<%@page import="fbv.com.negocio.PerfilUsuario"%><%@page import="fbv.com.servlets.ServletUsuario"%>
<%@page import="fbv.com.negocio.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Exclusão Usuário</title>
</head>
<%
	try{
		//Pegando parâmetros do request
		String cpfUsuario = "";
		String nomeUsuario = "";
		String loginUsuario = "";
		String senhaUsuario = "";
		Integer perfilUsuario = null;
		
		ArrayList<PerfilUsuario> perfil = null;
		//Pegando parâmetros do request
		perfil = (ArrayList<PerfilUsuario>) request.getAttribute(ServletPerfilUsuario.ID_REQ_OBJETO_PERFIL_USUARIO);
		Usuario usuario =(Usuario) request.getAttribute(ServletPerfilUsuario.ID_REQ_OBJETO_USUARIO);
		cpfUsuario = usuario.getCpf();
		nomeUsuario = usuario.getNome();
		loginUsuario = usuario.getLogin();
		senhaUsuario = usuario.getSenha();
		perfilUsuario = usuario.getPerfilUsuario().getId();
		
%>
<script type="text/javascript">

function eventoProcessarExclusao(){
	document.forms.form_principal.<%=ServletUsuario.ID_REQ_EVENTO%>.value = "<%=ServletUsuario.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO%>";
	document.forms.form_principal.submit();
}

</script>
<body>
	<form action="/ProjetoEleicaoWeb/ServletUsuario" method="post" id="form_principal">
	<table width="100%">
		<tr>
			<td>
				<input type="hidden" id="<%=ServletUsuario.ID_REQ_EVENTO%>" name="<%=ServletUsuario.ID_REQ_EVENTO%>" value="">
				Exclusão de Usuário
			</td>
		</tr>
		<tr>
			<td width="25%">
				CPF do Usuário:
			</td>
			<td>
				<input type="text" id="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" name="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" value="<%=cpfUsuario%>" readonly="readonly" ></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Nome do Usuário:
			</td>
			<td>
				<input type="text" id="<%=ServletUsuario.ID_REQ_NOME_USUARIO%>" name="<%=ServletUsuario.ID_REQ_NOME_USUARIO%>" value="<%=nomeUsuario%>" readonly="readonly"></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Login do Usuário:
			</td>
			<td>
				<input type="text" id="<%=ServletUsuario.ID_REQ_LOGIN_USUARIO%>" name="<%=ServletUsuario.ID_REQ_LOGIN_USUARIO%>" value="<%=loginUsuario%>" readonly="readonly"></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Senha do Usuário:
			</td>
			<td>
				<input type="password" id="<%=ServletUsuario.ID_REQ_SENHA_USUARIO%>" name="<%=ServletUsuario.ID_REQ_SENHA_USUARIO%>" value="<%=senhaUsuario%>" readonly="readonly"></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Perfil do Usuário:
			</td>
			<td> 
				<select name="<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>">  	
					<% 
						Iterator iteratorPerfilUsuario = null;
						iteratorPerfilUsuario = perfil.iterator(); 
					
						while(iteratorPerfilUsuario.hasNext()){
							Object perfilUsuarioGenerico = iteratorPerfilUsuario.next();
							PerfilUsuario perfilUsu = (PerfilUsuario) perfilUsuarioGenerico;
							
							String descPerfilUsuario = perfilUsu.getDescricao();
							Integer id = perfilUsu.getId();
							
							if(perfilUsuario.equals(id)){
					%>
								<option id="<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>"  value="<%= id %>" selected="selected" readonly="readonly"><%= descPerfilUsuario%> </option> <% 
							} else {					
						%>
								<option id="<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>"  value="<%= id %>"><%= descPerfilUsuario%></option> 
						<%
							}
						}
						%>                                      
           		</select> 
			</td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td align="center"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td align="center"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarExclusao()" value="Confirmar"> </td>
		</tr>
	
	</table>
	</form>
<% }catch(Exception e){
	e.printStackTrace();
}
%>
</body>
</html>