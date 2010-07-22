package fbv.com.negocio;

public class EstadoEmCurso implements IEstado {

	public int getValor() {
		return 3;
	}

	public String getUpdateSQL(Eleicao eleicao) {
		
    	String sql = "UPDATE Eleicao SET " +
			 "ID_ESTADO = 3 "+ 
		 "WHERE ID_ELEICAO = " + eleicao.getId() + ";";
    	
		return sql;
	}

}
