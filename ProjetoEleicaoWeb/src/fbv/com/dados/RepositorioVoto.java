package fbv.com.dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;
import fbv.com.excecoes.ExcecaoRegistroJaExistente;
import fbv.com.excecoes.ExcecaoRegistroNaoExistente;
import fbv.com.negocio.Eleicao;
import fbv.com.negocio.OpcaoVoto;
import fbv.com.negocio.Usuario;
import fbv.com.negocio.Voto;
import fbv.com.util.GerenciadorConexaoBDR;

/*
Gerador Maurício Veiga ----------------------------------------------
Opcaovoto = Nome da classe de entidade
pOpcaovoto = Parâmetro da entidade
*/

public class RepositorioVoto implements IRepositorioBD {
	
	private Connection conexao;
	private Statement statement; 
	
	public RepositorioVoto() throws ExcecaoAcessoRepositorio, SQLException{
		GerenciadorConexaoBDR banco = GerenciadorConexaoBDR.getMyInstance();
		Connection conexao = banco.getConexao(false);
		statement = conexao.createStatement();		
	}
	

//	Métodos implementados no repositório
	
	public void incluir(Object pVoto) throws ExcecaoRegistroJaExistente {
		Voto voto =(Voto) pVoto;
        try
        {
			//Armazenando os valores do código e a Descrição do Voto para passar para o statement
        	int codigo = 0;
			int idUsuario = voto.getUsuario().getId();
			int idEleicao = voto.getEleicao().getId();
			int idOpcaoVoto = voto.getOpcaoVoto().getId();
			double vlVoto = voto.getValorVoto();

            try
            {
            	ResultSet rs = statement.executeQuery("select max(ID_VOTO) as maximo from Voto;");
            	
            	if(rs.next()){
            		codigo = rs.getInt("maximo") + 1;                		
            	}else{
            		codigo = 1;
            	}
            	
            	String sql = 
            		"INSERT INTO Voto (ID_VOTO,ID_USUARIO,ID_ELEICAO,ID_OPCAO_VOTO,VALOR_VOTO) VALUES("+ codigo + ","  + idUsuario + "," + idEleicao + ","+ idOpcaoVoto + "," + vlVoto +");";
            	statement.executeUpdate(sql);
            
            	
            }
            catch (Exception e)
            {
                throw new ExcecaoAcessoRepositorio("Erro ao incluir no banco de dados!" /*+ "/n" + e.ToString()*/);
            } 
        }
        catch (Exception e)
        {
            throw new ExcecaoRegistroJaExistente(""+voto.getIdVoto());
        }

		
	}
	public void alterar(Object pVoto) throws ExcecaoAcessoRepositorio {
		System.out.println("VOTO NAO SE ALTERA");
	}
	
	public void excluir(Object pVoto) throws ExcecaoAcessoRepositorio {
		System.out.println("VOTO NAO SE EXCLUI");
	}
	
	//	------------------	
    //BUSCA PELA CHAVE PRIMÁRIA
	//-------------------
	public Voto consultarPelaChave(Object pVoto) throws ExcecaoRegistroNaoExistente {
		Voto voto = (Voto)pVoto;
		int codigo = voto.getIdVoto();
		try
        {
			ResultSet rs = null;
            try
            {
            	Voto retorno = null;
            	rs = statement.executeQuery("SELECT  * from Voto WHERE ID_VOTO = " + codigo + ";");
            	
                while (rs.next())
                {
                	retorno = obterVoto(rs);
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


	public ArrayList<Voto> consultarTodos() throws SQLException, ExcecaoRegistroNaoExistente {
		ArrayList<Voto> colecaoVoto = new ArrayList<Voto>();
		Voto voto = null;
		try
        {
			ResultSet rs = null;
            try
            {
            	rs = statement.executeQuery("SELECT * from voto;");
                while (rs.next())
                {
                	voto = obterVoto(rs);					
					colecaoVoto.add(voto);                               
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
		return colecaoVoto;
	}
	
	/**
	 * Método auxiliar que obtém do resultSet os valores dos atributos trazidos na consulta e monta uma instância de voto
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Voto obterVoto(ResultSet rs) throws Exception{
		Voto retorno = null;
		int codVoto;
		int idUsuario;
		int idEleicao;
		int idOpcaoVoto;

		Usuario usuario = null;
		Eleicao eleicao = null;
		OpcaoVoto opcaoVoto = null;

		//Capturando os valores do result set
		codVoto = rs.getInt ("ID_VOTO");
		idUsuario = rs.getInt("ID_USUARIO");
		idEleicao = rs.getInt("ID_ELEICAO");
		idOpcaoVoto = rs.getInt("ID_OPCAO_VOTO");
		double vlVoto = rs.getDouble("VALOR_VOTO");
		Timestamp dhVoto = rs.getTimestamp("DH_VOTO");
		// Instânciando os objetos auxiliares para criar um voto com eles;
		eleicao = new Eleicao(idEleicao);
		usuario = new Usuario(idUsuario);
		opcaoVoto = new OpcaoVoto(idOpcaoVoto);

		//Criando uma nova instância de Voto com os parâmetros capturados
		//armanenando a instância em retorno
		retorno = new Voto(codVoto, eleicao, usuario, opcaoVoto, vlVoto, dhVoto);

		return retorno;
	}
} 
