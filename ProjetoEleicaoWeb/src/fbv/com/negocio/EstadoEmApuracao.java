package fbv.com.negocio;

import java.text.SimpleDateFormat;

public class EstadoEmApuracao implements IEstado {

	private static final int valor = 4;
	private static final String descricao = "Em Apuracao";
	
	public int getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
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
