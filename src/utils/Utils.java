package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.DAO.DAO;
import model.DAO.computer.ComputerDAOImpl;

public abstract class Utils {
	private static Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	public static  Connection openConnection() throws SQLException {
		try{
			logger.info("Connection to the database");
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(DAO.URL_DB, DAO.USER_DB, DAO.PASSWORD_DB);
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
