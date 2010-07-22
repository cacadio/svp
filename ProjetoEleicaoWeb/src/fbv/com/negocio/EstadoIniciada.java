package fbv.com.negocio;

import java.text.SimpleDateFormat;

public class EstadoIniciada implements IEstado {

	public int getValor() {
		return 2;
	}

	public String getUpdateSQL(Eleicao eleicao) {
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		
    	String sql = "UPDATE Eleicao SET " +
			 "ID_ESTADO = 2, "+ 
			 "DT_INICIO = '" + sdt.format(eleicao.getDataAbertura()) + "' " + 
		 "WHERE ID_ELEICAO = " + eleicao.getId() + ";";
    	
		return sql;
	}

}
