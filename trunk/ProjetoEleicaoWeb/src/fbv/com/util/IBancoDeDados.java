package fbv.com.util;
import fbv.com.excecoes.ExcecaoAcessoRepositorio;

public interface IBancoDeDados {
	 
	public abstract void beginTransaction() throws ExcecaoAcessoRepositorio;
	public abstract void commitTransaction() throws ExcecaoAcessoRepositorio;
	public abstract void rollbackTransaction();
}
 
