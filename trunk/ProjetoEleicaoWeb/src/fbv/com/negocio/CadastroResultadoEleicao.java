package fbv.com.negocio;

import fbv.com.dados.IRepositorioResultado;

public class CadastroResultadoEleicao {
	private IRepositorioResultado repositorio;
	
	public CadastroResultadoEleicao(IRepositorioResultado repositorio){
		if (repositorio != null)
			this.repositorio = repositorio;
	}
	
	public ResultadoEleicao consultar(Eleicao eleicao) throws Exception{
		return repositorio.consultar(eleicao);
	}
}
