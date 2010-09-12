package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.IRepositorioBD;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public class CadastroVoto {
	
private IRepositorioBD repositorioBD;
	
	public CadastroVoto(IRepositorioBD repositorio){
		if(repositorio != null){
			this.repositorioBD = repositorio;
		}
	}
	
	public void incluir(Voto pVoto) throws SQLException, ExcecaoRegistroJaExistente{
		repositorioBD.incluir(pVoto);
	}
	
	public void alterar(Voto pVoto) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.alterar(pVoto);
	}
	
	public void excluir(Voto pVoto) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.excluir(pVoto);
	}
	
	public Voto consultarPelaChave(Voto pVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return (Voto) repositorioBD.consultarPelaChave(pVoto);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Voto> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente{
		return repositorioBD.consultarTodos();
	}
}
