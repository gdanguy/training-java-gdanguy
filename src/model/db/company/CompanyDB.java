package model.db.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import configuration.Config;
import model.company.Company;

public class CompanyDB {

	private Connection conn;

	public CompanyDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(Config.URL_DB, Config.USER_DB, Config.PASSWORD_DB);

	}

	protected void finalize() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
		}
	}

	public ArrayList<Company> getCompanies() {
		try{
			PreparedStatement s = conn.prepareStatement("SELECT id, name FROM company");
			ResultSet r = s.executeQuery();
			ArrayList<Company> result = new ArrayList<>();
			while(r.next()){
				result.add(new Company(r.getInt(1),r.getString(2)));
			}
			return result;
		}catch(SQLException e){
			return null;
		}
		
	}
	
}
