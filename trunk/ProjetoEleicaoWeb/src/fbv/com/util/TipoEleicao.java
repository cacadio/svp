package fbv.com.util;

public enum TipoEleicao {
	
	ESCOLHA_UNICA(1), PONTUACAO(2);
	
	private final int value;
	
	TipoEleicao(int value){
		this.value = value;
	}
	
	public int value(){
		return this.value;
	}
}
