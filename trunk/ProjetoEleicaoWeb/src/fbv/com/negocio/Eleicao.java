package fbv.com.negocio;

import java.util.Date;


public class Eleicao {
	
	private int id;
	private String descricao;
	private boolean publica;
	private boolean visibilidadeVoto;
	private Date dataAbertura;
	private Date dataEncerramento;
	private IEstado estado;
	private boolean multiplosVotos;

	public static final IEstado NOVA = EstadoNova.getInstancia();
	public static final IEstado INICIADA = EstadoIniciada.getInstancia();
	public static final IEstado EMCURSO = EstadoEmCurso.getInstancia();
	public static final IEstado CONCLUIDA = EstadoConcluida.getInstancia();
	public static final IEstado APURADA = EstadoApurada.getInstancia();

	
	public Eleicao(){
		
	}
	
	public Eleicao(int id){
		this.id = id;
	}

	public Eleicao(int id, String descricao, boolean publica,
			boolean visibilidadeVoto, Date dataAbertura,
			Date dataEncerramento, IEstado estado, boolean multiplosVotos) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.publica = publica;
		this.visibilidadeVoto = visibilidadeVoto;
		this.dataAbertura = dataAbertura;
		this.dataEncerramento = dataEncerramento;
		this.estado = estado;
		this.multiplosVotos = multiplosVotos;
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

	public boolean isPublica() {
		return publica;
	}

	public void setPublica(boolean inPublica) {
		this.publica = inPublica;
	}

	public boolean isVisibilidadeVoto() {
		return visibilidadeVoto;
	}

	public void setVisibilidadeVoto(boolean inVisibilidadeVoto) {
		this.visibilidadeVoto = inVisibilidadeVoto;
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

	public IEstado getEstado() {
		return estado;
	}

	public void setEstado(IEstado estado) {
		this.estado = estado;
	}

	public boolean isMultiplosVotos() {
		return multiplosVotos;
	}

	public void setMultiplosVotos(boolean inMultiplosVotos) {
		this.multiplosVotos = inMultiplosVotos;
	}
}
 
