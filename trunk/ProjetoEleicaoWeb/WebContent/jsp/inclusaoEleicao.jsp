<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletEleicao"%>
<%@page import="fbv.com.util.TipoEleicao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.EleicaoEscolhaUnica"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Inclusão Perfil Usuário</title>
	<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
	<script src="./js/jquery.js" type="text/javascript" language="javascript"></script>
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
		TipoEleicao tipo;
		if (request.getAttribute(ServletEleicao.ID_REQ_TIPO_ELEICAO).equals(TipoEleicao.ESCOLHA_UNICA.value())){
			tipo = TipoEleicao.ESCOLHA_UNICA;
		}
		else{
			tipo = TipoEleicao.PONTUACAO;
		}
%>
	
	<script type="text/javascript">
	
	function eventoProcessarInclusao(){
	
		var msg = '';

		$('input[obrigatorio]').each(function(){
			if ($(this).val() == ''){
				msg += ' - ' + $(this).attr('title') + '\n';
			}
		});
	
		if (msg != ''){
			alert('Os seguintes campos devem ser preenchidos:\n' + msg);
			return false;
		}
		document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO%>.value = "<%=ServletEleicao.ID_REQ_EVENTO_PROCESSAR_INCLUSAO%>";
		document.forms.form_principal.submit();
	}

	function exibirCampos(tipo){
		if (tipo == '1'){
			$('#trEscolhaUnica').show();
			$('#trPontuacao').hide();
			$('input[pontuacao]').each(function(){
				$(this).removeAttr('obrigatorio');
			});
			$('input[escolhaunica]').each(function(){
				$(this).attr('obrigatorio', '1');
			});
		}
		else{
			$('#trEscolhaUnica').hide();
			$('#trPontuacao').show();
			$('input[escolhaunica]').each(function(){
				$(this).removeAttr('obrigatorio');
			});
			$('input[pontuacao]').each(function(){
				$(this).attr('obrigatorio', '1');
			});
		}
	}
	
	</script>
<body>
	<form action="/ProjetoEleicaoWeb/ServletEleicao" method="post" id="form_principal">
	<table width="100%">
		<tr>
			<th class="titulopagina" align="center" colspan="2">
				<input type="hidden" id="<%=ServletEleicao.ID_REQ_EVENTO%>" name="<%=ServletEleicao.ID_REQ_EVENTO%>" value="">
				Incluir Eleição
			</th>
		</tr>
		<tr>
			<td class="rotulodado" width="25%">
				Tipo:
			</td>
			<td class="valordado">
				<select id="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" name="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" obrigatorio="1" onchange="exibirCampos(this.value)">
					<option value="<%= TipoEleicao.ESCOLHA_UNICA.value() %>" <%= request.getAttribute(ServletEleicao.ID_REQ_TIPO_ELEICAO).equals(TipoEleicao.ESCOLHA_UNICA.value())? "selected": "" %>>Escolha Única</option>
					<option value="<%= TipoEleicao.PONTUACAO.value() %>" <%= request.getAttribute(ServletEleicao.ID_REQ_TIPO_ELEICAO).equals(TipoEleicao.PONTUACAO.value())? "selected": "" %>>Pontuação</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="rotulodado" width="25%">
				Descrição:
			</td>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" value="" title="Descrição" obrigatorio="1"></input>
			</td>
		</tr>
		<tr>
			<td class="rotulodado">
				Pública?
			</td>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="1" title="Pública" obrigatorio="1">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="0" title="Pública" obrigatorio="1">Não
			</td>
		</tr>
		<tr>
			<td class="rotulodado">
				Voto Aberto?
			</td>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="1" title="Voto Aberto" obrigatorio="1">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="0" title="Voto Aberto" obrigatorio="1">Não
			</td>
		</tr>
		<tr>
			<td class="rotulodado">
				Múltiplos Votos?
			</td>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="1" title="Múltiplos Votos" obrigatorio="1">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="0" title="Múltiplos Votos" obrigatorio="1">Não
			</td>
		</tr>
		<tr>
			<td class="rotulodado" width="25%">
				Data Abertura:
			</td>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" value="" title="Data Abertura" size="12" maxlength="10" obrigatorio="1"></input>
			</td>
		</tr>
		<tr>
			<td class="rotulodado" width="25%">
				Data Encerramento:
			</td>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" value="" title="Data Encerramento" size="12" maxlength="10" obrigatorio="1"></input>
			</td>
		</tr>
		<tbody id="trEscolhaUnica" style="display: <%= (tipo == TipoEleicao.ESCOLHA_UNICA)? "": "none" %>">
			<tr>
				<td class="rotulodado">
					Eleição Associada:
				</td>
				<td class="valordado">
					<select id="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>" name="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>">
						<option value="0"></option>
					<%
					@SuppressWarnings("unchecked")
					ArrayList<EleicaoEscolhaUnica> eleicoes = (ArrayList<EleicaoEscolhaUnica>)request.getAttribute(ServletEleicao.ID_REQ_ARRAY_LIST_ELEICAO);
					if (eleicoes != null){
						for(EleicaoEscolhaUnica eleicao : eleicoes){
					%>
						<option value="<%= eleicao.getId() %>"><%= eleicao.getDescricao() %></option>
					<%
						}
					}
					%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="rotulodado">
					Existe Voto Nulo/Branco?
				</td>
				<td class="valordado">
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="1" title="Voto Nulo/Branco" obrigatorio="1" escolhaunica="1">Sim&nbsp;
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="0" title="Voto Nulo/Branco" obrigatorio="1" escolhaunica="1">Não
				</td>
			</tr>
			<tr>
				<td class="rotulodado">
					Percentual para Vitória:
				</td>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PERCENTUAL_VITORIA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PERCENTUAL_VITORIA_ELEICAO %>" title="Percentual para Vitória" obrigatorio="1" escolhaunica="1">
				</td>
			</tr>
		</tbody>
		<tbody id="trPontuacao" style="display: <%= (tipo == TipoEleicao.PONTUACAO)? "": "none" %>">
			<tr>
				<td class="rotulodado">
					Pontuação Mínima:
				</td>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" title="Pontuação Mínima" pontuacao="1">
				</td>
			</tr>
			<tr>
				<td class="rotulodado">
					Pontuação Máxima:
				</td>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" title="Pontuação Máxima" pontuacao="1">
				</td>
			</tr>
			<tr>
				<td class="rotulodado">
					Intervalo da Pontuação:
				</td>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" title="Intervalo da Pontuação" pontuacao="1">
				</td>
			</tr>
		</tbody>
	</table>
	<table width="100%">
		<tr>
			<th class="footer" colspan="2">&nbsp;</th>
		</tr>
		<tr>
			<td class="linhabotao" align="center"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td class="linhabotao" align="center"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarInclusao()" value="Confirmar"> </td>
		</tr>
	
	</table>
	</form>
<% }catch(Exception e){
	e.printStackTrace();
}
%>
</body>
</html>