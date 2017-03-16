package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import model.dao.computer.ComputerDAOImpl;

public abstract class Utils {
	public static final String URL_DB = "jdbc:mysql://localhost/computer-database-db?useSSL=false";
	public static final String USER_DB = "admincdb";
	public static final String PASSWORD_DB = "qwerty1234";
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	public static  Connection openConnection() throws SQLException {
		try{
			logger.info("Connection to the database");
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
		}catch(ClassNotFoundException e){
			logger.error("Error Connection : " +e);
			return null;
		}
	}
	
	public static void closeConnection(Connection c) throws SQLException {
		logger.info("Disconnection to the database");
		c.close();
	}
}
