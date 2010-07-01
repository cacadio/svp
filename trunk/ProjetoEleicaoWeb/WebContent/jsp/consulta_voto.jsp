<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.Voto"%>
<%@page import="fbv.com.servlets.ServletVoto"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta Voto</title>
</head>
<%
	try{
		
	//Obtem Parâmetros do request	
	ArrayList<Voto> arrayListVoto = (ArrayList<Voto>) request.getAttribute(ServletVoto.ID_REQ_ARRAY_LIST_VOTO);
	
%>

<script type="text/javascript" >

function eventoConsultar(){
	document.forms.form_principal.<%=ServletVoto.ID_REQ_EVENTO%>.value = "<%=ServletVoto.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA%>";
	document.forms.form_principal.submit();
}

function eventoIncluir(){
	document.forms.form_principal.<%=ServletVoto.ID_REQ_EVENTO%>.value = "<%=ServletVoto.ID_REQ_EVENTO_EXIBIR_INCLUSAO%>";
	document.forms.form_principal.submit();
}


</script>
<body>
<form action="/ProjetoEleicaoWeb/ServletVoto" method="post" id="form_principal">
	<table width="100%" border="1">
		
		<tr>
			<td>
				<input type="hidden" id="<%=ServletVoto.ID_REQ_EVENTO%>" name="<%=ServletVoto.ID_REQ_EVENTO%>" value="">
				Consulta Voto
			</td>
		</tr>
		<tr>
			<td>
				Código do Voto:
			</td>
			<td>
				<input type="text" id="<%=ServletVoto.ID_REQ_ID_VOTO%>" name="<%=ServletVoto.ID_REQ_ID_VOTO%>" value="" ></input>
			</td>
			<td align="center"><input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"  ></td>
		</tr>
	</table>
	<table width="100%">
		<%
		//Exibindo dados
		if(arrayListVoto != null && !arrayListVoto.isEmpty()){
			String checked = null;
			for(int i = 0 ; i < arrayListVoto.size() ; i++){
				
				checked = "";
				Voto voto = arrayListVoto.get(i);
				
				if(i == 0){
					checked="checked";
				}
				
		%>
			<tr>
				<td><input type="radio" id="<%=ServletVoto.ID_REQ_CHAVE_PRIMARIA%>" name="<%=ServletVoto.ID_REQ_CHAVE_PRIMARIA%>" <%=checked%> value="<%=voto.getIdVoto()%>"> </td>
				<td>Usuário: </td>
				<td align="left"><%=voto.getNomeUsuario()%></td>
				<td>Código Eleição: </td>
				<td align="left"><%=voto.getIdEleicao()%></td>
				<td>Opção de Voto: </td>
				<td align="left"><%=voto.getDescricaoOpcaoVoto()%></td>
				<td>Valor Voto: </td>
				<td align="left"><%=voto.getValorVoto()%></td>
				<td>Data: </td>
				<td align="left"><%=voto.getDataHora()%></td>
				
			</tr>
		<%
			}
		}else{
		%>
		<tr>
			<td colspan="4">Nenhum Registro Encontrado</td>
		</tr>	
		
		<%
		}
		%>		
	</table>
	<table width="100%">
		<tr>
			<td align="center"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
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