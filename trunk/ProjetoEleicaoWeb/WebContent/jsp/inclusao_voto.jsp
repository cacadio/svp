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
	<table width="100%">
		<tr>
			<td>
				<input type="hidden" id="<%=ServletVoto.ID_REQ_EVENTO%>" name="<%=ServletVoto.ID_REQ_EVENTO%>" value="">
				Inclusão de Voto
			</td>
			</tr>
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
			<td width="25%">
				Opção de Voto:
			</td>
			<td>
				<input type="radio" id="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" value= <%=opcaoVoto.getId()%>></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Descrição:
			</td>
			<td>
				<input type="text" id="descOpcaoVoto" name="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" value=<%=descricao%> readonly="readonly"></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Imagem:
			</td>
			<td>
				<img src="<%=pathImage%>" width="260" height="261" border="2" >
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
			<td width="25%">
				Opção de Voto:
			</td>
			<td>
				<input type="radio" id="radioOpcaoVoto" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" value= <%=opcaoVoto.getId()%>></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Descrição:
			</td>
			<td>
				<input type="text" id="descOpcaoVoto" name="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" value=<%=descricao%> readonly="readonly"></input>
			</td>
		</tr>
		<tr>
			<td width="25%">
				Imagem:
			</td>
			<td>
				<img src="<%=pathImage%>" width="260" height="261" border="2" >
			</td>
		</tr>
		<tr>
			<td width="25%">
				Valor do Voto:
			</td>
			<td>
				<select name="<%=ServletVoto.ID_REQ_VALOR_VOTO + opcaoVoto.getId()%>"> 
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
			<td align="center"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
			<td align="center"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarInclusao()" value="Confirmar"> </td>
		</tr>
	
	</table>
	</form>
<% }catch(Exception e){
	e.printStackTrace();
}
%>
</body>
</html>