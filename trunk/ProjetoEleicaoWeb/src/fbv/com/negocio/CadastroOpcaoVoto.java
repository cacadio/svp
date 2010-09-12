package fbv.com.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.dados.RepositorioOpcaoVoto;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public class CadastroOpcaoVoto {
 
	private RepositorioOpcaoVoto repositorioBD;
	
	public CadastroOpcaoVoto(RepositorioOpcaoVoto repositorio){
		if(repositorio != null){
			this.repositorioBD = repositorio;
		}
	}
	
	public void incluir(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroJaExistente{
		repositorioBD.incluir(pOpcaoVoto);
	}
	
	public void alterar(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.alterar(pOpcaoVoto);
	}
	
	public void excluir(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoAcessoRepositorio{
		repositorioBD.excluir(pOpcaoVoto);
	}
	
	public OpcaoVoto consultarPelaChave(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return (OpcaoVoto) repositorioBD.consultarPelaChave(pOpcaoVoto);
	}
	
	public ArrayList<OpcaoVoto> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente{
		return repositorioBD.consultarTodos();
	}
	
	public ArrayList<OpcaoVoto> consultarPeloIDEleicao(OpcaoVoto pOpcaoVoto) throws SQLException, ExcecaoRegistroNaoExistente{
		return repositorioBD.consultarPeloIDEleicao(pOpcaoVoto);
	}
	
}
 
