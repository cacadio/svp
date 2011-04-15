/**Arquivo que possui funções javascript que são usadas 
 * por diversas jsp's do sistema
 *  
 */

//Usada para setar o foco no campo passado como parâmetro
function setarFoco(pCampo){
	if (pCampo != null){
		pCampo.focus();
	}
}

//Seta o evento de ProcessarFiltroConsulta
function eventoConsultar() {
	document.forms.form_principal.idEvento.value = "processarFiltroConsulta";
	document.forms.form_principal.submit();
}

//Seta o evento de ExibirInclusão
function eventoIncluir() {
	document.forms.form_principal.idEvento.value = "exibirInclusao";
	document.forms.form_principal.submit();
}