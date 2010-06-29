package fbv.com.dados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.EleicaoPontuacao;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Eleicao = Nome da classe de entidade
pEleicao = Parâmetro da entidade
*/

public class RepositorioEleicaoPontuacao implements IRepositorioBD {
	
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
            try
            {
            	String sql = "INSERT INTO eleicao " +
            				 "(ID_ESTADO, DESCRICAO, IN_PUBLICA, IN_VISIBILIDADE_ABERTA, " +
            				  "IN_MAIS_DE_UM_VOTO, DT_INICIO, DT_FIM)" +
            				 "VALUES(?, ?, ?, ?, ?, ?, ?);";
            	
            	
            	PreparedStatement insert = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            	
            	insert.setInt(1, eleicao.getEstado());
            	insert.setString(2, eleicao.getDescricao());
            	insert.setBoolean(3, eleicao.isPublica());
            	insert.setBoolean(4, eleicao.isVisibilidadeVoto());
            	insert.setBoolean(5, eleicao.isMultiplosVotos());
            	insert.setDate(6, (Date) eleicao.getDataAbertura());
            	insert.setDate(7, (Date) eleicao.getDataEncerramento());
            	
            	int codigo = -1;
            	
            	ResultSet rs = insert.getGeneratedKeys();
            	
            	if (rs.next())
            		codigo = rs.getInt(1);
            	
            	insert.executeUpdate();
            	insert.close();
            	
            	sql = "INSERT INTO pontuacao " +
	   				 "(ID_ELEICAO_PONTUACAO, PONTUACAO_MINIMA, " +
	   				  "PONTUACAO_MAXIMA, GRAU_INTERVALOS)" +
	   				 "VALUES(?, ?, ?, ?);";
            	
            	insert = conexao.prepareStatement(sql);
            	insert.setInt(1, codigo);
            	insert.setInt(2, eleicao.getPontuacaoMinima());
            	insert.setInt(3, eleicao.getPontuacaoMaxima());
            	insert.setDouble(4, eleicao.getIntervaloPontuacao());
            	
            	insert.executeUpdate();
            	insert.close();
            	
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
		EleicaoPontuacao eleicao = (EleicaoPontuacao)pEleicao;
        try
        {
			int codigo = eleicao.getId();
			
            if (consultarPelaChave(codigo) != null)
            {
                try
                {
                	String sql = "UPDATE Eleicao SET " +
					   				 "ID_ESTADO = ?, DESCRICAO = ?, " + 
					   				 "IN_PUBLICA = ?, IN_VISIBILIDADE_ABERTA = ?, " +
					   				 "IN_MAIS_DE_UM_VOTO = ?, " +
					   				 "DT_INICIO = ?, DT_FIM = ?) " +
				   				 "WHERE ID_ELEICAO = ?;";
   	
				   	java.sql.PreparedStatement update = conexao.prepareStatement(sql);
				   	update.setInt(1, eleicao.getEstado());
				   	update.setString(2, eleicao.getDescricao());
				   	update.setBoolean(3, eleicao.isPublica());
				   	update.setBoolean(4, eleicao.isVisibilidadeVoto());
				   	update.setBoolean(5, eleicao.isMultiplosVotos());
				   	update.setDate(6, (Date) eleicao.getDataAbertura());
				   	update.setDate(7, (Date) eleicao.getDataEncerramento());
				   	update.setInt(8, codigo);
				   	
				   	update.execute();
				   	update.close();
				   	
	            	sql = "UPDATE INTO pontuacao SET " +
	   				 		"PONTUACAO_MINIMA = ?, " +
	   				 		"PONTUACAO_MAXIMA = ?, " +
	   				 		"GRAU_INTERVALOS = ?" +
	   				 	  "WHERE ID_ELEICAO_PONTUACAO = ?;";
           	
	            	update = conexao.prepareStatement(sql);
	            	update.setInt(1, eleicao.getPontuacaoMinima());
	            	update.setInt(2, eleicao.getPontuacaoMaxima());
	            	update.setDouble(3, eleicao.getIntervaloPontuacao());
	            	update.setInt(4, codigo);
		           	
	            	update.executeUpdate();
	            	update.close();
				   	
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
                   	statement.executeUpdate("DELETE FROM Eleicao WHERE ID_ELEICAO = " + codigo + ";");
                }
                catch (Exception e)
                {
                    throw new ExcecaoAcessoRepositorio("Erro ao excluir no banco de dados!");
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
	            	rs = statement.executeQuery("SELECT E.*, EU.* from eleicao E " + 
	            								"INNER JOIN pontuacao EU " +
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

	public ArrayList<EleicaoPontuacao> consultarTodos() throws SQLException {
		try
        {
			ArrayList<EleicaoPontuacao> retorno = null;
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT E.*, P.* from eleicao E " + 
            								"INNER JOIN pontuacao P " +
            										"ON P.ID_ELEICAO_PONTUACAO = E.ID_ELEICAO;");

            	retorno = new ArrayList<EleicaoPontuacao>();
            	
            	while (rs.next())
                {
                	EleicaoPontuacao eleicao = preencherObjeto(rs);
                	
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
	
	private EleicaoPontuacao preencherObjeto(ResultSet rs) throws SQLException{
		
    	EleicaoPontuacao eleicao = new EleicaoPontuacao();
    	//Capturando os valores do result set
    	eleicao.setId(rs.getInt("ID_ELEICAO"));
    	eleicao.setEstado(rs.getInt("ID_ESTADO"));
		eleicao.setDescricao(rs.getString ("DESCRICAO"));
		eleicao.setPublica(rs.getBoolean("IN_PUBLICA"));
		eleicao.setVisibilidadeVoto(rs.getBoolean("IN_VISIBILIDADE_ABERTA"));
		eleicao.setMultiplosVotos(rs.getBoolean("IN_MAIS_DE_UM_VOTO"));
		eleicao.setDataAbertura(rs.getDate("DT_INICIO"));
		eleicao.setDataEncerramento(rs.getDate("DT_FIM"));
		eleicao.setPontuacaoMinima(rs.getInt("PONTUACAO_MINIMA"));
		eleicao.setPontuacaoMaxima(rs.getInt("PONTUACAO_MAXIMA"));
		eleicao.setIntervaloPontuacao(rs.getInt("GRAU_INTERVALOS"));

		return eleicao;
	}
} 
