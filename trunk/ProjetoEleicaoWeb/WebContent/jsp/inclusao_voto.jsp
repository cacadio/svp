<%@page import="fbv.com.util.TipoEleicao"%>
<%@page import="fbv.com.negocio.EleicaoPontuacao"%>
<%@page import="fbv.com.negocio.EleicaoEscolhaUnica"%>
<%@page import="fbv.com.negocio.Eleicao"%>
<%@page import="fbv.com.servlets.ServletEleicao"%>
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
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="./estilo/estilo.css">
<title>Inclusão de Voto</title>
</head>
<%

	Iterator iterator = null;
	String descricao = "";
	String imagem = "";
	String tipoDeEleicao = "";
	String descEleicao = "";
	String idEleicao = "";
	Integer valorVoto = null;
	ArrayList<OpcaoVoto> arrayListVoto = null;
	String pathImage = "";	
	int contador = 0;
	ArrayList vlVoto = null;

	//Obtem Parâmetros do request	
	arrayListVoto = (ArrayList<OpcaoVoto>) request.getAttribute(ServletVoto.ID_REQ_OBJETO_VOTO);
	tipoDeEleicao = (String)request.getAttribute(ServletVoto.ID_REQ_TIPO_DE_ELEICAO);
	idEleicao = (String)request.getAttribute(ServletVoto.ID_REQ_ID_ELEICAO);
	descEleicao = (String)request.getAttribute(ServletVoto.ID_REQ_DESCRICAO_ELEICAO);

	try{
%>
<script type="text/javascript">

function eventoProcessarInclusao(){
	
	document.forms.form_principal.<%=ServletVoto.ID_REQ_EVENTO%>.value = "<%=ServletVoto.ID_REQ_EVENTO_PROCESSAR_INCLUSAO%>";

	validaCheckbox();

	if(validaCheckbox())
	{
		document.forms.form_principal.submit();
	}
}

function validaCheckbox()
{ 	
	var frm = document.forms.form1;
	for(i=0; i < frm.length; i++){
	    
        //Verifica se o elemento do formulário corresponde a um checkbox e se é o checkbox desejado
        if (frm.elements[i].type == "checkbox") {
                //Verifica se o checkbox foi selecionado
                if(!frm.elements[i].checked) {
                    alert("Selecione todas as opcoes de voto!");
                    return false;
                }                    
        }    
    } 
   		return true;
}


</script>
<body>
	<form action="/ProjetoEleicaoWeb/ServletVoto" method="post" id="form_principal" name="form1">
	<input type="hidden" id="<%=ServletVoto.ID_REQ_EVENTO%>" name="<%=ServletVoto.ID_REQ_EVENTO%>" value="">
	<input type="hidden" id="<%=ServletVoto.ID_REQ_ID_ELEICAO%>" name="<%=ServletVoto.ID_REQ_ID_ELEICAO%>" value="<%=idEleicao%>">
	<input type="hidden" id="<%=ServletVoto.ID_REQ_TIPO_DE_ELEICAO%>" name="<%=ServletVoto.ID_REQ_TIPO_DE_ELEICAO%>" value="<%=tipoDeEleicao%>">
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
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<td colspan="4">
				<div class="post">
					<h1 class="title">Inclusão Voto</h1>
				</div>
			</td>
		</tr>
		<tr>
			<th class="rotulodado"><%=descEleicao%></th>
		</tr>
	<%
		iterator = arrayListVoto.iterator(); 	
		while(iterator.hasNext()){
	%>
	 <tr>
	 <td>
	 	<table height="100%">
			<tr>
			<th class="td" height="20%">Descrição:</th>
			</tr>
			<tr>
			<th class="td" height="58%">Imagem:</th>
			</tr>
			<%
				if(tipoDeEleicao.equals("OPCAO_UNICA")){
			%>
			<tr>
			<th class="td" height="22%">Valor do Voto:</th>
			</tr>
			<%	} %>	
	 	</table>
	 </td>
	 	<td>
	 	<table height="100%" cellpadding="0" cellspacing="0" border="0">
	 	    <tr>
	 	    <% 	
				while(iterator.hasNext() && contador < 5){
				
					Object opcaoVotoGenerico = iterator.next();
					OpcaoVoto opcaoVoto = (OpcaoVoto) opcaoVotoGenerico;
				
					descricao = opcaoVoto.getDescricao();
					pathImage = opcaoVoto.getPath_foto();		
				%>
				<td>
	 		<table width="100%">
	 		 <%
		    if(tipoDeEleicao.equals(ServletEleicao.ID_REQ_PONTUACAO_TIPO_ELEICAO_PONTUACAO)){
		     %>
				<tr>
				<td class="valordado" width="30%">
				<input type="checkbox" id=""<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO + opcaoVoto.getId()%>" value= <%=opcaoVoto.getId()%>>
				<input type="text" id="descOpcaoVoto" name="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" value="<%=descricao%>" readonly="readonly"></td>
				</tr>
				<tr>
				<td class="valordado" height="50%">&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=pathImage%>" width="120" height="60" border="1"></td>
				</tr>
				<tr>
				<td class="valordado" height="22%"><select name="<%=ServletVoto.ID_REQ_VALOR_VOTO + opcaoVoto.getId()%>"> 
				<% 
				
				Integer intervaloPontuacao = null;
				Integer valorMaximoPontuacao = null;
				Integer valorMinimoPontuacao = null;
				
				intervaloPontuacao = (Integer)request.getAttribute(ServletVoto.ID_REQ_INTERVALO_PONTUACAO_ELEICAO);
				valorMaximoPontuacao = (Integer)request.getAttribute(ServletVoto.ID_REQ_PONTUACAO_MAXIMA_ELEICAO);
				valorMinimoPontuacao = (Integer)request.getAttribute(ServletVoto.ID_REQ_PONTUACAO_MINIMA_ELEICAO);
				
				if(intervaloPontuacao != null && valorMaximoPontuacao != null && valorMinimoPontuacao != null){
					vlVoto = new ArrayList();
					for(int k= valorMinimoPontuacao.intValue(); k <= valorMaximoPontuacao.intValue(); k= k + intervaloPontuacao.intValue()){	
						vlVoto.add(k);
			    	}
							
					Iterator iteraValorVoto = null;
					iteraValorVoto = vlVoto.iterator(); 
							
					while(iteraValorVoto.hasNext()){
							
						Object vlVotoObj = iteraValorVoto.next();
				%>           
	  					<option value="<%= vlVotoObj %>"><%= vlVotoObj%></option>  
	             <% } %>                                       
	           			</select>  
				<% } %> 

				</td>
	 			</tr>
	 		<%}else{ %>
	 			
	 			<tr>
					<td class="valordado" height="10%"><input type="radio" id="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" name="<%=ServletOpcaoVoto.ID_REQ_CODIGO_OPCAO_VOTO%>" value= <%=opcaoVoto.getId()%>></td>
				</tr>
				<tr>
					<td class="valordado" height="18%"><input type="text" id="descOpcaoVoto" name="<%=ServletOpcaoVoto.ID_REQ_DESCRICAO_OPCAO_VOTO%>" value="<%=descricao%>" readonly="readonly"></td>
				</tr>
				<tr>
					<td class="valordado" height="50%">&nbsp;<img src="<%=pathImage%>" width="120" height="60" border="1" ></td>
				</tr>
	 		<% }%>
	 		</table>
	 			<%
	 				contador++;
					}
				%>
	    	</tr>
	 	</table>
	 	</td>
	 </tr>
	 <% contador = 0;
	 } 
	 %>
		<tr>
			<td class="linhabotao"><input type="button" id="botaoConfirmar" name="botaoConfirmar" onclick="eventoProcessarInclusao()" value="Confirmar"> </td>
			<td class="linhabotao"><input type="button" id="botaoVoltar" name="botaoVoltar" onclick="history.back()" value="Voltar"></td>
		</tr>
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