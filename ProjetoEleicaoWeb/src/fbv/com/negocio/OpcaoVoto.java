package fbv.com.negocio;

public class OpcaoVoto {
 
	private int id;
	
	private int idEleicao;
	
	private String descricao;
	 
	private String path_foto;
	
	public OpcaoVoto(){
	}
	
	public OpcaoVoto(int id){
		this.id = id;
	}

	public OpcaoVoto(int id, int idEleicao, String descricao, String pathFoto) {
		super();
		this.id = id;
		this.idEleicao = idEleicao;
		this.descricao = descricao;
		path_foto = pathFoto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEleicao() {
		return idEleicao;
	}

	public void setIdEleicao(int idEleicao) {
		this.idEleicao = idEleicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPath_foto() {
		return path_foto;
	}

	public void setPath_foto(String pathFoto) {
		path_foto = pathFoto;
	}
}
 
