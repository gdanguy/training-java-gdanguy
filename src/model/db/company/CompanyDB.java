package model.db.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import configuration.Config;
import model.company.Company;

public class CompanyDB implements CompanyDBInterface{
	private static Logger logger = LoggerFactory.getLogger(CompanyDB.class);
	private Connection conn;

	/**
	 * Builder
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CompanyDB() throws ClassNotFoundException, SQLException {
		if( Config.LOGGER_MESSAGE )
			logger.info("Connection to the database");
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(Config.URL_DB, Config.USER_DB, Config.PASSWORD_DB);

	}
	
	/**
	 * Close connection
	 */
	protected void finalize() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
		}
	}

	/**
	 * This method returns the list of companies.
	 * @return
	 * @throws SQLException 
	 */
	public ArrayList<Company> getCompanies() throws SQLException {
		if( Config.LOGGER_MESSAGE )
			logger.info("Get all companies");
		PreparedStatement s = conn.prepareStatement("SELECT id, name FROM company");
		ResultSet r = s.executeQuery();
		ArrayList<Company> result = new ArrayList<>();
		while(r.next()){
			result.add(new Company(r.getInt(1),r.getString(2)));
		}
		return result;
		
	}
	
}
