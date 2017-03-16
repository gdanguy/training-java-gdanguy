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
import model.Pages;
import model.company.Company;

public class CompanyDAOImpl implements CompanyDAO{
	private static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	private Connection conn;

	/**
	 * Builder
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CompanyDAOImpl() throws ClassNotFoundException, SQLException {
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
	 * This method returns the first page of companies.
	 * @return
	 * @throws SQLException 
	 */
	public Pages<Company> getCompanies() throws SQLException {
		return getPageCompanies(0);
	}
	

	public Pages<Company> getPageCompanies(int page) throws SQLException {
		logger.info("Get all companies");
		PreparedStatement s = conn.prepareStatement("SELECT id, name FROM company LIMIT "+Pages.PAGE_SIZE+" OFFSET "+page*Pages.PAGE_SIZE);
		ResultSet r = s.executeQuery();
		ArrayList<Company> result = new ArrayList<>();
		while( r.next() ){
			result.add(new Company(r.getInt(1),r.getString(2)));
		}
		return new Pages<Company>(result,page);
		
	}
	
}
