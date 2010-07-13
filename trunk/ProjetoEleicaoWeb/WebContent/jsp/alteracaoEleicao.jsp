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
	<script type="text/javascript" language="JavaScript" src="./js/jquery.maskedinput.js"></script>
	<script type="text/javascript">
		$(function(){
			$('#<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>').mask('99/99/9999',{placeholder:' '});
			$('#<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>').mask('99/99/9999',{placeholder:' '});
		});
	</script>
</head>
<%
	try{
		//Pegando parâmetros do request
		Object e = request.getAttribute(ServletEleicao.ID_REQ_OBJETO_ELEICAO);
		Eleicao eleicao = null;
		TipoEleicao tipo;
		if (e instanceof EleicaoEscolhaUnica){
			tipo = TipoEleicao.ESCOLHA_UNICA;
			eleicao = (EleicaoEscolhaUnica) e;
		}
		else{
			tipo = TipoEleicao.PONTUACAO;
			eleicao = (EleicaoPontuacao)e;
		}
		
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
%>
	<script type="text/javascript">
	
	function eventoProcessarAlteracao(){
	
		var msg = '';
		$('[obrigatorio]').each(function(){
			if ($(this).val() == ''){
				msg += ' - ' + $(this).attr('title') + '/n';
			}
		});
	
		if (msg != ''){
			alert('Os seguintes campos devem ser preenchidos:/n' + msg);
			return false;
		}
		document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO %>.value = "<%=ServletEleicao.ID_REQ_EVENTO_PROCESSAR_ALTERACAO%>";
		document.forms.form_principal.submit();
	}
	
	</script>
<body>
	<form action="/ProjetoEleicaoWeb/ServletEleicao" method="post" id="form_principal">
	<table width="100%">
		<tr>
			<th class="titulopagina" align="center" colspan="2">
				<input type="hidden" id="<%=ServletEleicao.ID_REQ_EVENTO%>" name="<%=ServletEleicao.ID_REQ_EVENTO%>" value="">
				<input type="hidden" id=<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO %> name=<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO %> value="<%= eleicao.getId() %>">
				Alterar Eleição
			</th>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">
				Tipo:
			</th>
			<td class="valordado">
				<%= tipo == TipoEleicao.ESCOLHA_UNICA ? "Escolha Única": "Pontuação"%>
				<input type="hidden" id=<%=ServletEleicao.ID_REQ_TIPO_ELEICAO %> name=<%=ServletEleicao.ID_REQ_TIPO_ELEICAO %> value="<%= tipo.value() %>">
			</td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">
				Descrição:
			</th>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" value="<%= eleicao.getDescricao() %>" title="Descrição" obrigatorio="1"></input>
			</td>
		</tr>
		<tr>
			<th class="rotulodado">
				Pública?
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="1" title="Pública" obrigatorio="1" <%= eleicao.isPublica()? "checked=\"checked\"": ""  %> >Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="0" title="Pública" obrigatorio="1"  <%= !eleicao.isPublica()? "checked=\"checked\"": ""  %>>Não
			</td>
		</tr>
		<tr>
			<th class="rotulodado">
				Voto Aberto?
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="1" title="Voto Aberto" obrigatorio="1" <%= eleicao.isVisibilidadeVoto()? "checked=\"checked\"": ""  %>>Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="0" title="Voto Aberto" obrigatorio="1" <%= !eleicao.isVisibilidadeVoto()? "checked=\"checked\"": ""  %>>Não
			</td>
		</tr>
		<tr>
			<th class="rotulodado">
				Múltiplos Votos?
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="1" title="Múltiplos Votos" obrigatorio="1" <%= eleicao.isMultiplosVotos()? "checked=\"checked\"": ""  %>>Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="0" title="Múltiplos Votos" obrigatorio="1" <%= !eleicao.isMultiplosVotos()? "checked=\"checked\"": ""  %>>Não
			</td>
		</tr>
		<%
		if (eleicao.getEstado() > 1){
		%>
		<tr>
			<th class="rotulodado" width="12%">
				Data Abertura:
			</th>
			<td class="valordado">
				<input type="hidden" id="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" value="<%= sdt.format(eleicao.getDataAbertura()) %>" />
				<input type="text" value="<%= sdt.format(eleicao.getDataAbertura()) %>" title="Data Abertura" size="12" maxlength="10" disabled="disabled" />
			</td>
		</tr>
		<%
		}
		%>
		<tr>
			<th class="rotulodado" width="12%">
				Data Encerramento:
			</th>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" value="<%= eleicao.getDataEncerramento() != null ? sdt.format(eleicao.getDataEncerramento()) : "" %>" title="Data Encerramento" size="12" maxlength="10" />
			</td>
		</tr>
		<%
		if (tipo == TipoEleicao.ESCOLHA_UNICA){
			EleicaoEscolhaUnica eleicaoEscUnica = (EleicaoEscolhaUnica)eleicao;
		%>
		<tbody id="trEscolhaUnica">
			<tr>
				<th class="rotulodado">
					Eleição Associada:
				</th>
				<td class="valordado">
					<select id="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>" name="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>" <%= eleicao.getEstado() > 1? "disabled=\"disabled\"": "" %>>
						<option value="0"></option>
					<%
					int idEleicaoPai = eleicaoEscUnica.getEleicaoPai() != null ? eleicaoEscUnica.getEleicaoPai().getId() : 0;
					@SuppressWarnings("unchecked")
					ArrayList<EleicaoEscolhaUnica> eleicoes = (ArrayList<EleicaoEscolhaUnica>)request.getAttribute(ServletEleicao.ID_REQ_ARRAY_LIST_ELEICAO);
					if (eleicoes != null){
						for(EleicaoEscolhaUnica el : eleicoes){
							if (el.getEstado() == 5 && el.getId() != eleicao.getId() ){
					%>
						<option value="<%= el.getId() %>" <%= el.getId() == idEleicaoPai? "selected=\"selected\"": "" %>><%= el.getDescricao() %></option>
					<%
							}
						}
					}
					%>
					</select>
				</td>
			</tr>
			<tr>
				<th class="rotulodado">
					Existe Voto Nulo/Branco?
				</th>
				<td class="valordado">
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="1" title="Voto Nulo/Branco" obrigatorio="1" <%= eleicaoEscUnica.isCampoNulo()? "checked=\"checked\"": ""  %>>Sim&nbsp;
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="0" title="Voto Nulo/Branco" obrigatorio="1" <%= !eleicaoEscUnica.isCampoNulo()? "checked=\"checked\"": ""  %>>Não
				</td>
			</tr>
			<tr>
				<th class="rotulodado">
					Percentual para Vitória:
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PERCENTUAL_VITORIA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PERCENTUAL_VITORIA_ELEICAO %>" value="<%= eleicaoEscUnica.getPercentualVitoria() %>" title="Percentual para Vitória" obrigatorio="1">
				</td>
			</tr>
		</tbody>
		<%
		}
		else {
			EleicaoPontuacao eleicaoPontuacao = (EleicaoPontuacao)eleicao;
		%>
		<tbody id="trPontuacao">
			<tr>
				<th class="rotulodado">
					Pontuação Mínima:
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" value="<%= eleicaoPontuacao.getPontuacaoMinima() %>" title="Pontuação Mínima" obrigatorio="1">
				</td>
			</tr>
			<tr>
				<th class="rotulodado">
					Pontuação Máxima:
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" value="<%= eleicaoPontuacao.getPontuacaoMaxima() %>" title="Pontuação Máxima" obrigatorio="1">
				</td>
			</tr>
			<tr>
				<th class="rotulodado">
					Intervalo da Pontuação:
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" value="<%= eleicaoPontuacao.getIntervaloPontuacao() %>" title="Intervalo da Pontuação" obrigatorio="1">
				</td>
			</tr>
		</tbody>
		<%
		}
		%>
	</table>
	<table width="100%">
		<tr>
			<th class="footer" colspan="2">&nbsp;</th>
		</tr>
		<tr>
			<td class="linhabotao" align="center"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td class="linhabotao" align="center"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarAlteracao()" value="Confirmar"> </td>
		</tr>
	
	</table>
	</form>
<% }catch(Exception e){
	e.printStackTrace();
}
%>
</body>
</html>