package fbv.com.servlets;

import java.sql.SQLException;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.PerfilUsuario;

public class TesteProjetoWeb {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PerfilUsuario pu = new PerfilUsuario();
		ArrayList<PerfilUsuario> colecaoPerfilUsuario = new ArrayList<PerfilUsuario>();
		
		pu.setId(3);
		pu.setDescricao("Teste3");
		
		Fachada fachada;
		try {
			fachada = Fachada.getInstancia();
			fachada.incluirPerfilUsuario(pu);
			System.out.println("Funcionou");
			
			colecaoPerfilUsuario = fachada.consultarTodosPerfilUsuario();
			
			if(colecaoPerfilUsuario != null){
				for(Object obj : colecaoPerfilUsuario.toArray()){
					pu = (PerfilUsuario)obj;
					System.out.println(pu.getId());
					System.out.println(pu.getDescricao());
				}
			}
		} catch (ExcecaoAcessoRepositorio e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
//		} catch (ExcecaoRegistroJaExistente e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (ExcecaoRegistroNaoExistente e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcecaoRegistroJaExistente e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
