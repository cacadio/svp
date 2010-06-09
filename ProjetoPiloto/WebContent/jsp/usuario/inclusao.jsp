<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inclusão de Usuário</title>
</head>
<body>
	<form action="" id="formInclusaoUsuario" method="post">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>Inclusão de Usuário</td>
			</tr>
			<tr>
			<td><hr> </td>
			</tr>
			<tr>
				<td>Nome</td>
				<td ><input type="text" id="nomeUsuario" name="nomeUsuario"> </td>
			</tr>
			<tr >
				<td  ></td>
			</tr>
			<tr>
				<td>Login</td>
				<td ><input type="text" id="loginUsuario" name="loginUsuario"> </td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>Senha</td>
				<td ><input type="text" id="senhaUsuario" name="senhaUsuario"> </td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>CPF</td>
				<td ><input type="text" id="cpfUsuario" name="cpfUsuario"> </td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>Tipo de Usuário</td>
				<td align="left" colspan="2">
					<select id="tipoUsuario" name="tipoUsuario">
						<option value="0">:: Selecione Uma Opção ::</option>
						<option value="1">Eleitor</option>
						<option value="1">Gestor</option>
					</select> 
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td colspan="3" align="center" ><input type="submit" id="cadastrarUsuario" value="Salvar"></td>
			</tr>
			
		</table>
	
	
	</form>
</body>
</html>