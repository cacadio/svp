package fbv.com.util;

public enum EstadoEleicao {
	
	CRIADA(1),	INICIADA(2), FECHADA(3);

	private final int value;
	
	EstadoEleicao(int value) {  
      this.value = value;  
    }  
  
    public int value() {  
      return this.value;  
    }
}
