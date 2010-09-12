<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletEleicao"%>
<%@page import="fbv.com.util.TipoEleicao"%>
<%@page import="fbv.com.negocio.Eleicao"%>
<%@page import="fbv.com.negocio.EleicaoEscolhaUnica"%>
<%@page import="fbv.com.negocio.EleicaoPontuacao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Inclusão Perfil Usuário</title>
	<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
	<script src="./js/jquery.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
</head>
<%
	try{
		//Pegando parâmetros do request
		Eleicao eleicao = (Eleicao)request.getAttribute(ServletEleicao.ID_REQ_OBJETO_ELEICAO);
		TipoEleicao tipo;
		if (eleicao instanceof EleicaoEscolhaUnica)
			tipo = TipoEleicao.ESCOLHA_UNICA;
		else
			tipo = TipoEleicao.PONTUACAO;
		
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
%>
	<script type="text/javascript">
	
	function eventoProcessarExclusao(){
		document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO %>.value = "<%=ServletEleicao.ID_REQ_EVENTO_PROCESSAR_EXCLUSAO%>";
		document.forms.form_principal.submit();
	}
	
	</script>
<body>
	<form action="/ProjetoEleicaoWeb/ServletEleicao" method="post" id="form_principal">
	<input type="hidden" id="<%=ServletEleicao.ID_REQ_EVENTO%>" name="<%=ServletEleicao.ID_REQ_EVENTO%>" value="">
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
			<li><a href="/ProjetoEleicaoWeb/ServletVoto">Voto</a></li>
		</ul>
	</div>
	<!-- end #menu -->
</div>
	<table width="80%" border="0" align="center">
		<tr>
			<td colspan="4">
				<div class="post">
					<h1 class="title">Excluir Eleição </h1>
				</div>
			</td>
		</tr>
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<input type="hidden" id="<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO%>" class="camporeadonly" value="<%=eleicao.getId()%>">
		<tr>
			<th class="td" width="12%">
				Tipo:
			</th>
			<td class="valordado">
				<input type="hidden" id="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" name="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" value="<%= tipo.value() %>" />
				<select disabled="disabled">
					<option value="<%= TipoEleicao.ESCOLHA_UNICA.value() %>" <%= tipo == TipoEleicao.ESCOLHA_UNICA ? "selected": "" %>>Escolha Única</option>
					<option value="<%= TipoEleicao.PONTUACAO.value() %>" <%= tipo == TipoEleicao.PONTUACAO ? "selected": "" %>>Pontuação</option>
				</select>
			</td>
		</tr>
		<tr>
			<th class="td" width="12%">
				Descrição:
			</th>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" value="<%= eleicao.getDescricao() %>" title="Descrição" readonly="readonly"></input>
			</td>
		</tr>
		<tr>
			<th class="td">
				Pública?
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="1" title="Pública" <%= eleicao.isPublica()? "checked=\"checked\"": ""  %> disabled="disabled">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="0" title="Pública"  <%= !eleicao.isPublica()? "checked=\"checked\"": ""  %> disabled="disabled">Não
			</td>
		</tr>
		<tr>
			<th class="td">
				Voto Aberto?
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="1" title="Voto Aberto" <%= eleicao.isVisibilidadeVoto()? "checked=\"checked\"": ""  %> disabled="disabled">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="0" title="Voto Aberto" <%= !eleicao.isVisibilidadeVoto()? "checked=\"checked\"": ""  %> disabled="disabled">Não
			</td>
		</tr>
		<tr>
			<th class="td">
				Múltiplos Votos?
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="1" title="Múltiplos Votos" <%= eleicao.isMultiplosVotos()? "checked=\"checked\"": ""  %> disabled="disabled">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="0" title="Múltiplos Votos" <%= !eleicao.isMultiplosVotos()? "checked=\"checked\"": ""  %> disabled="disabled">Não
			</td>
		</tr>
		<%
		if (eleicao.getEstado().getValor() > 1){
		%>
		<tr>
			<th class="td" width="12%">
				Data Abertura:
			</th>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" value="<%= sdt.format(eleicao.getDataAbertura()) %>" title="Data Abertura" readonly="readonly"></input>
			</td>
		</tr>
		<%
		}
		%>
		<tr>
			<th class="td" width="12%">
				Data Encerramento:
			</th>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" value="<%= sdt.format(eleicao.getDataEncerramento()) %>" title="Data Encerramento" readonly="readonly"></input>
			</td>
		</tr>
		<%
		if (tipo == TipoEleicao.ESCOLHA_UNICA){
			EleicaoEscolhaUnica eleicaoEU = (EleicaoEscolhaUnica)eleicao;
		%>
		<tbody id="trEscolhaUnica">
			<tr>
				<th class="td">
					Eleição Associada:
				</th>
				<td class="valordado">
					<select id="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>" name="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>" disabled="disabled">
						<option value="0"></option>
					<%
					@SuppressWarnings("unchecked")
					ArrayList<EleicaoEscolhaUnica> eleicoes = (ArrayList<EleicaoEscolhaUnica>)request.getAttribute(ServletEleicao.ID_REQ_ARRAY_LIST_ELEICAO);
					if (eleicoes != null){
						for(EleicaoEscolhaUnica eleicaoEscolhaUnica : eleicoes){
							if (eleicaoEscolhaUnica.getId() != eleicao.getId()){
					%>
						<option value="<%= eleicaoEscolhaUnica.getId() %>" <%= (eleicaoEU.getEleicaoPai() == null? "": (eleicaoEU.getEleicaoPai().getId() == eleicaoEscolhaUnica.getId()? "selected": "")) %>><%= eleicaoEscolhaUnica.getDescricao() %></option>
					<%
							}
						}
					}
					%>
					</select>
				</td>
			</tr>
			<tr>
				<th class="td">
					Existe Voto Nulo/Branco?
				</th>
				<td class="valordado">
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="1" title="Voto Nulo/Branco" <%= eleicaoEU.isCampoNulo()? "checked=\"checked\"": ""  %> disabled="disabled">Sim&nbsp;
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="0" title="Voto Nulo/Branco" <%= eleicaoEU.isCampoNulo()? "checked=\"checked\"": ""  %> disabled="disabled">Não
				</td>
			</tr>
			<tr>
				<th class="td">
					Percentual para Vitória:
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PERCENTUAL_VITORIA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PERCENTUAL_VITORIA_ELEICAO %>" value="<%= eleicaoEU.getPercentualVitoria() %>" title="Percentual para Vitória" readonly="readonly">
				</td>
			</tr>
		</tbody>
		<%
		}
		else {
			EleicaoPontuacao eleicaoPontuacao = (EleicaoPontuacao)eleicao;
		%>
		<tbody id="trPontuacao" style="display: none">
			<tr>
				<th class="td">
					Pontuação Mínima:
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" value="<%= eleicaoPontuacao.getPontuacaoMinima() %>" title="Pontuação Mínima" readonly="readonly">
				</td>
			</tr>
			<tr>
				<th class="td">
					Pontuação Máxima:
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" value="<%= eleicaoPontuacao.getPontuacaoMaxima() %>" title="Pontuação Máxima" readonly="readonly">
				</td>
			</tr>
			<tr>
				<th class="td">
					Intervalo da Pontuação:
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" value="<%= eleicaoPontuacao.getIntervaloPontuacao() %>" title="Intervalo da Pontuação" readonly="readonly">
				</td>
			</tr>
		</tbody>
		<%
		}
		%>
			<td class="linhabotao" align="center"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td class="linhabotao" align="center"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarExclusao()" value="Confirmar"> </td>
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
</html>