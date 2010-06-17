package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Eleicao = Nome da classe de entidade
pEleicao = Parâmetro da entidade
*/

public class RepositorioEleicaoEscolhaUnica implements IRepositorioBD {
	
	private GerenciadorConexaoBDR banco;
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioEleicaoEscolhaUnica() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pEleicao) throws ExcecaoRegistroJaExistente {
		EleicaoEscolhaUnica eleicao = (EleicaoEscolhaUnica)pEleicao;
        try
        {
			//Armazenando os valores do código e a Descrição do Eleicao para passar para o statement
			int codigo = eleicao.getId();
			String desc = eleicao.getDescricao();
			boolean inPublica = eleicao.isInPublica();
			boolean inVisibilidadeVoto = eleicao.isInVisibilidadeVoto();
			Date dataAbertura = eleicao.getDataAbertura();
			Date dataEncerramento = eleicao.getDataEncerramento();
			int estado = eleicao.getEstado();
			boolean inMultiplosVotos = eleicao.isInMultiplosVotos();
			
			boolean inCampoNulo = eleicao.isInCampoNulo();
			double percentualVitoria = eleicao.getPercentualVitoria();
			int idEleicaoPai = eleicao.getIdEleicaoPai();
			
            if (consultarPelaChave(eleicao) != null)
            {
            	throw new ExcecaoRegistroJaExistente(""+eleicao.getId());

            }
            else
            {
                try
                {
                	statement.executeUpdate("INSERT INTO ESCOLHA_UNICA VALUES(" + codigo + ",'" + desc + "');");
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
		EleicaoEscolhaUnica eleicao = (EleicaoEscolhaUnica)pEleicao;
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
                	//statement.executeUpdate("UPDATE ESCOLHA_UNICA SET descEleicao = '"+ desc +"' WHERE codEleicao = " + codigo + ";");
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
		EleicaoEscolhaUnica eleicao = (EleicaoEscolhaUnica)pEleicao;
		int codigo = eleicao.getId();
        try
        {
			
            if (consultarPelaChave(codigo) != null)
            {
                try
                {
                   	statement.executeUpdate("DELETE FROM ESCOLHA_UNICA WHERE codEleicao = " + codigo + ";");
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
	public EleicaoEscolhaUnica consultarPelaChave(Object pEleicao) throws ExcecaoRegistroNaoExistente {
		EleicaoEscolhaUnica eleicao = (EleicaoEscolhaUnica)pEleicao;
		int codigo = eleicao.getId();
		try
	        {
				ResultSet rs = null;
	            try
	            {
	            	EleicaoEscolhaUnica retorno = null;
	            	rs = statement.executeQuery("SELECT  * from ESCOLHA_UNICA WHERE codEleicao = " + codigo + ";");
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
