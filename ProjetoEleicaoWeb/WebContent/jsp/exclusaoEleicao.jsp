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
	<script src="./js/jquery.js" type="text/javascript"></script>
</head>
<%
	try{
		//Pegando parâmetros do request
		Eleicao eleicao = (Eleicao)request.getAttribute(ServletEleicao.ID_REQ_OBJETO_PERFIL_USUARIO);
		TipoEleicao tipo;
		if (eleicao instanceof EleicaoEscolhaUnica)
			tipo = TipoEleicao.ESCOLHA_UNICA;
		else
			tipo = TipoEleicao.PONTUACAO;
		
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
%>
	<script type="text/javascript">
	
	function eventoProcessarExclusao(){
		document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO %>.value = "<%=ServletEleicao.ID_REQ_EVENTO_PROCESSAR_INCLUSAO%>";
		document.forms.form_principal.submit();
	}
	
	</script>
<body>
	<form action="/ProjetoEleicaoWeb/<%= request.getAttribute(ServletEleicao.ID_REQ_NOME_SERVLET) %>" method="post" id="form_principal">
	<table width="100%">
		<tr>
			<td align="center" colspan="2">
				<input type="hidden" id="<%=ServletEleicao.ID_REQ_EVENTO%>" name="<%=ServletEleicao.ID_REQ_EVENTO%>" value="">
				<input type="hidden" id=<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO %> name=<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO %> value="<%= eleicao.getId() %>">
				<b>Excluir Eleição</b>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Tipo:
			</td>
			<td>
				<select id="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" name="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" disabled="disabled">
					<option value="<%= TipoEleicao.ESCOLHA_UNICA.value() %>" <%= tipo == TipoEleicao.ESCOLHA_UNICA ? "selected": "" %>>Escolha Única</option>
					<option value="<%= TipoEleicao.PONTUACAO.value() %>" <%= tipo == TipoEleicao.PONTUACAO ? "selected": "" %>>Pontuação</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Descrição:
			</td>
			<td>
				<input type="text" id="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" value="<%= eleicao.getDescricao() %>" title="Descrição" readonly="readonly"></input>
			</td>
		</tr>
		<tr>
			<td>
				Pública?
			</td>
			<td>
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="1" title="Pública" <%= eleicao.isPublica()? "checked=\"checked\"": ""  %> readonly="readonly">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="0" title="Pública"  <%= !eleicao.isPublica()? "checked=\"checked\"": ""  %> readonly="readonly">Não
			</td>
		</tr>
		<tr>
			<td>
				Voto Aberto?
			</td>
			<td>
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="1" title="Voto Aberto" <%= eleicao.isVisibilidadeVoto()? "checked=\"checked\"": ""  %> readonly="readonly">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="0" title="Voto Aberto" <%= !eleicao.isVisibilidadeVoto()? "checked=\"checked\"": ""  %> readonly="readonly">Não
			</td>
		</tr>
		<tr>
			<td>
				Múltiplos Votos?
			</td>
			<td>
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="1" title="Múltiplos Votos" <%= eleicao.isMultiplosVotos()? "checked=\"checked\"": ""  %> readonly="readonly">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="0" title="Múltiplos Votos" <%= !eleicao.isMultiplosVotos()? "checked=\"checked\"": ""  %> readonly="readonly">Não
			</td>
		</tr>
		<tr>
			<td width="25%">
				Data Abertura:
			</td>
			<td>
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" value="<%= sdt.format(eleicao.getDataAbertura()) %>" title="Data Abertura" readonly="readonly"></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Data Encerramento:
			</td>
			<td>
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" value="<%= sdt.format(eleicao.getDataEncerramento()) %>" title="Data Encerramento" readonly="readonly"></input>
			</td>
		</tr>
		<%
		if (tipo == TipoEleicao.ESCOLHA_UNICA){
			EleicaoEscolhaUnica eleicaoEU = (EleicaoEscolhaUnica)eleicao;
		%>
		<tbody id="trEscolhaUnica">
			<tr>
				<td>
					Eleição Associada:
				</td>
				<td>
					<select id="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>" name="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>" disabled="disabled">
						<option value="0"></option>
					<%
					@SuppressWarnings("unchecked")
					ArrayList<EleicaoEscolhaUnica> eleicoes = (ArrayList<EleicaoEscolhaUnica>)request.getAttribute(ServletEleicao.ID_REQ_ARRAY_LIST_ELEICAO);
					for(EleicaoEscolhaUnica eleicaoEscolhaUnica : eleicoes){
						if (eleicaoEscolhaUnica.getId() != eleicao.getId()){
					%>
						<option value="<%= eleicaoEscolhaUnica.getId() %>" <%= (eleicaoEU.getEleicaoPai() == null? "": (eleicaoEU.getEleicaoPai().getId() == eleicaoEscolhaUnica.getId()? "selected": "")) %>><%= eleicaoEscolhaUnica.getDescricao() %></option>
					<%
						}
					}
					%>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					Existe Voto Nulo/Branco?
				</td>
				<td>
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="1" title="Voto Nulo/Branco" <%= eleicaoEU.isCampoNulo()? "checked=\"checked\"": ""  %> readonly="readonly">Sim&nbsp;
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="0" title="Voto Nulo/Branco" <%= eleicaoEU.isCampoNulo()? "checked=\"checked\"": ""  %> readonly="readonly">Não
				</td>
			</tr>
			<tr>
				<td>
					Percentual para Vitória:
				</td>
				<td>
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
				<td>
					Pontuação Mínima:
				</td>
				<td>
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" value="<%= eleicaoPontuacao.getPontuacaoMinima() %>" title="Pontuação Mínima" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					Pontuação Máxima:
				</td>
				<td>
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" value="<%= eleicaoPontuacao.getPontuacaoMaxima() %>" title="Pontuação Máxima" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					Intervalo da Pontuação:
				</td>
				<td>
					<input type="text" id="<%= ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" value="<%= eleicaoPontuacao.getIntervaloPontuacao() %>" title="Intervalo da Pontuação" readonly="readonly">
				</td>
			</tr>
		</tbody>
		<%
		}
		%>
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