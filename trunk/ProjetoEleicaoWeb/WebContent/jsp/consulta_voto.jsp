<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.Voto"%>
<%@page import="fbv.com.servlets.ServletVoto"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<script type="text/javascript" src="js/biblioteca_funcoes_eleicao.js" ></script>
<title>Consulta Voto</title>
</head>
<%
	try{
		
	//Obtem Parâmetros do request	
	ArrayList<Voto> arrayListVoto = (ArrayList<Voto>) request.getAttribute(ServletVoto.ID_REQ_ARRAY_LIST_VOTO);
	
%>

<body onload="setarFoco(document.forms.form_principal.<%=ServletVoto.ID_REQ_ID_VOTO%>)">
<form action="/ProjetoEleicaoWeb/ServletVoto" method="post" id="form_principal">
<input type="hidden" id="<%=ServletVoto.ID_REQ_EVENTO%>" name="<%=ServletVoto.ID_REQ_EVENTO%>" value="">
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
			<li><a href="/ProjetoEleicaoWeb/ServletUsuario">Usuário</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletPerfilUsuario">Perfil de Usuário</a></li>
		</ul>
	</div>
	<!-- end #menu -->
</div>
	<table width="80%" border="0" align="center">
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<td colspan="4">
				<div class="post">
					<h1 class="title">Consulta Voto</h1>
				</div>
			</td>
		</tr>
		<tr>
			<th class="td" width="22%">Código do Voto:</th>	
			<td><input type="text" id="<%=ServletVoto.ID_REQ_ID_VOTO%>" name="codigoName" value="" size="16" maxlength="10">
								  <input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"></td>
		</tr>
		</table>
	
	<table width="80%" border="0" align="center">
		<th class="td" width="3%">&nbsp;&nbsp;&nbsp;#</th>
		<th class="td" align="right">Usuário: </td>
		<th class="td" align="right">Código Eleição: </td>
		<th class="td" align="right">Opção de Voto: </td>
		<th class="td" align="right">Valor Voto: </td>
		<th class="td" align="right">Data: </td>
		<%
		//Exibindo dados
		if(arrayListVoto != null && !arrayListVoto.isEmpty()){
			String checked = null;
			String classeLinha = "";
			for(int i = 0 ; i < arrayListVoto.size() ; i++){
				
				checked = "";
				Voto voto = arrayListVoto.get(i);
				
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
				<td class="<%=classeLinha%>"><input type="radio" id="<%=ServletVoto.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletVoto.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=voto.getIdVoto()%>"> </td>
				<td class="<%=classeLinha%>"><%=voto.getNomeUsuario()%></td>
				<td class="<%=classeLinha%>"><%=voto.getIdEleicao()%></td>
				<td class="<%=classeLinha%>"><%=voto.getDescricaoOpcaoVoto()%></td>
				<td class="<%=classeLinha%>"><%=voto.getValorVoto()%></td>
				<td class="<%=classeLinha%>"><%=voto.getDataHora()%></td>
				
			</tr>
		<%
			}
		}else{
			%>
			<%
		}
		%>
			<tr align="center">
				<td class="linhabotao"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Votar" onclick="eventoIncluir()"> </td>
			</tr>
			</div>
		<div id="sidebar-bgbtm"></div>
		</div>
		</table>
		<div id="footer">
		<p>&copy; 2008. All Rights Reserved.</p>
	</div>

</form>

<%
	}catch(Exception e){
		e.printStackTrace();
	}

%>
</body>
</html>