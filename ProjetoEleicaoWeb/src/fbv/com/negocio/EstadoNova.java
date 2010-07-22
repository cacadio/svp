package fbv.com.negocio;

import java.text.SimpleDateFormat;

public class EstadoNova implements IEstado {

	public int getValor() {
		return 1;
	}

	public String getUpdateSQL(Eleicao eleicao) {
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		
    	String sql = "UPDATE Eleicao SET " +
			 "ID_ESTADO = 1, "+ 
			 "DESCRICAO = '" + eleicao.getDescricao() + "', " + 
			 "IN_PUBLICA = " + (eleicao.isPublica()? "1": "0") + ", " + 
			 "IN_VISIBILIDADE_ABERTA = " + (eleicao.isVisibilidadeVoto()? "1": "0") + ", " +
			 "IN_MAIS_DE_UM_VOTO = " + (eleicao.isMultiplosVotos()? "1": "0") + ", " +
			 "DT_FIM = " + (eleicao.getDataEncerramento() != null? "'" + sdt.format(eleicao.getDataEncerramento()) + "'": "null") + " " +
		 "WHERE ID_ELEICAO = " + eleicao.getId() + ";";
    	
		return sql;
	}
}
