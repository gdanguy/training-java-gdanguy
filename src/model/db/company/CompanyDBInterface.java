package model.db.company;

import java.sql.SQLException;
import java.util.ArrayList;

import model.company.Company;

public interface CompanyDBInterface {
	public ArrayList<Company> getCompanies() throws SQLException;
}
