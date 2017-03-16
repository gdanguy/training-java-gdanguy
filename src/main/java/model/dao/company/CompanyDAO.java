package model.dao.company;

import java.sql.SQLException;

import model.Pages;
import model.company.Company;

public interface CompanyDAO {
	
	public Pages<Company> getCompanies() throws SQLException;
	public Pages<Company> getPageCompanies(int page) throws SQLException;
	
	public static CompanyDAOImpl getInstance() {
		return CompanyDAOImpl.INSTANCE;
	}
}
