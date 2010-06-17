package fbv.com.negocio;

import java.sql.Timestamp;

public class Voto {
	
	private int idVoto;
	
	private int idEleicao;
	
	private int idUsuario;
	
	private int idOpcaoVoto;
	
	private double valorVoto;
	
	private Timestamp dataHora;
	
	public Voto(){
		
	}

	public Voto(int idVoto, int idEleicao, int idUsuario, int idOpcaoVoto,
			double valorVoto, Timestamp dataHora) {
		super();
		this.idVoto = idVoto;
		this.idEleicao = idEleicao;
		this.idUsuario = idUsuario;
		this.idOpcaoVoto = idOpcaoVoto;
		this.valorVoto = valorVoto;
		this.dataHora = dataHora;
	}

	public int getIdVoto() {
		return idVoto;
	}

	public void setIdVoto(int idVoto) {
		this.idVoto = idVoto;
	}

	public int getIdEleicao() {
		return idEleicao;
	}

	public void setIdEleicao(int idEleicao) {
		this.idEleicao = idEleicao;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdOpcaoVoto() {
		return idOpcaoVoto;
	}

	public void setIdOpcaoVoto(int idOpcaoVoto) {
		this.idOpcaoVoto = idOpcaoVoto;
	}

	public double getValorVoto() {
		return valorVoto;
	}

	public void setValorVoto(double valorVoto) {
		this.valorVoto = valorVoto;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
}
