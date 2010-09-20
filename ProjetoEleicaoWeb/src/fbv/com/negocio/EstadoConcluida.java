package fbv.com.negocio;

public class EstadoConcluida implements IEstado {

	private static final int valor = 4;
	private static final String descricao = "CONCLUIDA";
	private static IEstado instancia;
	
	private EstadoConcluida(){
	}
	
	public static IEstado getInstancia() {
		if(instancia == null)
			instancia = new EstadoConcluida();
		return instancia;
	}
	
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
