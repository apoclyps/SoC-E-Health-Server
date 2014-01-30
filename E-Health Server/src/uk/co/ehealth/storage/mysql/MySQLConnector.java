package uk.co.ehealth.storage.mysql;

import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySQLConnector {
	protected Connection connection = null;
	private Statement statement = null;
	//private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private DataSource datasource;
	private String database_name = "mbchb";

	public MySQLConnector() {
		System.out.println("Connection Opened");
		try {
			datasource = (DataSource) new InitialContext()
					.lookup("java:/comp/env/jdbc/" + database_name);
			connection = datasource.getConnection();
		} catch(NameNotFoundException nnfe){
			System.out.println("MySQLConnector : File not found Exception in DatabaseConnector.java");
			nnfe.printStackTrace();
		}catch (NamingException ne) {
			System.out.println("Naming Exception in DatabaseConnector.java");
			ne.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception in DatabaseConnector.java");
			e.printStackTrace();
		} 
	}

	public boolean checkConnection() {
		System.out.println("Connection Check");
		try {
			connection.close();
			connection = datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				System.out.println("Connection Closed");
				connection.close();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
