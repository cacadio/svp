package fbv.com.negocio;

public class PerfilUsuario {
	
	private int id;
	
	private String descricao;

	
	public PerfilUsuario(){
		
	}
	
	public PerfilUsuario(int id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
 
