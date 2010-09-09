package fbv.com.negocio;

public class ResultadoEleicao {
	private Eleicao eleicao;
	private OpcaoVoto opcaoVoto;
	private int totalVotos;
	
	public ResultadoEleicao(Eleicao eleicao){
		this.eleicao = eleicao;
	}

	public Eleicao getEleicao() {
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}

	public OpcaoVoto getOpcaoVoto() {
		return opcaoVoto;
	}

	public void setOpcaoVoto(OpcaoVoto opcaoVoto) {
		this.opcaoVoto = opcaoVoto;
	}

	public int getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(int totalVotos) {
		this.totalVotos = totalVotos;
	}
}
