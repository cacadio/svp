<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.negocio.Voto"%>
<%@page import="fbv.com.servlets.ServletVoto"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
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
<input type="hidden" id="<%=ServletVoto.ID_REQ_EVENTO%>" name="<%=ServletVoto.ID_REQ_EVENTO%>" value="">
					
	<table width="100%">
	  <tr>
		<th class="titulopagina">Consulta Voto</th>
	  </tr>
	  </table>
	 <table width="100%">	
		<tr>
			<th class="rotulodado">Código do Voto:</th>	
			<td class="valordado"><input type="text" id="<%=ServletVoto.ID_REQ_ID_VOTO%>" name="<%=ServletVoto.ID_REQ_ID_VOTO%>" value="" size="8" maxlength="10">
								  <input type="button"  id="botaoConsultar" name="botaoConsultar" onclick="eventoConsultar()" value="Localizar"></td>
		</tr>
		</table>
	
	<table width="100%">
		<th class="rotulodado" width="3%">&nbsp;&nbsp;&nbsp;#</th>
		<th class="rotulodado">Usuário: </td>
		<th class="rotulodado" >Código Eleição: </td>
		<th class="rotulodado">Opção de Voto: </td>
		<th class="rotulodado">Valor Voto: </td>
		<th class="rotulodado">Data: </td>
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
		<tr>
			<td colspan="4">Nenhum Registro Encontrado</td>
		</tr>	
		
		<%
		}
		%>		
	</table>
	<table width="100%">
		<tr>
			<th class="footer" colspan="3">&nbsp;</th>
		</tr>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoIncluir" name="botaoIncluir" value="Incluir" onclick="eventoIncluir()"> </td>
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