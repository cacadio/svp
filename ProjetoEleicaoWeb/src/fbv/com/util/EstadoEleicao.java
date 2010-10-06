package fbv.com.util;

import fbv.com.negocio.Eleicao;
import fbv.com.negocio.IEstado;

public class EstadoEleicao {
	public static IEstado getEstado(int estado){
		switch (estado) {
		case 1:
			return Eleicao.NOVA;
		case 2:
			return Eleicao.INICIADA;
		case 3:
			return Eleicao.EMCURSO;
		case 5:
			return Eleicao.APURADA;
		case 4:
			return Eleicao.CONCLUIDA;
		default:
			return Eleicao.NOVA;
		}
		
	}
}
