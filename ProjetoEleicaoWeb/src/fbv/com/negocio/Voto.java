package fbv.com.negocio;

import java.sql.Timestamp;

public class Voto {
	
	private int idVoto;
	
	private Eleicao eleicao;
	
	private Usuario usuario;
	
	private OpcaoVoto opcaoVoto;
	
	private double valorVoto;
	
	private Timestamp dataHora;
	
	public Voto(){	
	}

	public Voto(int idVoto, Eleicao pEleicao, Usuario pUsuario, OpcaoVoto pOpcaoVoto,
			double valorVoto, Timestamp dataHora) {
		super();
		this.idVoto = idVoto;
		this.eleicao = pEleicao;
		this.usuario = pUsuario;
		this.opcaoVoto = pOpcaoVoto;
		this.valorVoto = valorVoto;
		this.dataHora = dataHora;
	}

	public int getIdVoto() {
		return idVoto;
	}

	public void setIdVoto(int idVoto) {
		this.idVoto = idVoto;
	}

	public Eleicao getEleicao() {
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public OpcaoVoto getOpcaoVoto() {
		return opcaoVoto;
	}

	public void setOpcaoVoto(OpcaoVoto pOpcaoVoto) {
		this.opcaoVoto = pOpcaoVoto;
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
