package fbv.com.negocio;

import java.util.ArrayList;

public class ResultadoEleicao {

	private Eleicao eleicao;
	private int totalVotos;
	private ArrayList<ResultadoOpcaoVoto> resultadoOpcoes;
	private EleicaoEscolhaUnica eleicao2Turno;
	
	public ResultadoEleicao(Eleicao eleicao){
		this.eleicao = eleicao;
		resultadoOpcoes = new ArrayList<ResultadoOpcaoVoto>();
	}

	public Eleicao getEleicao() {
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}

	public int getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(int totalVotos) {
		this.totalVotos = totalVotos;
	}

	public ArrayList<ResultadoOpcaoVoto> getResultadoOpcoes() {
		return resultadoOpcoes;
	}

	public EleicaoEscolhaUnica getEleicao2Turno() {
		return eleicao2Turno;
	}

	public void setEleicao2Turno(EleicaoEscolhaUnica eleicao2Turno) {
		this.eleicao2Turno = eleicao2Turno;
	}
}
