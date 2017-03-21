package model.dao.company;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Pages;
import model.company.Company;

public interface CompanyDAO {

    /**
     * Get a page of companies.
     * @param page who pages is needed
     * @return Pages<Company> contains companies wanted
     * @throws SQLException if SQL fails
     */
    Pages<Company> getPageCompanies(int page) throws SQLException;

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     * @throws SQLException if SQL fails
     */
    Company getCompany(int id) throws SQLException;

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     * @throws SQLException if SQL fail
     */
    ArrayList<Company> getAllCompany() throws SQLException;

    /**
     * Return Instance of DAO.
     * @return a DAO Instance
     */
    static CompanyDAOImpl getInstance() {
        return CompanyDAOImpl.INSTANCE;
    }
}
