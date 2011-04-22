package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.UsuarioPorEleicao;
import fbv.com.util.GerenciadorConexaoBDR;


public class RepositorioUsuarioPorEleicao implements IRepositorioBD {
	
	private Connection conexao;
	private Statement statement;
	
	public RepositorioUsuarioPorEleicao() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pUsuarioPorEleicao) throws ExcecaoRegistroJaExistente {
		UsuarioPorEleicao usuarioPorEleicao = (UsuarioPorEleicao)pUsuarioPorEleicao;
		//Armazenando os valores dos códigos  para passar para o statement
		int codEleicao = usuarioPorEleicao.getIdEleicao();
		int codUsuario = usuarioPorEleicao.getIdUsuario();	

		try
        {
	
            if (consultarPelaChave(usuarioPorEleicao) != null)
            {
            	throw new ExcecaoRegistroJaExistente(""+codEleicao + "-" + "codUsuario");

            }
            else
            {
                try
                {
                	//TODO: Alterar da maneira mais adequada
                	statement.executeUpdate("INSERT INTO usuario_por_eleicao VALUES(" + codEleicao + "," + codUsuario + ");");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
                } 
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+codEleicao + "-" + codUsuario);
        }

		
	}

	public void alterar(Object pUsuarioPorEleicao) throws ExcecaoAcessoRepositorio {
		// Esta operação não faz nada para a entidade em questão
	}
	
	public void excluir(Object pUsuarioPorEleicao) throws ExcecaoAcessoRepositorio {	
		UsuarioPorEleicao usuarioPorEleicao = (UsuarioPorEleicao)pUsuarioPorEleicao;
        try{
            if (consultarPelaChave(pUsuarioPorEleicao) != null)
            {
                try
                {

					//Armazenando os valores dos códigos  para passar para o statement
					int codEleicao = usuarioPorEleicao.getIdEleicao();
					int codUsuario = usuarioPorEleicao.getIdUsuario();	
					
                   	statement.executeUpdate("DELETE FROM usuario_por_eleicao WHERE ELEICAO_ID_ELEICAO = "  + codEleicao + 
					 "/n AND USUARIO_ID_USUARIO = " + codUsuario + ";");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Não é possível excluir pois" + '\n' + " não existe esta relação de Usário/Eleição !");
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoAcessoRepositorio(e.getMessage() );
        }

	}
	
	//	------------------	
    //BUSCA PELA CHAVE PRIMÁRIA
	//-------------------
	public UsuarioPorEleicao consultarPelaChave(Object pUsuarioPorEleicao) throws ExcecaoRegistroNaoExistente {
		UsuarioPorEleicao usuarioPorEleicao = (UsuarioPorEleicao)pUsuarioPorEleicao;
	try
        {
			ResultSet rs = null;
			UsuarioPorEleicao retorno = null;

            try
            {
					
				//Armazenando os valores dos códigos  para passar para o statement
				int codEleicao = usuarioPorEleicao.getIdEleicao();
				int codUsuario = usuarioPorEleicao.getIdUsuario();	
				
            	rs = statement.executeQuery("SELECT  * from usuario_por_eleicao WHERE ELEICAO_ID_ELEICAO = "  + codEleicao + 
					 "/n AND USUARIO_ID_USUARIO = " + codUsuario + ";");
                
				if (rs.next())
                {
					retorno = usuarioPorEleicao;
                }

                return retorno;                               

            }
            catch (Exception e)
            {
                throw new ExcecaoAcessoRepositorio("Erro ao buscar no banco de dados!" /*+ "/n" + e.toString()*/);
            }
            
			finally
			{
				if (rs != null)
				{
					try {rs.close();}catch(Exception e) {}
				}
				if (conexao != null)
				{
					try {statement.close();}catch(Exception e) {}
				}		
				if (conexao != null)
				{
					try {conexao.close();}catch(Exception e) {}
				}				
			}
       }
        catch (Exception e)
        {
            throw new ExcecaoRegistroNaoExistente(e.getMessage() /*+ "/n" + e.toString()*/);
        }
		
	}

	public ArrayList<UsuarioPorEleicao> consultarTodos() throws ExcecaoRegistroNaoExistente {
		ArrayList<UsuarioPorEleicao> colecao = new ArrayList<UsuarioPorEleicao>();
		try
        {	
			ResultSet rs = null;
			UsuarioPorEleicao usuarioPorEleicao = null;
            try
            {
            	rs = statement.executeQuery("SELECT * from usuario_por_eleicao ;");
                while (rs.next())
                {
				
                	//Capturando os valores do result set
                	int codEleicao = rs.getInt ("ELEICAO_ID_ELEICAO");
					int codUsuario = rs.getInt ("USUARIO_ID_USUARIO");
					//Criando uma nova instância do arraylist com os parâmetros capturados
					usuarioPorEleicao = new UsuarioPorEleicao();
					usuarioPorEleicao.setIdEleicao(codEleicao); 
					usuarioPorEleicao.setIdUsuario(codUsuario); 
					//armanenando a instância em retorno
					colecao.add(usuarioPorEleicao);                               
                }

            }
            catch (Exception e)
            {
                throw new ExcecaoAcessoRepositorio("Erro ao buscar no banco de dados!" /*+ "/n" + e.toString()*/);
            }
            
			finally
			{
				if (rs != null)
				{
					try {rs.close();}catch(Exception e) {}
				}
				if (conexao != null)
				{
					try {statement.close();}catch(Exception e) {}
				}		
				if (conexao != null)
				{
					try {conexao.close();}catch(Exception e) {}
				}				
			}
       }
        catch (Exception e)
        {
            throw new ExcecaoRegistroNaoExistente(e.getMessage() /*+ "/n" + e.toString()*/);
        }
		return colecao;
	}
	
} 
