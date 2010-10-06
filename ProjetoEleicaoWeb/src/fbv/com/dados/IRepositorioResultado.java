package fbv.com.dados;

import fbv.com.negocio.Eleicao;
import fbv.com.negocio.ResultadoEleicao;

public interface IRepositorioResultado {
	public ResultadoEleicao consultar(Eleicao eleicao) throws Exception;
}
