package fbv.com.negocio;

import java.util.Date;

import fbv.com.util.EstadoEleicao;

public class Eleicao {
	
	private int id;
 
	private String descricao;
	
	private boolean inPublica;
	
	private boolean inVisibilidadeVoto;

	private Date dataAbertura;
	
	private Date dataEncerramento;

	private int estado;
	 
	private boolean inMultiplosVotos;
	
	public Eleicao(){
		
	}

	public Eleicao(int id, String descricao, boolean inPublica,
			boolean inVisibilidadeVoto, Date dataAbertura,
			Date dataEncerramento, int estado, boolean inMultiplosVotos) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.inPublica = inPublica;
		this.inVisibilidadeVoto = inVisibilidadeVoto;
		this.dataAbertura = dataAbertura;
		this.dataEncerramento = dataEncerramento;
		this.estado = estado;
		this.inMultiplosVotos = inMultiplosVotos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isInPublica() {
		return inPublica;
	}

	public void setInPublica(boolean inPublica) {
		this.inPublica = inPublica;
	}

	public boolean isInVisibilidadeVoto() {
		return inVisibilidadeVoto;
	}

	public void setInVisibilidadeVoto(boolean inVisibilidadeVoto) {
		this.inVisibilidadeVoto = inVisibilidadeVoto;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public boolean isInMultiplosVotos() {
		return inMultiplosVotos;
	}

	public void setInMultiplosVotos(boolean inMultiplosVotos) {
		this.inMultiplosVotos = inMultiplosVotos;
	}
}
 
