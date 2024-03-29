package fbv.com.negocio;

import java.text.SimpleDateFormat;

public class EstadoIniciada implements IEstado {

	private EstadoIniciada() {
	}

	private static final int valor = 2;
	private static final String descricao = "Iniciada";
	private static IEstado instancia;
	
	public static IEstado getInstancia() {
		if(instancia == null)
			instancia = new EstadoIniciada();
		return instancia;
	}
	
	public int getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
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
