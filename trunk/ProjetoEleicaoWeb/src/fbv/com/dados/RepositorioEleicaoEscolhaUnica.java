package fbv.com.dados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.EleicaoEscolhaUnica;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maur�cio Veiga ----------------------------------------------
Eleicao = Nome da classe de entidade
pEleicao = Par�metro da entidade
*/

public class RepositorioEleicaoEscolhaUnica implements IRepositorioBD {
	
	//private GerenciadorConexaoBDR banco;
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioEleicaoEscolhaUnica() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		//this.conexao = conexao;
		statement = conexao.createStatement();		
	}
	

//	M�todos implementados no reposit�rio
	
	public void incluir(Object pEleicao) throws ExcecaoRegistroJaExistente {
		EleicaoEscolhaUnica eleicao = (EleicaoEscolhaUnica)pEleicao;
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            try
            {
             	String sql = "INSERT INTO eleicao " +
            				 "(ID_ESTADO, DESCRICAO, IN_PUBLICA, IN_VISIBILIDADE_ABERTA, " +
            				  "IN_MAIS_DE_UM_VOTO, DT_INICIO, DT_FIM)" +
            				 "VALUES(" + eleicao.getEstado() + ", '" + eleicao.getDescricao() + "', " + 
            				 (eleicao.isPublica()? "1": "0") + ", " + 
            				 (eleicao.isVisibilidadeVoto()? "1": "0") + ", " + 
            				 (eleicao.isMultiplosVotos()? "1": "0") + ", '" + 
            				 sdt.format(eleicao.getDataAbertura())  + "', '" + 
            				 sdt.format(eleicao.getDataEncerramento()) + "');";
            	
            	statement.executeUpdate(sql);
            	
            	int codigo = -1;
            	
            	ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
            	
            	if (rs.next())
            		codigo = rs.getInt(1);
            	
            	sql = "INSERT INTO escolha_unica " +
	   				 "(ID_ELEICAO_ESCOLHA_UNICA, ID_ELEICAO_PAI, " +
	   				  "BRANCO_NULO, PERCENT_VITORIA)" +
	   				 "VALUES(" + codigo + ", " + 
	   				 (eleicao.getEleicaoPai() != null? eleicao.getEleicaoPai().getId(): "null") + ", " + 
	   				(eleicao.isCampoNulo()? "1": "0") + ", " +
	   				eleicao.getPercentualVitoria() + ");";
            	
            	statement.executeUpdate(sql);
            	
            	eleicao.setId(codigo);
            }
            catch (Exception e)
            {
                throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
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
			int codigo = eleicao.getId();
			
            if (consultarPelaChave(codigo) != null)
            {
                try
                {
                	SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
                	
                	String sql = "UPDATE Eleicao SET " +
					   				 "ID_ESTADO = " + eleicao.getEstado() + ", "+ 
					   				 "DESCRICAO = '" + eleicao.getDescricao() + "', " + 
					   				 "IN_PUBLICA = " + (eleicao.isPublica()? "1": "0") + ", " + 
					   				 "IN_VISIBILIDADE_ABERTA = " + (eleicao.isVisibilidadeVoto()? "1": "0") + ", " +
					   				 "IN_MAIS_DE_UM_VOTO = " + (eleicao.isMultiplosVotos()? "1": "0") + ", " +
					   				 "DT_INICIO = '" + sdt.format(eleicao.getDataAbertura()) + "', " + 
					   				 "DT_FIM = '" + sdt.format(eleicao.getDataEncerramento()) + "' " +
				   				 "WHERE ID_ELEICAO = " + ";";
   	
                	statement.executeUpdate(sql);
				   /*	java.sql.PreparedStatement update = conexao.prepareStatement(sql);
				   	update.setInt(1, eleicao.getEstado());
				   	update.setString(2, eleicao.getDescricao());
				   	update.setBoolean(3, eleicao.isPublica());
				   	update.setBoolean(4, eleicao.isVisibilidadeVoto());
				   	update.setBoolean(5, eleicao.isMultiplosVotos());
				   	update.setDate(6, (Date) eleicao.getDataAbertura());
				   	update.setDate(7, (Date) eleicao.getDataEncerramento());
				   	update.setInt(8, codigo);
				   	
				   	update.execute();
				   	update.close();*/
				   	
	            	sql = "UPDATE INTO escolha_unica SET " +
	   				 		"ID_ELEICAO_PAI = " + (eleicao.getEleicaoPai() != null? eleicao.getEleicaoPai().getId(): "null") + ", " +
	   				 		"BRANCO_NULO = " + ", " +
	   				 		"PERCENT_VITORIA = " + "" +
	   				 	  "WHERE ID_ELEICAO_ESCOLHA_UNICA = " + ";";
           	
	            	/*update = conexao.prepareStatement(sql);
	            	update.setInt(1, eleicao.getIdEleicaoPai());
	            	update.setBoolean(2, eleicao.isCampoNulo());
	            	update.setDouble(3, eleicao.getPercentualVitoria());
	            	update.setInt(4, codigo);
		           	
	            	update.executeUpdate();
	            	update.close();*/
				   	
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao alterar no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("Erro ao remover no banco de dados! Registro n�o Existente!");
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
                   	statement.executeUpdate("DELETE FROM Eleicao WHERE ID_ELEICAO = " + codigo + ";");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao excluir no banco de dados!");
                }
            }
            else
            {
                throw new ExcecaoRegistroNaoExistente("N�o � poss�vel excluir pois" + '\n' + " n�o existe este Eleicao !");
            }
        }
        catch (Exception e)
        {
            throw new ExcecaoAcessoRepositorio(e.getMessage() );
        }

	}
	
	//	------------------	
    //BUSCA PELA CHAVE PRIM�RIA
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
	            	rs = statement.executeQuery("SELECT E.*, EU.* from eleicao E " + 
	            								"INNER JOIN escolha_unica EU " +
	            										"ON EU.ID_ELEICAO_ESCOLHA_UNICA = E.ID_ELEICAO " +
	            								"WHERE E.ID_ELEICAO = " + codigo + ";");
	                while (rs.next())
	                {
	                	retorno = preencherObjeto(rs);
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

	public ArrayList<EleicaoEscolhaUnica> consultarTodos() throws SQLException {
		try
        {
			ArrayList<EleicaoEscolhaUnica> retorno = null;
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT E.*, EU.* from eleicao E " + 
            								"INNER JOIN escolha_unica EU " +
            										"ON EU.ID_ELEICAO_ESCOLHA_UNICA = E.ID_ELEICAO;");

            	retorno = new ArrayList<EleicaoEscolhaUnica>();
            	
            	while (rs.next())
                {
                	EleicaoEscolhaUnica eleicao = preencherObjeto(rs);
                	
                	retorno.add(eleicao);
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
	        throw new SQLException(e.getMessage());
	    }
	}
	
	private EleicaoEscolhaUnica preencherObjeto(ResultSet rs) throws SQLException{
		
    	EleicaoEscolhaUnica eleicao = new EleicaoEscolhaUnica();
    	//Capturando os valores do result set
    	eleicao.setId(rs.getInt("ID_ELEICAO"));
    	eleicao.setEstado(rs.getInt("ID_ESTADO"));
		eleicao.setDescricao(rs.getString ("DESCRICAO"));
		eleicao.setPublica(rs.getBoolean("IN_PUBLICA"));
		eleicao.setVisibilidadeVoto(rs.getBoolean("IN_VISIBILIDADE_ABERTA"));
		eleicao.setMultiplosVotos(rs.getBoolean("IN_MAIS_DE_UM_VOTO"));
		eleicao.setDataAbertura(rs.getDate("DT_INICIO"));
		eleicao.setDataEncerramento(rs.getDate("DT_FIM"));
		if (rs.getObject("ID_ELEICAO_PAI") != null)
			try {
				eleicao.setEleicaoPai(consultarPelaChave(new EleicaoEscolhaUnica(rs.getInt("ID_ELEICAO_PAI"))));
			} catch (ExcecaoRegistroNaoExistente e) {
				e.printStackTrace();
			}
		eleicao.setCampoNulo(rs.getBoolean("BRANCO_NULO"));
		eleicao.setPercentualVitoria(rs.getDouble("PERCENT_VITORIA"));

		return eleicao;
	}
} 
