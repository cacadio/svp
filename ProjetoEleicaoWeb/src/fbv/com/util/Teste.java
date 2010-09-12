package fbv.com.util;

import java.sql.SQLException;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.negocio.Fachada;
import fbv.com.negocio.PerfilUsuario;
import fbv.com.negocio.Usuario;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PerfilUsuario pu = new PerfilUsuario();
		Usuario us = new Usuario();
		
		pu.setId(1);
		pu.setDescricao("Administrador");
		
		us.setCpf("08976345632");
		us.setLogin("o_cara_2");
		us.setSenha("1234");
		us.setNome("Ronaldo_Alterado");
		us.setPerfilUsuario(pu);
		
		Fachada fachada;
		try {
			fachada = Fachada.getInstancia();
			//fachada.incluirPerfilUsuario(pu);
			//fachada.excluirPerfilUsuario(pu);
			//fachada.alterarPerfilUsuario(pu);
//			colecaoPerfilUsuario = fachada.consultarTodosPerfilUsuario();
//			if(colecaoPerfilUsuario != null){
//				for(Object obj : colecaoPerfilUsuario.toArray()){
//					pu = (PerfilUsuario)obj;
//					System.out.println(pu.getId());
//					System.out.println(pu.getDescricao());
//				}
//			}
			
			fachada.incluirUsuario(us);
			
			System.out.println("Esta Funcionando...");
		} catch (ExcecaoAcessoRepositorio e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ExcecaoRegistroJaExistente e) {
			e.printStackTrace();
		}
	}

}
