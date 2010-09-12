<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="fbv.com.servlets.ServletLogin"%>
<%@page import="fbv.com.servlets.ServletMenu"%>
<%@page import="fbv.com.negocio.Eleicao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="fbv.com.negocio.EleicaoEscolhaUnica"%>
<%@page import="fbv.com.negocio.EleicaoPontuacao"%>
<%@page import="fbv.com.util.TipoEleicao"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>..:: Sistema de Votação ::..</title>
<link rel="stylesheet" type="text/css" href="./estilo/style.css" media="screen" />
</head>
<%
try{
	Iterator itEleicaoPriv = null;
	Iterator itEleicaoPubl = null;
	
	ArrayList arrEleicaoPrivada = (ArrayList) request.getAttribute(ServletMenu.ID_REQ_ARRAY_LIST_ELEICAO_PRIVADA);
	ArrayList arrEleicaoPublica = (ArrayList) request.getAttribute(ServletMenu.ID_REQ_ARRAY_LIST_ELEICAO_PUBLICA);
	
	if(arrEleicaoPrivada != null && !arrEleicaoPrivada.isEmpty()){
		itEleicaoPriv = arrEleicaoPrivada.iterator();
	}else{
		itEleicaoPriv = (new ArrayList()).iterator();
	}
	
	if(arrEleicaoPublica != null && !arrEleicaoPublica.isEmpty()){
		itEleicaoPubl = arrEleicaoPublica.iterator();
	}else{
		itEleicaoPubl = (new ArrayList()).iterator();
	}
	
%>
<script type="text/javascript" >

function eventoDetalharEleicao(idEleicao, tpEleicao) {
	document.forms.form_principal.<%=ServletMenu.ID_REQ_EVENTO%>.value = "<%=ServletMenu.ID_REQ_EVENTO_PROCESSAR_FILTRO_CONSULTA%>";
	document.forms.form_principal.<%=ServletMenu.ID_REQ_ID_ELEICAO%>.value = idEleicao;
	document.forms.form_principal.<%=ServletMenu.ID_REQ_TIPO_DE_ELEICAO%>.value = tpEleicao;
	document.forms.form_principal.submit();
}

</script>
<body>
<form action="/ProjetoEleicaoWeb/ServletMenu" method="post" id="form_principal">
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
			<li><a href="/ProjetoEleicaoWeb/ServletVoto">Voto</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletResultado">Resultado</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletUsuario">Usuário</a></li>
			<li><a href="/ProjetoEleicaoWeb/ServletPerfilUsuario">Perfis</a></li>
		</ul>
	</div>
	<!-- end #menu -->
</div>

<input type="hidden" id="<%=ServletMenu.ID_REQ_EVENTO%>" name="<%=ServletMenu.ID_REQ_EVENTO%>" value="">
<input type="hidden" id="<%=ServletMenu.ID_REQ_ID_ELEICAO%>" name="<%=ServletMenu.ID_REQ_ID_ELEICAO%>" value="">
<input type="hidden" id="<%=ServletMenu.ID_REQ_TIPO_DE_ELEICAO%>" name="<%=ServletMenu.ID_REQ_TIPO_DE_ELEICAO%>" value="">
	<table width="80%" border="0" align="center">
		<tr>
			<td colspan="2">
				<div class="post">
					<h1 class="title">Eleições </h1>
				</div>
			</td>
		</tr>
		<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
		<tr>
			<td class="td"><b>Privado</b></td>
			<td class="td"><b>Pública</b></td>
		</tr>
		<tr>
			<td>
				<table>
				
				<%
				Eleicao eleicao = null;
				EleicaoEscolhaUnica eleicaoUnica = null;
				EleicaoPontuacao eleicaoPontuacao = null;
				TipoEleicao tpEleicao = null;
				while(itEleicaoPriv.hasNext()){
					Object obj = itEleicaoPriv.next();
					if(obj.getClass().getName().length() != 32){
						eleicaoUnica = (EleicaoEscolhaUnica)obj;
						tpEleicao = TipoEleicao.ESCOLHA_UNICA;
						eleicao = eleicaoUnica;
					}else{
						eleicaoPontuacao = (EleicaoPontuacao)obj;
						tpEleicao = TipoEleicao.PONTUACAO;
						eleicao = eleicaoPontuacao;
					}
					
				%>
					<tr>
						<td>
							<li><a href="javascript:eventoDetalharEleicao(<%=eleicao.getId()%>, '<%=tpEleicao%>')"><%=eleicao.getDescricao()%></a></li>
						</td>
					</tr>				
				<%
				tpEleicao = null;
				}//fim do for %>
				</table>
			</td>
			<td>
				<table>
				<%while(itEleicaoPubl.hasNext()){ 
					Object obj = itEleicaoPubl.next();
					if(obj.getClass().getName().length() != 32){
						eleicaoUnica = (EleicaoEscolhaUnica)obj;
						tpEleicao = TipoEleicao.ESCOLHA_UNICA;
						eleicao = eleicaoUnica;
					}else{
						eleicaoPontuacao = (EleicaoPontuacao)obj;
						tpEleicao = TipoEleicao.PONTUACAO;
						eleicao = eleicaoPontuacao;
					}
				%>
				<tr>
					<td>
						<li><a href="javascript:eventoDetalharEleicao(<%=eleicao.getId()%>, '<%=tpEleicao%>')"><%=eleicao.getDescricao()%></a></li>						
					</td>
				</tr>
				<%
				tpEleicao = null;
				}//fim do for %>
				</table>
			</td>
		</tr>
		</div>
		<div id="sidebar-bgbtm"></div>
		</div>
	</table>
	<div id="footer">
		<p>&copy; 2010. All Rights Reserved.</p>
	</div>
</form>
<%
	}catch(Exception e){
		e.printStackTrace();
	}
%>
</body>
</html>