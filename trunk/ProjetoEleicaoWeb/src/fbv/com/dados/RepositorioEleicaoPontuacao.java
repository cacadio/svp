package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Eleicao = Nome da classe de entidade
pEleicao = Parâmetro da entidade
*/

public class RepositorioEleicaoPontuacao implements IRepositorioBD {
	
	private GerenciadorConexaoBDR banco;
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioEleicaoPontuacao() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pEleicao) throws ExcecaoRegistroJaExistente {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
        try
        {
			//Armazenando os valores do código e a Descrição do Eleicao para passar para o statement
			int codigo = eleicao.getId();
			//String desc = pEleicao.getDescricao();			
			
            if (consultarPelaChave(codigo) != null)
            {
            	throw new ExcecaoRegistroJaExistente(""+eleicao.getId());

            }
            else
            {
                try
                {
                	//TODO: Alterar da maneira mais adequada
                	//statement.executeUpdate("INSERT INTO Eleicao VALUES(" + codigo + ",'" + desc + "');");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
                } 
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+eleicao.getId());
        }

		
	}
	public void alterar(Object pEleicao) throws ExcecaoAcessoRepositorio {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
        try
        {

			//Armazenando os valores do código e a Descrição do Eleicao para passar para o statement
			int codigo = eleicao.getId();
			//String desc = eleicao.getDescricao();			
			
            if (consultarPelaChave(codigo) != null)
            {
            	
                try
                {
                	//TODO: Alterar da maneira mais adequada
                	//statement.executeUpdate("UPDATE Eleicao SET descEleicao = '"+ desc +"' WHERE codEleicao = " + codigo + ";");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Erro ao remover no banco de dados! Registro não Existente!");
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoAcessoRepositorio("Erro ao inicializar dados do Repositorio!" );
        }
	}
	
	public void excluir(Object pEleicao) throws ExcecaoAcessoRepositorio {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
		int codigo = eleicao.getId();
        try
        {
			
            if (consultarPelaChave(codigo) != null)
            {
                try
                {
                   	statement.executeUpdate("DELETE FROM Eleicao WHERE codEleicao = " + codigo + ";");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Não é possível excluir pois" + '\n' + " não existe este Eleicao !");
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
	public EleicaoPontuacao consultarPelaChave(Object pEleicao) throws ExcecaoRegistroNaoExistente {
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
		int codigo = eleicao.getId();
		try
	        {
				ResultSet rs = null;
	            try
	            {
	            	EleicaoPontuacao retorno = null;
	            	rs = statement.executeQuery("SELECT  * from Eleicao WHERE codEleicao = " + codigo + ";");
	                while (rs.next())
	                {
	                	//Capturando os valores do result set
	                	int codEleicao = rs.getInt ("ID_Eleicao");
						String dsEleicao = rs.getString ("DS_Eleicao");
						//Criando uma nova instância de Eleicao com os parâmetros capturados
	                    //armanenando a instância em retorno
						//retorno = new EstadoConservacao(codEleicao,dsEleicao);
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

	public ArrayList consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}
} 
