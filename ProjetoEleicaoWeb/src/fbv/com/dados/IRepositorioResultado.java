package fbv.com.dados;

import java.util.ArrayList;

import fbv.com.negocio.ResultadoEleicao;

public interface IRepositorioResultado {
	public ArrayList<ResultadoEleicao> consultar(int idEleicao) throws Exception;
}
