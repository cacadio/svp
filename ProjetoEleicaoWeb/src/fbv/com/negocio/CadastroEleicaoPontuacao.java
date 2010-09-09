package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.IRepositorioBD;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public class CadastroEleicaoPontuacao {
 
	private IRepositorioBD repositorioBD;
	
	public CadastroEleicaoPontuacao(IRepositorioBD repositorio){
		if(repositorio != null){
			this.repositorioBD = repositorio;
		}
	}
	
	public void incluir(EleicaoPontuacao pEleicaoPontuacao) throws SQLException, ExcecaoRegistroJaExistente{
		repositorioBD.incluir(pEleicaoPontuacao);
	}
	
	public void alterar(EleicaoPontuacao pEleicaoPontuacao) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.alterar(pEleicaoPontuacao);
	}
	
	public void excluir(EleicaoPontuacao pEleicaoPontuacao) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.excluir(pEleicaoPontuacao);
	}
	
	public EleicaoPontuacao consultarPelaChave(Eleicao pEleicaoPontuacao) throws SQLException, ExcecaoRegistroNaoExistente{
		return (EleicaoPontuacao) repositorioBD.consultarPelaChave(pEleicaoPontuacao);
	}
	
	public ArrayList<EleicaoPontuacao> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente{
		return repositorioBD.consultarTodos();
	}
	 
}
 
