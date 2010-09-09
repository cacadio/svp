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
	<script type="text/javascript" src="js/biblioteca_funcoes_eleicao.js" ></script>
	<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
	
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
<body onload="setarFoco(document.forms.form_principal.<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>)">
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
			<li><a href="/ProjetoEleicaoWeb/ServletLogin">Login</a></li>
		</ul>
	</div>
	<!-- end #menu -->
</div>
	<table width="80%" border="0" align="center">
		<tr>
			<td colspan="4">
				<div class="post">
					<h1 class="title">Incluir Eleição </h1>
				</div>
			</td>
		</tr>
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<th class="td" width="22%" align="right">
				Tipo:
			</th>
			<td class="valordado">
				<select id="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" name="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" obrigatorio="1" onchange="exibirCampos(this.value)">
					<option value="<%= TipoEleicao.ESCOLHA_UNICA.value() %>" <%= request.getAttribute(ServletEleicao.ID_REQ_TIPO_ELEICAO).equals(TipoEleicao.ESCOLHA_UNICA.value())? "selected": "" %>>Escolha Única</option>
					<option value="<%= TipoEleicao.PONTUACAO.value() %>" <%= request.getAttribute(ServletEleicao.ID_REQ_TIPO_ELEICAO).equals(TipoEleicao.PONTUACAO.value())? "selected": "" %>>Pontuação</option>
				</select>
			</td>
		</tr>
		<tr>
			<th class="td" width="22%" align="right">
				Descrição:
			</th>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DESCRICAO_ELEICAO%>" value="" title="Descrição" obrigatorio="1"></input>
			</td>
		</tr>
		<tr>
			<th class="td">
				<b>Pública?</b>
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="1" title="Pública" obrigatorio="1">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_PUBLICA_ELEICAO %>" value="0" title="Pública" obrigatorio="1">Não
			</td>
		</tr>
		<tr>
			<th class="td">
				<b>Voto Aberto?</b>
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="1" title="Voto Aberto" obrigatorio="1">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VISIBILIDADE_ABERTA_ELEICAO %>" value="0" title="Voto Aberto" obrigatorio="1">Não
			</td>
		</tr>
		<tr>
			<th class="td">
				<b>Múltiplos Votos?</b>
			</th>
			<td class="valordado">
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="1" title="Múltiplos Votos" obrigatorio="1">Sim&nbsp;
				<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_VOTO_MULTIPLO_ELEICAO %>" value="0" title="Múltiplos Votos" obrigatorio="1">Não
			</td>
		</tr>
		<tr>
			<th class="td" width="22%">
				<b>Data Encerramento (Prevista):</b>
			</th>
			<td class="valordado">
				<input type="text" id="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_DATA_FIM_ELEICAO%>" value="" title="Data Encerramento" size="12" maxlength="10"></input>
			</td>
		</tr>
		<tbody id="trEscolhaUnica" style="display: <%= (tipo == TipoEleicao.ESCOLHA_UNICA)? "": "none" %>">
			<tr>
				<th class="td">
					<b>Eleição Associada:</b>
				</th>
				<td class="valordado">
					<select id="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>" name="<%= ServletEleicao.ID_REQ_CODIGO_ELEICAO_PAI %>">
						<option value="0"></option>
					<%
					@SuppressWarnings("unchecked")
					ArrayList<EleicaoEscolhaUnica> eleicoes = (ArrayList<EleicaoEscolhaUnica>)request.getAttribute(ServletEleicao.ID_REQ_ARRAY_LIST_ELEICAO);
					if (eleicoes != null){
						for(EleicaoEscolhaUnica eleicao : eleicoes){
							if (eleicao.getEstado().getValor() == 5){
					%>
						<option value="<%= eleicao.getId() %>"><%= eleicao.getDescricao() %></option>
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
					<b>Existe Voto Nulo/Branco?</b>
				</th>
				<td class="valordado">
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Sim" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="1" title="Voto Nulo/Branco" obrigatorio="1" escolhaunica="1">Sim&nbsp;
					<input type="radio" id="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>_Nao" name="<%= ServletEleicao.ID_REQ_IN_CAMPO_NULO_ELEICAO %>" value="0" title="Voto Nulo/Branco" obrigatorio="1" escolhaunica="1">Não
				</td>
			</tr>
			<tr>
				<th class="td">
					<b>Percentual para Vitória:</b>
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PERCENTUAL_VITORIA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PERCENTUAL_VITORIA_ELEICAO %>" title="Percentual para Vitória" obrigatorio="1" escolhaunica="1">
				</td>
			</tr>
		</tbody>
		<tbody id="trPontuacao" style="display: <%= (tipo == TipoEleicao.PONTUACAO)? "": "none" %>">
			<tr>
				<th class="td">
					<b>Pontuação Mínima:</b>
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MINIMA_ELEICAO %>" title="Pontuação Mínima" pontuacao="1">
				</td>
			</tr>
			<tr>
				<th class="td">
					<b>Pontuação Máxima:</b>
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_PONTUACAO_MAXIMA_ELEICAO %>" title="Pontuação Máxima" pontuacao="1">
				</td>
			</tr>
			<tr>
				<th class="td">
					<b>Intervalo da Pontuação:</b>
				</th>
				<td class="valordado">
					<input type="text" id="<%= ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" name="<%=ServletEleicao.ID_REQ_INTERVALO_PONTUACAO_ELEICAO %>" title="Intervalo da Pontuação" pontuacao="1">
				</td>
			</tr>
		</tbody>
		<tr>
			<td class="linhabotao" align="center"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td class="linhabotao" align="center"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarInclusao()" value="Confirmar"> </td>
		</tr>
	
	</div>
		<div id="sidebar-bgbtm"></div>
		</div>
	</table>
	<div id="footer">
		<p>&copy; 2008. All Rights Reserved.</p>
	</div>
	</form>
<% }catch(Exception e){
	e.printStackTrace();
}
%>
</body>
</html>