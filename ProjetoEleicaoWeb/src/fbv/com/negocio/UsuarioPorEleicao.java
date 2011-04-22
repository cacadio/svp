package fbv.com.negocio;

//Lista de imports

//Assintarura da Entidade
public class UsuarioPorEleicao {

	// Lista de atributos da entidade
	private Integer idEleicao; 
	private Integer idUsuario; 
	
	
   	// Construtor vazio
	public UsuarioPorEleicao() {
    	// não faz nada
	}
	
    // Construtor com todos os atributos
    public UsuarioPorEleicao(Integer pIdEleicao, Integer pIdUsuario){
		idEleicao = pIdEleicao; 
		idUsuario = pIdUsuario; 
	}
    
    // Lista de gets e sets
    

	public Integer getIdEleicao(){ 
		return idEleicao; 
	 }

	public void setIdEleicao(Integer pIdEleicao) { 
		this.idEleicao = pIdEleicao; 
	 }

	public Integer getIdUsuario(){ 
		return idUsuario; 
	 }

	public void setIdUsuario(Integer pIdUsuario) { 
		this.idUsuario = pIdUsuario; 
	 }
}
