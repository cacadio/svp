package fbv.com.negocio;

import java.text.SimpleDateFormat;

public class EstadoApurada implements IEstado {

	private static final int valor = 5;
	private static final String descricao = "Apurada";
	private static IEstado instancia;
	
	private EstadoApurada(){
		
	}
	
	public static IEstado getInstancia() {
		if(instancia == null)
			instancia = new EstadoApurada();
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
			 "ID_ESTADO = " + valor + ", "+ 
			 "DT_FIM = '" + sdt.format(eleicao.getDataEncerramento()) + "' " +
		 "WHERE ID_ELEICAO = " + eleicao.getId() + ";";
    	
		return sql;
	}



}
