package model.dao.company;

import java.util.ArrayList;

import model.Pages;
import model.company.Company;
import model.dao.DAOException;

public interface CompanyDAO {

    /**
     * Get a page of companies.
     * @param page who pages is needed
     * @return Pages<Company> contains companies wanted
     * @throws DAOException if SQL fails
     */
    Pages<Company> getPage(int page) throws DAOException;

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     * @throws DAOException if SQL fails
     */
    Company get(int id) throws DAOException;

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     * @throws DAOException if SQL fail
     */
    ArrayList<Company> getAll() throws DAOException;

    /**
     * Get the number of Companies.
     * @return the number of companies in DataBase
     * @throws DAOException if no result
     */
    int count() throws DAOException;

    /**
     * Return Instance of DAO.
     * @return a DAO Instance
     */
    static CompanyDAOImpl getInstance() {
        return CompanyDAOImpl.INSTANCE;
    }
}
