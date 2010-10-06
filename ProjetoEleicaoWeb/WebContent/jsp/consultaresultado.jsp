<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.Voto"%>
<%@page import="fbv.com.servlets.ServletVoto"%>

<%@page import="fbv.com.negocio.ResultadoEleicao"%>
<%@page import="fbv.com.servlets.ServletEleicao"%>
<%@page import="fbv.com.negocio.Eleicao"%>
<%@page import="fbv.com.negocio.ResultadoOpcaoVoto"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%><html>
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
		ResultadoEleicao resultado = (ResultadoEleicao) request.getAttribute(ServletEleicao.ID_REQ_RESULTADO);
		ArrayList<Eleicao> arrayEleicao = (ArrayList<Eleicao>) request.getAttribute(ServletEleicao.ID_REQ_ARRAY_LIST_ELEICAO);
	
%>

<body onload="setarFoco(document.forms.form_principal.<%=ServletVoto.ID_REQ_ID_ELEICAO%>)">
	<form action="/ProjetoEleicaoWeb/ServletResultado" method="post" id="form_principal">
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
				<li><a href="/ProjetoEleicaoWeb/ServletVoto">Voto</a></li>
				<li><a href="/ProjetoEleicaoWeb/ServletResultado">Resultado</a></li>
				<li><a href="/ProjetoEleicaoWeb/ServletUsuario">Usuário</a></li>
				<li><a href="/ProjetoEleicaoWeb/ServletPerfilUsuario">Perfis</a></li>
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
					<h1 class="title">Resultado Eleição</h1>
				</div>
			</td>
		</tr>
		<tr>
			<th class="td" width="22%">Eleição:</th>	
			<td>
				<select id="<%=ServletVoto.ID_REQ_ID_ELEICAO%>" name="<%=ServletVoto.ID_REQ_ID_ELEICAO%>">
					<option value="0"></option>
				<%
				for (Eleicao eleicao : arrayEleicao){
					%>
					<option value="<%= eleicao.getId() %>" <%= eleicao.getId() == Integer.parseInt(request.getAttribute(ServletEleicao.ID_REQ_ID_ELEICAO).toString())? "selected=\"selected\"": "" %>><%= eleicao.getDescricao() %></option>
					<%
				}
				%>
				</select>
				<input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar">
			</td>
		</tr>
	</table>
	
	<table width="80%" border="0" align="center">
		<tr>
			<th class="td" width="3%" align="center">&nbsp;&nbsp;&nbsp;#</th>
			<th class="td" align="center">Opção de Voto</th>
			<th class="td" align="center" width="20%">Total de Votos</th>
			<th class="td" align="center" width="20%">Percentual</th>
		</tr>
		<%
		//Exibindo dados
		if (resultado != null){
			String classeLinha = "";
			int i = 0;
			for(ResultadoOpcaoVoto resultadoOpcao: resultado.getResultadoOpcoes()){
				i++;
				if (i % 2 == 0) {
					classeLinha = "linhaimpar";
				} else {
					classeLinha = "linhapar";
				}
				NumberFormat nf = new DecimalFormat("0.00");
		%>
			<tr>
				<td class="<%=classeLinha%>" align="center"><%=i%></td>
				<td class="<%=classeLinha%>"><%=resultadoOpcao.getOpcaoVoto().getDescricao()%></td>
				<td class="<%=classeLinha%>" align="right"><%=resultadoOpcao.getTotalVotos()%></td>
				<td class="<%=classeLinha%>" align="right"><%= nf.format(resultadoOpcao.getPercentualVotos())%></td>
			</tr>
		<%
			}
			if (resultado.getEleicao2Turno() != null){
			%>
			<tr>
				<td colspan="3">
				<br/>
				<b>Esta Eleição irá para segundo turno.</b><br/><br/>
				Eleição de Segundo Turno criada: <br/>
				<b>&nbsp;&nbsp;- Código:</b> <%= resultado.getEleicao2Turno().getId() %><br/>
				<b></br>&nbsp;&nbsp;- Descrição:</b> <%= resultado.getEleicao2Turno().getDescricao() %><br/><br/>
				Os dois primeiros candidatos mais votados participarão do segundo turno.
				</td>
			</tr>
			<%
			}
		}
		%>
	</div>
	<div id="sidebar-bgbtm"></div>
	</div>
	</table>
	<div id="footer">
		<p>&copy; 2010. All Rights Reserved.</p>
	</div>

	</form>

<%
	}catch(Exception e){
		e.printStackTrace();
	}

%>
</body>
</html>