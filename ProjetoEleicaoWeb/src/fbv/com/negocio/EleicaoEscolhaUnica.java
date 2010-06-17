package fbv.com.negocio;

public class EleicaoEscolhaUnica extends Eleicao {
	
	private boolean inCampoNulo;
	
	private double percentualVitoria;
	
	private int idEleicaoPai;
	
	public EleicaoEscolhaUnica (){
		
	}

	public EleicaoEscolhaUnica(boolean inCampoNulo, double percentualVitoria,
			int idEleicaoPai) {
		super();
		this.inCampoNulo = inCampoNulo;
		this.percentualVitoria = percentualVitoria;
		this.idEleicaoPai = idEleicaoPai;
	}

	public boolean isInCampoNulo() {
		return inCampoNulo;
	}

	public void setInCampoNulo(boolean inCampoNulo) {
		this.inCampoNulo = inCampoNulo;
	}

	public double getPercentualVitoria() {
		return percentualVitoria;
	}

	public void setPercentualVitoria(double percentualVitoria) {
		this.percentualVitoria = percentualVitoria;
	}

	public int getIdEleicaoPai() {
		return idEleicaoPai;
	}

	public void setIdEleicaoPai(int idEleicaoPai) {
		this.idEleicaoPai = idEleicaoPai;
	}
}
