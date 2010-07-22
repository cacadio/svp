package fbv.com.negocio;

import java.text.SimpleDateFormat;

public class EstadoEmApuracao implements IEstado {

	public int getValor() {
		return 4;
	}

	public String getUpdateSQL(Eleicao eleicao) {
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		
    	String sql = "UPDATE Eleicao SET " +
			 "ID_ESTADO = 4, "+ 
			 "DT_FIM = '" + sdt.format(eleicao.getDataEncerramento()) + "' " +
		 "WHERE ID_ELEICAO = " + eleicao.getId() + ";";
    	
		return sql;
	}

}
