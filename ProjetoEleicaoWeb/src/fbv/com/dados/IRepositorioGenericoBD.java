package fbv.com.dados;

import java.sql.SQLException;
import java.util.ArrayList;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;

public interface IRepositorioGenericoBD {
		 
	@SuppressWarnings("unchecked")
	public ArrayList consultarPeloIDEleicao(Object pChave) throws SQLException, ExcecaoRegistroNaoExistente;
}
	 
