package model.db.company;

import java.sql.SQLException;

import model.Pages;
import model.company.Company;

public interface CompanyDBInterface {
	
	public Pages<Company> getCompanies() throws SQLException;
	public Pages<Company> getPageCompanies(int page) throws SQLException;
}
