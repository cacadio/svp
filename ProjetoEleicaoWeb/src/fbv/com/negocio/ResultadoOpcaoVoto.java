package fbv.com.negocio;

public class ResultadoOpcaoVoto {
	private OpcaoVoto opcaoVoto;
	private int totalVotos;
	private double percentualVotos;
	
	public ResultadoOpcaoVoto(OpcaoVoto opcao){
		this.opcaoVoto = opcao;
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

	public double getPercentualVotos() {
		return percentualVotos;
	}

	public void setPercentualVotos(double percentualVotos) {
		this.percentualVotos = percentualVotos;
	}
}
