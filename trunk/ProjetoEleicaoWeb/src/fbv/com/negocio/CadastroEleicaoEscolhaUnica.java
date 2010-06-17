package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.IRepositorioBD;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public class CadastroEleicaoEscolhaUnica {
 
	private IRepositorioBD repositorioBD;
	
	public CadastroEleicaoEscolhaUnica(IRepositorioBD repositorio){
		if(repositorio != null){
			this.repositorioBD = repositorio;
		}
	}
	
	public void incluir(EleicaoEscolhaUnica pEleicaoEscolhaUnica) throws SQLException, ExcecaoRegistroJaExistente{
		repositorioBD.incluir(pEleicaoEscolhaUnica);
	}
	
	public void alterar(EleicaoEscolhaUnica pEleicaoEscolhaUnica) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.alterar(pEleicaoEscolhaUnica);
	}
	
	public void excluir(EleicaoEscolhaUnica pEleicaoEscolhaUnica) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.excluir(pEleicaoEscolhaUnica);
	}
	
	public EleicaoEscolhaUnica consultarPelaChave(EleicaoEscolhaUnica pEleicaoEscolhaUnica) throws SQLException, ExcecaoRegistroNaoExistente{
		return (EleicaoEscolhaUnica) repositorioBD.consultarPelaChave(pEleicaoEscolhaUnica);
	}
	
	public ArrayList<EleicaoEscolhaUnica> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente{
		return repositorioBD.consultarTodos();
	}
	 
}
 
