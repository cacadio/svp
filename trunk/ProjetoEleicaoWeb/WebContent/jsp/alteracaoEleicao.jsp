<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletEleicao"%>
<%@page import="fbv.com.util.TipoEleicao"%>
<%@page import="fbv.com.negocio.Eleicao"%>
<%@page import="fbv.com.negocio.EleicaoEscolhaUnica"%>
<%@page import="fbv.com.negocio.EleicaoPontuacao"%>
<%@page import="java.text.SimpleDateFormat"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Inclusão Perfil Usuário</title>
	<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
	<script src="./js/jquery.js" type="text/javascript"></script>
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
			<td class="rotulodado" width="25%">
				Tipo:
			</td>
			<td class="valordado">
				<%= tipo == TipoEleicao.ESCOLHA_UNICA ? "Escolha Única": "Pontuação"%>
				<input type="hidden" id=<%=ServletEleicao.ID_REQ_TIPO_ELEICAO %> name=<%=ServletEleicao.ID_REQ_TIPO_ELEICAO %> value="<%= tipo.value() %>">
			</td>
		</tr>
		<tr>
			<td class="rotulodado" width="25%">
				Descrição:
			</td>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" value="<%= eleicao.getDescricao() %>" title="Descrição" obrigatorio="1"></input>
			</td>
		</tr>
		<tr>
			<td class="rotulodado">
				Pública?
			</td>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="1" title="Pública" obrigatorio="1" <%= eleicao.isPublica()? "checked=\"checked\"": ""  %> >Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="0" title="Pública" obrigatorio="1"  <%= !eleicao.isPublica()? "checked=\"checked\"": ""  %>>Não
			</td>
		</tr>
		<tr>
			<td class="rotulodado">
				Voto Aberto?
			</td>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="1" title="Voto Aberto" obrigatorio="1" <%= eleicao.isVisibilidadeVoto()? "checked=\"checked\"": ""  %>>Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="0" title="Voto Aberto" obrigatorio="1" <%= !eleicao.isVisibilidadeVoto()? "checked=\"checked\"": ""  %>>Não
			</td>
		</tr>
		<tr>
			<td class="rotulodado">
				Múltiplos Votos?
			</td>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="1" title="Múltiplos Votos" obrigatorio="1" <%= eleicao.isMultiplosVotos()? "checked=\"checked\"": ""  %>>Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="0" title="Múltiplos Votos" obrigatorio="1" <%= !eleicao.isMultiplosVotos()? "checked=\"checked\"": ""  %>>Não
			</td>
		</tr>
		<tr>
			<td class="rotulodado" width="25%">
				Data Abertura:
			</td>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" value="<%= sdt.format(eleicao.getDataAbertura()) %>" title="Data Abertura"  obrigatorio="1"></input>
			</td>
		</tr>
		<tr>
			<td class="rotulodado" width="25%">
				Data Encerramento:
			</td>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_INICIO_ELEICAO%>" value="<%= sdt.format(eleicao.getDataEncerramento()) %>" title="Data Encerramento" obrigatorio="1"></input>
			</td>
		</tr>
		<%
		if (tipo == TipoEleicao.ESCOLHA_UNICA){
			EleicaoEscolhaUnica eleicaoEscUnica = (EleicaoEscolhaUnica)eleicao;
		%>
		<tbody id="trEscolhaUnica">
			<tr>
				<td class="rotulodado">
					Existe Voto Nulo/Branco?
				</td>
				<td class="valordado">
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="1" title="Voto Nulo/Branco" obrigatorio="1" <%= eleicaoEscUnica.isCampoNulo()? "checked=\"checked\"": ""  %>>Sim&nbsp;
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="0" title="Voto Nulo/Branco" obrigatorio="1" <%= eleicaoEscUnica.isCampoNulo()? "checked=\"checked\"": ""  %>>Não
				</td>
			</tr>
			<tr>
				<td class="rotulodado">
					Percentual para Vitória:
				</td>
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
				<td class="rotulodado">
					Pontuação Mínima:
				</td>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" value="<%= eleicaoPontuacao.getPontuacaoMinima() %>" title="Pontuação Mínima" obrigatorio="1">
				</td>
			</tr>
			<tr>
				<td class="rotulodado">
					Pontuação Máxima:
				</td>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" value="<%= eleicaoPontuacao.getPontuacaoMaxima() %>" title="Pontuação Máxima" obrigatorio="1">
				</td>
			</tr>
			<tr>
				<td class="rotulodado">
					Intervalo da Pontuação:
				</td>
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