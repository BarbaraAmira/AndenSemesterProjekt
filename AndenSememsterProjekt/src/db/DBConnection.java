package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private Connection connection = null;
	private static DBConnection dbConnection;
	
	private static final String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String dbName = "DMA-CSD-S243_10632081";
	private static final String serverAddress = "hildur.ucn.dk";
	//private static final String serverAddress = "192.168.56.2";
	private static final int    serverPort = 1433;
	private static final String userName = "DMA-CSD-S243_10632081";
	private static final String password = "Password1!";
	
	private DBConnection() {
		String connectionString = String.format("jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s;encrypt=false", 
				serverAddress, serverPort, dbName, userName, password);
		try {
			Class.forName(driverClass);
			connection = DriverManager.getConnection(connectionString);
		} catch (ClassNotFoundException e) {
			System.err.println("Could not load JDBC driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Could not connect to database " + dbName + "@" + serverAddress + ":" + serverPort + " as user " + userName + " using password ******");
			System.out.println("Connection string was: " + connectionString.substring(0, connectionString.length() - password.length()) + "....");
			e.printStackTrace();
		}
	}
	
	public static DBConnection getInstance() {
		if(dbConnection == null) {
			dbConnection = new DBConnection();
		}
		return dbConnection;
	}
	
	//Metode der vil starte en transaktion
	public void startTransaction() throws DataAccessException {
		try {
		connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not Start transaction");
		}
	}
	
	//Metode der så laver transactionen
	public void commitTransaction() throws DataAccessException {
		try {
			try {
		connection.commit();
			} catch (SQLException e) {
				throw e;
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not commit transaction");
		}
	}
	
	//Metoden der ruller metoden tilbage hvis der skulle ske en fejl
	public void rollbackTransaction() throws DataAccessException {
		try {
			try {
		connection.rollback();
			} catch (SQLException e) {
				throw e;
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not commit transaction");
		}
	}
	
	public int executeInsertWithIdentity(PreparedStatement ps) throws SQLException  {
		int res = -1;
		try {
			res = ps.executeUpdate();
			if(res > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	public int executeInsertWithIdentity(String sql) throws SQLException  {
		System.out.println("DBConnection, Inserting: " + sql);
		int res = -1;
		try (Statement s = connection.createStatement()) {
			res = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if(res > 0) {
				ResultSet rs = s.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
			//s.close(); -- the try block does this for us now

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	public int executeUpdate(String sql) throws SQLException {
		System.out.println("DBConnection, Updating: " + sql);
		int res = -1;
		try (Statement s = connection.createStatement()){
			res = s.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	
	public Connection getConnection() {
		return connection;
	}
	
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
