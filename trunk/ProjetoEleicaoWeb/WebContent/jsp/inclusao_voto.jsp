<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="fbv.com.servlets.ServletVoto"%>
<%@page import="java.util.Iterator"%>
<%@page import="fbv.com.negocio.OpcaoVoto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fbv.com.servlets.ServletOpcaoVoto"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<title>Inclusão de Voto</title>
</head>
<%

	Iterator iterator = null;
	String descricao = "";
	String imagem = "";
	String tipoDeEleicao = "";
	Integer valorVoto = null;
	ArrayList<OpcaoVoto> arrayListVoto = null;
	String pathImage = "";	

	//Obtem Parâmetros do request	
	arrayListVoto = (ArrayList<OpcaoVoto>) request.getAttribute(ServletVoto.ID_REQ_OBJETO_VOTO);
	tipoDeEleicao = (String)request.getAttribute(ServletVoto.ID_REQ_TIPO_DE_ELEICAO);

	try{
%>
<script type="text/javascript">

function eventoProcessarInclusao(){
	
	document.forms.form_principal.<%=ServletVoto.ID_REQ_EVENTO%>.value = "<%=ServletVoto.ID_REQ_EVENTO_PROCESSAR_INCLUSAO%>";
	document.forms.form_principal.submit();
}

</script>
<body>
	<form action="/ProjetoEleicaoWeb/ServletVoto" method="post" id="form_principal">
	<input type="hidden" id="<%=ServletVoto.ID_REQ_EVENTO%>" name="<%=ServletVoto.ID_REQ_EVENTO%>" value="">
	<input type="hidden" id="tipoElicaoHidden" name="<%=ServletVoto.ID_REQ_TIPO_DE_ELEICAO_HIDDEN%>" value="<%=tipoDeEleicao%>">
	<table width="100%">
		<tr>
			<th class="titulopagina">Inclusão Voto</th>
		</tr>
	</table>
	<table width="100%">
			<% 
			if(tipoDeEleicao.equals("OPCAO_UNICA")) {
		
		
			iterator = arrayListVoto.iterator(); 
		
			while(iterator.hasNext()){
			
				Object opcaoVotoGenerico = iterator.next();
				OpcaoVoto opcaoVoto = (OpcaoVoto) opcaoVotoGenerico;
				
				descricao = opcaoVoto.getDescricao();
				pathImage = opcaoVoto.getPath_foto();
		
		%>
		<tr>
			<th class="rotulodado" width="12%">Opção Voto:</th>
			<td class="valordado"><input type="radio" id="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" value= <%=opcaoVoto.getId()%>></td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">Descrição:</th>
			<td class="valordado"><input type="text" id="descOpcaoVoto" name="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" value=<%=descricao%> readonly="readonly"></td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">Imagem:</th>
			<td class="valordado"><img src="<%=pathImage%>" width="260" height="261" border="2" >
			</td>
		</tr>
			<%
				}
			
			 }else{ 
	
			iterator = arrayListVoto.iterator(); 	
			while(iterator.hasNext()){
				
				Object opcaoVotoGenerico = iterator.next();
				OpcaoVoto opcaoVoto = (OpcaoVoto) opcaoVotoGenerico;
				
				descricao = opcaoVoto.getDescricao();
				pathImage = opcaoVoto.getPath_foto();		
		%>
		<tr>
			<th class="rotulodado" width="12%">Opção Voto:</th>
			<td class="valordado"><input type="checkbox" id="<%=ServletOpcaoVoto.ID_REQ_CHECK_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO + opcaoVoto.getId()%>" value= <%=opcaoVoto.getId()%>></td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">Descrição:</th>
			<td class="valordado"><input type="text" id="descOpcaoVoto" name="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" value=<%=descricao%> readonly="readonly"></td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">Imagem:</th>
			<td class="valordado"><img src="<%=pathImage%>" width="260" height="261" border="2" >
			</td>
		</tr>
		<tr>
			<th class="rotulodado" width="12%">Valor do Voto:</th>
			<td class="valordado"><select name="<%=ServletVoto.ID_REQ_VALOR_VOTO + opcaoVoto.getId()%>"> 
				<% 
						ArrayList vlVoto = new ArrayList();
						vlVoto.add(0,"Selecionar Valor");
						vlVoto.add(1,"2");
						vlVoto.add(2,"4");
						vlVoto.add(3,"6");
						vlVoto.add(4,"8");
						vlVoto.add(5,"10");
						
						Iterator iteraValorVoto = null;
						iteraValorVoto = vlVoto.iterator(); 
						
						for(int j=0; vlVoto.size() > j; j++){
						
							Object vlVotoObj = vlVoto.get(j);
						 %>           
  							<option value="<%= vlVotoObj %>"><%= vlVotoObj%></option>  
             			<% } %>                                       
           		</select>  
			</td>
		</tr>
		<%
			}
		%>
		<%
		}
		%>
		
	</table>
	<table width="100%">
		<tr>
			<th class="footer" colspan="2">&nbsp;</th>
		</tr>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td class="linhabotao"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarInclusao()" value="Confirmar"> </td>
		</tr>
	
	</table>
	</form>
<% }catch(Exception e){
	e.printStackTrace();
}
%>
</body>
</html>