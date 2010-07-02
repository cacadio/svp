<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletUsuario"%><%@page import="fbv.com.negocio.PerfilUsuario"%>
<%@page import="fbv.com.servlets.ServletPerfilUsuario"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<title>Inclusão Usuário</title>
</head>
<%
	try{

		ArrayList<PerfilUsuario> perfilUsuario = null;
		//Pegando parâmetros do request
		perfilUsuario = (ArrayList<PerfilUsuario>) request.getAttribute(ServletPerfilUsuario.ID_REQ_OBJETO_PERFIL_USUARIO);
%>
<script type="text/javascript">

function eventoProcessarInclusao(){
	if(document.forms.form_principal.<%=ServletUsuario.ID_REQ_CPF_USUARIO%>.value == ""){
		alert("Campo Obrigatório - CPF do Usuário!");
		return false;
	}
	if(document.forms.form_principal.<%=ServletUsuario.ID_REQ_NOME_USUARIO%>.value == ""){
		alert("Campo Obrigatório - Nome do Usuário!");
		return false;
	}
	if(document.forms.form_principal.<%=ServletUsuario.ID_REQ_LOGIN_USUARIO%>.value == ""){
		alert("Campo Obrigatório - Login do Usuário!");
		return false;
	}
	if(document.forms.form_principal.<%=ServletUsuario.ID_REQ_SENHA_USUARIO%>.value == ""){
		alert("Campo Obrigatório - Senha do Usuário!");
		return false;
	}
	if(document.forms.form_principal.<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>.value == ""){
		alert("Campo Obrigatório - Perfil do Usuário!");
		return false;
	}
	document.forms.form_principal.<%=ServletUsuario.ID_REQ_EVENTO%>.value = "<%=ServletUsuario.ID_REQ_EVENTO_PROCESSAR_INCLUSAO%>";
	document.forms.form_principal.submit();
}

function VerificaCPF () {
	if (!vercpf(document.forms.form_principal.cpf.value)) {
	  
		errors="1";
		if (errors) 
		alert('CPF NÃO VÁLIDO');
			document.forms.form_principal.cpf.value = "";
			document.retorno = (errors == '');
	}
}
function vercpf (cpf) {
	if (cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999")
		return false;
		add = 0;
		for (i=0; i < 9; i ++)
			add += parseInt(cpf.charAt(i)) * (10 - i);
			rev = 11 - (add % 11);
			if (rev == 10 || rev == 11)
				rev = 0;
			if (rev != parseInt(cpf.charAt(9)))
				return false;
				add = 0;
			for (i = 0; i < 10; i ++)
				add += parseInt(cpf.charAt(i)) * (11 - i);
				rev = 11 - (add % 11);
			if (rev == 10 || rev == 11)
				rev = 0;
			if (rev != parseInt(cpf.charAt(10)))
				return false;
				document.retorno = (errors == '');
				return true;
}
</script>
<body>
<form action="/ProjetoEleicaoWeb/ServletUsuario" method="post" id="form_principal">
<input type="hidden" id="<%=ServletUsuario.ID_REQ_EVENTO%>" name="<%=ServletUsuario.ID_REQ_EVENTO%>" value="">	
	<table width="100%">
		<tr>
			<th class="titulopagina">Inclusão de Usuário</th>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<th class="rotulodado" with="12%">CPF do Usuário</th>
			<td class="valordado"><input type="text" maxlength="11" id="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" name="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" value="" onchange="VerificaCPF();"> *</td>
		</tr>
		<tr>
			<th class="rotulodado"with="12%">Nome do Usuário:</th>
			<td class="valordado"><input type="text" id="<%=ServletUsuario.ID_REQ_NOME_USUARIO%>" name="<%=ServletUsuario.ID_REQ_NOME_USUARIO%>" value="" > *</td>
		</tr>
		<tr>
			<th class="rotulodado" with="12%">Login do Usuário:</th>
			<td class="valordado"><input type="text" id="<%=ServletUsuario.ID_REQ_LOGIN_USUARIO%>" name="<%=ServletUsuario.ID_REQ_LOGIN_USUARIO%>" value="" > *</td>
		</tr>
		<tr>
			<th class="rotulodado" with="12%">Senha do Usuário:</th>
			<td class="valordado"><input type="password" id="<%=ServletUsuario.ID_REQ_SENHA_USUARIO%>" name="<%=ServletUsuario.ID_REQ_SENHA_USUARIO%>" value="" > *</td>
		</tr>
		<tr>
			<th class="rotulodado" with="12%">Perfil do Usuário:</th>
			<td class="valordado"> <select name="<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>">  
				
						<% 
						Iterator iterator = null;
						iterator = perfilUsuario.iterator(); 
					
						while(iterator.hasNext()){
							Object perfilUsuarioGenerico = iterator.next();
							PerfilUsuario perfilUsu = (PerfilUsuario) perfilUsuarioGenerico;
							
							String descPerfilUsuario = perfilUsu.getDescricao();
							Integer id = perfilUsu.getId();
						 %>           
  							<option id="<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>"  value="<%= id %>"><%= descPerfilUsuario%></option>  
             			<% } %>                                       
           		</select> 
			</td>
		</tr>
		<tr> 
		
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
<% }catch(Exception e){
	e.printStackTrace();
}
%>
</body>
</html>