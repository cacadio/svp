package fbv.com.negocio;

public class EleicaoEscolhaUnica extends Eleicao {
	
	private boolean campoNulo;
	
	private double percentualVitoria;
	
	private EleicaoEscolhaUnica eleicaoPai = null;
	
	public EleicaoEscolhaUnica (){
		
	}

	public EleicaoEscolhaUnica(boolean inCampoNulo, double percentualVitoria,
			EleicaoEscolhaUnica eleicaoPai) {
		super();
		this.campoNulo = inCampoNulo;
		this.percentualVitoria = percentualVitoria;
		this.eleicaoPai = eleicaoPai;
	}
	
	public EleicaoEscolhaUnica(int id){
		super(id);
	}

	public boolean isCampoNulo() {
		return campoNulo;
	}

	public void setCampoNulo(boolean inCampoNulo) {
		this.campoNulo = inCampoNulo;
	}

	public double getPercentualVitoria() {
		return percentualVitoria;
	}

	public void setPercentualVitoria(double percentualVitoria) {
		this.percentualVitoria = percentualVitoria;
	}

	public EleicaoEscolhaUnica getEleicaoPai() {
		return eleicaoPai;
	}

	public void setEleicaoPai(EleicaoEscolhaUnica eleicaoPai) {
		this.eleicaoPai = eleicaoPai;
	}
}
