package fbv.com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Hashtable;
import java.util.ResourceBundle;

import fbv.com.excecoes.ExcecaoAcessoRepositorio;


public class GerenciadorConexaoBDR implements IBancoDeDados {

	//Atributos da Conexao do BD carregados de dbconf.resx
	private static final String NOME_DRIVER = "com.mysql.jdbc.Driver";
	private static final String HOST_PADRAO = "localhost"; 
	private static final String BD_NAME_PADRAO = "svp"; 
	private static final String USUARIO_CONEXAO = "root"; 
	private static final String SENHA_CONEXAO = "root"; 
	private static final String URL_CONEXAO = 
		"jdbc:mysql://" + HOST_PADRAO + "/" + BD_NAME_PADRAO + "?user=" + USUARIO_CONEXAO + "&password=" + SENHA_CONEXAO;
	private static final String IND_CONECTA_COM_USUARIO = "IND_CONECTA_COM_USUARIO";
	private static final boolean IND_CONECTA_COM_USUARIO_DEF = true;
	private static final String NOME_PROP = "ConexaoJDBC";
	private static GerenciadorConexaoBDR myInstance;
	
	// Variáveis
	private String nomeDriver;
	private String urlConexao;
	private String usuarioConexao;
	private String senhaConexao;
	private Hashtable<String, Connection> threadsConexoes;
	private Connector myConnector;
	
	private abstract class Connector {
		public abstract Connection getConnection() throws ExcecaoAcessoRepositorio;
	}
	private class ConnectorSemUsuario extends Connector {
		public Connection getConnection() throws ExcecaoAcessoRepositorio {
			try {
				return DriverManager.getConnection(urlConexao);
			} catch (Exception e) {
				throw new ExcecaoAcessoRepositorio("Erro ao obter conexao JDBC",e);
			}
		}
	}
	private class ConnectorComUsuario extends Connector {
		public Connection getConnection() throws ExcecaoAcessoRepositorio {
			try {
				return DriverManager.getConnection(urlConexao,usuarioConexao,senhaConexao);
			} catch (Exception e) {
				throw new ExcecaoAcessoRepositorio("Erro ao obter conexao JDBC",e);
			}
		}
	}

	private GerenciadorConexaoBDR() {
		threadsConexoes = new Hashtable<String, Connection>();
		carregaParametros();
		try {
			Class.forName(nomeDriver).newInstance ();
		} catch (Exception e) {
			System.out.println("ERRO NA CARGA DO DRIVER "+nomeDriver);
			e.printStackTrace();
		}
	}
	public synchronized static final GerenciadorConexaoBDR getMyInstance() {
		if (myInstance == null) {
			myInstance = new GerenciadorConexaoBDR();
		}
		return myInstance;
	}
	private void carregaParametros() {
		boolean indConectaComUsuario = false;
		try {
			ResourceBundle rb = ResourceBundle.getBundle(NOME_PROP);
			nomeDriver = rb.getString(NOME_DRIVER).trim();
			urlConexao = rb.getString(URL_CONEXAO).trim();
			indConectaComUsuario = rb.getString(IND_CONECTA_COM_USUARIO).trim().equalsIgnoreCase("S");
			if (indConectaComUsuario) {
				usuarioConexao = rb.getString(USUARIO_CONEXAO);
				senhaConexao = rb.getString(SENHA_CONEXAO);
				myConnector = new ConnectorComUsuario();
			} else {
				myConnector = new ConnectorSemUsuario();
			}
		} catch (Throwable e) {
			nomeDriver = NOME_DRIVER;
			urlConexao = URL_CONEXAO;
			indConectaComUsuario = IND_CONECTA_COM_USUARIO_DEF;
			if (indConectaComUsuario) {
				usuarioConexao = USUARIO_CONEXAO;
				senhaConexao = SENHA_CONEXAO;
				myConnector = new ConnectorComUsuario();
			} else {
				myConnector = new ConnectorSemUsuario();
			}
		}
	}
	private Connection criaConexao() throws ExcecaoAcessoRepositorio {
		return myConnector.getConnection();
	}

	public Connection getConexao(boolean emTransacao) throws ExcecaoAcessoRepositorio {
		if (emTransacao) {
			Connection con = threadsConexoes.get(Thread.currentThread().getName());
			if (con == null) {
				throw new ExcecaoAcessoRepositorio("Transacao nao foi aberta");
			} else {
				return con;
			}
		} else {
			return criaConexao();
		}
	}
	public void beginTransaction() throws ExcecaoAcessoRepositorio {
		String nomeThread = Thread.currentThread().getName();
		Connection con = (Connection)threadsConexoes.get(nomeThread);
		if (con != null) {
			throw new ExcecaoAcessoRepositorio("Transacao ja foi aberta");
		} else {
			con = criaConexao();
			try {
				con.setAutoCommit(false);
			} catch (Exception e) {
				throw new ExcecaoAcessoRepositorio("Erro ao abrir transacao na conexao JDBC",e);
			}
			threadsConexoes.put(nomeThread,con);
		}
	}
	public void commitTransaction() throws ExcecaoAcessoRepositorio {
		String nomeThread = Thread.currentThread().getName();
		Connection con = (Connection)threadsConexoes.get(nomeThread);
		if (con == null) {
			throw new ExcecaoAcessoRepositorio("Transacao nao foi aberta");
		} else {
			try {
				con.commit();
			} catch (Exception e) {
				throw new ExcecaoAcessoRepositorio("Erro ao confirmar transacao na conexao JDBC",e);
			} finally {
				try {
					con.close();
				} catch (Exception e) {}
			}
			threadsConexoes.remove(nomeThread);
		}
	}
	public void rollbackTransaction() {
		String nomeThread = Thread.currentThread().getName();
		Connection con = (Connection)threadsConexoes.get(nomeThread);
		if (con != null) {
			try {
				con.rollback();
			} catch (Exception e) {
				System.out.println("Erro ao desfazer transacao na conexao JDBC");
			} finally {
				try {
					con.close();
				} catch (Exception e) {}
			}
			threadsConexoes.remove(nomeThread);
		} else {
			System.out.println("Transacao nao foi aberta");
		}
	}
}

