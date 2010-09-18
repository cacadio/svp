package fbv.com.negocio;

public class Usuario {
 
	private int id;
	
	private String login;
	 
	private String senha;
	 
	private String nome;
	
	private String cpf;
	
	private PerfilUsuario perfilUsuario;
	
	public Usuario(){
	}
	
	public Usuario(int id){
		this.id = id;
	}

	public Usuario(String cpf){
		this.cpf = cpf;
	}

	public Usuario(int id, String login, String senha, String nome, String cpf, PerfilUsuario perfilUsuario) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.cpf = cpf;
		this.perfilUsuario = perfilUsuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PerfilUsuario getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}
	
	
	 
}
 
