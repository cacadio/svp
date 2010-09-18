package fbv.com.negocio;

public interface IEstado {	
	public int getValor();
	public String getDescricao();
	public String getUpdateSQL(Eleicao eleicao);
}
