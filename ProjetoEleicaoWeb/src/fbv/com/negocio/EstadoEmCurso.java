package fbv.com.negocio;

public class EstadoEmCurso implements IEstado {

	private static final int valor = 3;
	private static final String descricao = "Em Curso";
	private static IEstado instancia;
	
	private EstadoEmCurso(){
	}
	
	public static IEstado getInstancia() {
		if(instancia == null)
			instancia = new EstadoEmCurso();
		return instancia;
	}
	
	public int getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUpdateSQL(Eleicao eleicao) {
		
    	String sql = "UPDATE Eleicao SET " +
			 "ID_ESTADO = 3 "+ 
		 "WHERE ID_ELEICAO = " + eleicao.getId() + ";";
    	
		return sql;
	}

}
