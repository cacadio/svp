<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletPerfilUsuario"%>
<%@page import="fbv.com.negocio.PerfilUsuario"%><%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<title>Alteração Usuário</title>
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

function eventoProcessarAlteracao(){
	document.forms.form_principal.<%=ServletUsuario.ID_REQ_EVENTO%>.value = "<%=ServletUsuario.ID_REQ_EVENTO_PROCESSAR_ALTERACAO%>";
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
				
<div id="header">
	<div id="logo">
		<h1><a href="#">Projeto Eleição</a></h1>
		<p>FBV - Faculdade Boa Viagem</p>
	</div>
	<!-- end #logo -->
	<div id="menu">
		<ul>
			<li class="first"><a href="/ProjetoEleicaoWeb/ServletMenu">Home</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletEleicao">Eleição</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletOpcaoVoto">Opções de Voto</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletVoto">Voto</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletUsuario">Usuário</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletPerfilUsuario">Perfil de Usuário</a></li>
		</ul>
	</div>
	<!-- end #menu -->
</div>
	<table width="80%" border="0" align="center">
		<tr>
			<td colspan="4">
				<div class="post">
					<h1 class="title">Alteração de Usuário </h1>
				</div>
			</td>
		</tr>
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<th class="td" width="22%" align="right">CPF do Usuário:</th>
			<td class="valordado"><input type="text" id="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" name="<%=ServletUsuario.ID_REQ_CPF_USUARIO%>" value="<%=cpfUsuario%>" onchange="VerificaCPF();" readonly="readonly" ></td>
		</tr>
		<tr>
			<th class="td" width="22%" align="right">Nome do Usuário:</th>
			<td class="valordado"><input type="text" id="<%=ServletUsuario.ID_REQ_NOME_USUARIO%>" name="<%=ServletUsuario.ID_REQ_NOME_USUARIO%>" value="<%=nomeUsuario%>" ></td>
		</tr>
		<tr>
			<th class="td" width="22%" align="right">	Login do Usuário:</th>
			<td class="valordado"><input type="text" id="<%=ServletUsuario.ID_REQ_LOGIN_USUARIO%>" name="<%=ServletUsuario.ID_REQ_LOGIN_USUARIO%>" value="<%=loginUsuario%>" ></td>
		</tr>
		<tr>
			<th class="td" width="22%" align="right">Senha do Usuário:</th>
			<td class="valordado"><input type="password" id="<%=ServletUsuario.ID_REQ_SENHA_USUARIO%>" name="<%=ServletUsuario.ID_REQ_SENHA_USUARIO%>" value="<%=senhaUsuario%>" ></td>
		</tr>
		<tr>
			<th class="td" width="22%" align="right">Perfil do Usuário:</th>
			<td class="valordado">
				<select name="<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>">  	
					<% 
						Iterator iterator = null;
						iterator = perfil.iterator(); 
					
						while(iterator.hasNext()){
							Object perfilUsuarioGenerico = iterator.next();
							PerfilUsuario perfilUsu = (PerfilUsuario) perfilUsuarioGenerico;
							
							String descPerfilUsuario = perfilUsu.getDescricao();
							Integer id = perfilUsu.getId();
							
							if(perfilUsuario.equals(id)){
					%>
								<option id="<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>"  value="<%= id %>" selected="selected"><%= descPerfilUsuario%></option> <% 
							} else {					
						%>
								<option id="<%=ServletUsuario.ID_REQ_ID_PERFIL_USUARIO%>"  value="<%= id %>"><%= descPerfilUsuario%></option> 
						<%
							}
						}
						%>                                      
           		</select> 
			</td>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td class="linhabotao"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarAlteracao()" value="Confirmar"> </td>
		</tr>
	</div>
		<div id="sidebar-bgbtm"></div>
		</div>
	</table>
	<div id="footer">
		<p>&copy; 2010. All Rights Reserved.</p>
	</div>
	
	</form>
<% }catch(Exception e){
	e.printStackTrace();
}
%>
</body>

<%@page import="fbv.com.servlets.ServletUsuario"%>
<%@page import="fbv.com.negocio.Usuario"%>
</html>