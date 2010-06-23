<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.Eleicao"%>
<%@page import="fbv.com.servlets.ServletEleicao"%>
<%@page import="fbv.com.util.TipoEleicao"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta Perfil de Usuário</title>
</head>
<%
	try{
		
	//Obtem Parâmetros do request	
	@SuppressWarnings("unchecked")
	ArrayList<Eleicao> arrayListEleicao = (ArrayList<Eleicao>) request.getAttribute(ServletEleicao.ID_REQ_ARRAY_LIST_ELEICAO);
	
%>

<script type="text/javascript" >

function eventoConsultar(){
	document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO%>.value = "<%=ServletEleicao.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA%>";
	document.forms.form_principal.submit();
}

function eventoIncluir(){
	document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO%>.value = "<%=ServletEleicao.ID_REQ_EVENTO_EXIBIR_INCLUSAO%>";
	document.forms.form_principal.submit();
}

function eventoAlterar(){
	<%
	if(arrayListEleicao != null && arrayListEleicao.size() > 0){
	%>
		document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO%>.value = "<%=ServletEleicao.ID_REQ_EVENTO_EXIBIR_ALTERACAO%>";
		document.forms.form_principal.submit();
	<%
	}else{	
	%>
		alert("Nenhum Registro Selecionado!");
		return false;
	<%
	}
	%>
}

function eventoExcluir(){
	<%
	if(arrayListEleicao != null && arrayListEleicao.size() > 0){
	%>

		document.forms.form_principal.<%=ServletEleicao.ID_REQ_EVENTO%>.value = "<%=ServletEleicao.ID_REQ_EVENTO_EXIBIR_EXCLUSAO%>";
		document.forms.form_principal.submit();
	<%
	}else{	
	%>
		alert("Nenhum Registro Selecionado!");
		return false;
	<%
	}
	%>
}

</script>
<body>
<form action="/ProjetoEleicaoWeb/Eleicao" method="post" id="form_principal">
	<table width="100%" border="0">
		
		<tr>
			<td colspan="3" align="center">
				<input type="hidden" id="<%=ServletEleicao.ID_REQ_EVENTO%>" name="<%=ServletEleicao.ID_REQ_EVENTO%>" value="">
				<b>Consultar Eleição</b>
			</td>
		</tr>
		<tr>
			<td width="80px">
				Tipo:
			</td>
			<td width="100px">
				<select id="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>" name="<%= ServletEleicao.ID_REQ_TIPO_ELEICAO %>">
					<option value="<%= TipoEleicao.ESCOLHA_UNICA.value() %>">Escolha Única</option>
					<option value="<%= TipoEleicao.PONTUACAO.value() %>">Pontuação</option>
				</select>
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
				Código:
			</td>
			<td>
				<input type="text" id="<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO%>" name="<%=ServletEleicao.ID_REQ_CODIGO_ELEICAO%>" value="" ></input>
			</td>
			<td><input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
		</tr>
	</table>
	<br/>
	<table width="100%" border="1">
		<tr>
			<td width="30px"></td>
			<td width="80px" align="center">Código</td>
			<td align="center">Descrição</td>
			<td width="120px" align="center">Data Abertura</td>
			<td width="120px" align="center">Data Encerramento</td>
		</tr>
		<%
		//Exibindo dados
		if(arrayListEleicao != null && !arrayListEleicao.isEmpty()){
			String checked = null;
			for(int i = 0 ; i < arrayListEleicao.size() ; i++){
				
				checked = "";
				Eleicao eleicao = arrayListEleicao.get(i);
				
				if(i == 0){
					checked="checked";
				}
				
		%>
		<tr>
			<td><input type="radio" id="<%=ServletEleicao.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletEleicao.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=eleicao.getId()%>"> </td>
			<td align="left"><%=eleicao.getId()%></td>
			<td align="left"><%=eleicao.getDescricao()%></td>
			<td align="center"><%= eleicao.getDataAbertura() %></td>
			<td align="center"><%= eleicao.getDataEncerramento() %></td>
		</tr>
		<%
			}
		}else if (arrayListEleicao != null){
		%>
		<tr>
			<td colspan="5">Nenhum Registro Encontrado</td>
		</tr>	
		
		<%
		}
		%>		
	</table>
	<table width="100%">
		<tr>
			<td align="right"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
			<td align="center"><input type="button" id="botaoAlterar" name="botaoAlterar" value="Alterar" onclick="eventoAlterar()"> </td>
			<td align="left"><input type="button" id="botaoExcluir" name="botaoExcluir" value="Excluir" onclick="eventoExcluir()"> </td>
		</tr>
	
	</table>

</form>

<%
	}catch(Exception e){
		e.printStackTrace();
	}

%>
</body>
</html>