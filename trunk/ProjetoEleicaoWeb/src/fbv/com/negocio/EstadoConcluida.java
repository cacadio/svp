package fbv.com.negocio;

public class EstadoConcluida implements IEstado {

	public int getValor() {
		return 5;
	}

	public String getUpdateSQL(Eleicao eleicao) {
		
    	String sql = "UPDATE Eleicao SET " +
			 "ID_ESTADO = 5 "+ 
		 "WHERE ID_ELEICAO = " + eleicao.getId() + ";";
    	
		return sql;
	}

}
