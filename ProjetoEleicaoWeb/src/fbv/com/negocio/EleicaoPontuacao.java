package fbv.com.negocio;

public class EleicaoPontuacao extends Eleicao {
	
	private int pontuacaoMaxima;
	
	private int pontuacaoMinima;
	
	private int intervaloPontuacao;
	
	public EleicaoPontuacao(){
		
	}

	public EleicaoPontuacao(int pontuacaoMaxima, int pontuacaoMinima,
			int intervaloPontuacao) {
		super();
		this.pontuacaoMaxima = pontuacaoMaxima;
		this.pontuacaoMinima = pontuacaoMinima;
		this.intervaloPontuacao = intervaloPontuacao;
	}

	public int getPontuacaoMaxima() {
		return pontuacaoMaxima;
	}

	public void setPontuacaoMaxima(int pontuacaoMaxima) {
		this.pontuacaoMaxima = pontuacaoMaxima;
	}

	public int getPontuacaoMinima() {
		return pontuacaoMinima;
	}

	public void setPontuacaoMinima(int pontuacaoMinima) {
		this.pontuacaoMinima = pontuacaoMinima;
	}

	public int getIntervaloPontuacao() {
		return intervaloPontuacao;
	}

	public void setIntervaloPontuacao(int intervaloPontuacao) {
		this.intervaloPontuacao = intervaloPontuacao;
	}
}
