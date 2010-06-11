<%@ page2 language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inclusão de Eleição</title>
</head>
<body>
	<form action="" id="formInclusaoEleicao" method="post">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>Inclusão de Eleição</td>
			</tr>
			<tr>
			<td><hr> </td>
			</tr>
			<tr>
				<td>Descrição</td>
				<td align="left" colspan="2"><input type="text" id="descricaoEleicao" name="descricaoEleicao"> </td>
			</tr>
			<tr >
				<td  ></td>
			</tr>
			<tr>
				<td>Eleição Pública</td>
				<td ><input type="radio" id="isEleicaoPublica" name="isEleicaoPublica" value="S">Sim</td>
				<td ><input type="radio" id="isEleicaoPublica" name="isEleicaoPublica" value="N" >Não</td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>Votos Nulos</td>
				<td ><input type="radio" id="hasVotosNulos" name="hasVotosNulos" value="S">Sim</td>
				<td ><input type="radio" id="hasVotosNulos" name="hasVotosNulos" value="N" >Não</td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>Data Início</td>
				<td align="left" colspan="2"><input type="text" id="dataInicioEleicao" name="dataInicioEleicao"> </td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>Data Final</td>
				<td align="left" colspan="2"><input type="text" id="dataFinalEleicao" name="dataFinalEleicao"> </td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>Tipo da Eleição</td>
				<td align="left" colspan="2">
					<select id="tipoEleicao" name="tipoEleicao">
						<option value="0">:: Selecione Uma Opção ::</option>
						<option value="1">Enquete</option>
					</select> 
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>Estado da Eleição</td>
				<td align="left" colspan="2">
					<select id="estadoEleicao" name="estadoEleicao">
						<option value="0">:: Selecione Uma Opção ::</option>
						<option value="1">Iniciada</option>
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
				<td colspan="3" align="center" ><input type="submit" id="cadastrarEleicao" value="Salvar"></td>
			</tr>
			
		</table>
	
	
	</form>
</body>
</html>