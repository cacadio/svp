package fbv.com.negocio;

import java.util.ArrayList;

import fbv.com.dados.IRepositorioResultado;

public class CadastroResultadoEleicao {
	private IRepositorioResultado repositorio;
	
	public CadastroResultadoEleicao(IRepositorioResultado repositorio){
		if (repositorio != null)
			this.repositorio = repositorio;
	}
	
	public ArrayList<ResultadoEleicao> consultar(int idEleicao) throws Exception{
		return repositorio.consultar(idEleicao);
	}
}
