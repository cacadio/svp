package fbv.com.negocio;

public class EstadoConcluida implements IEstado {

	private static final int valor = 5;
	private static final String descricao = "CONCLUIDA";

	public String getUpdateSQL(Eleicao eleicao) {
		
    	String sql = "UPDATE Eleicao SET " +
			 "ID_ESTADO = 5 "+ 
		 "WHERE ID_ELEICAO = " + eleicao.getId() + ";";
    	
		return sql;
	}

	public int getValor() {
		return valor;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
