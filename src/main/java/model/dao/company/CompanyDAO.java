package model.dao.company;

import model.Page;
import model.company.Company;
import model.dao.DAOException;

import java.util.ArrayList;

public interface CompanyDAO {

    /**
     * Get a page of companies.
     * @param page who pages is needed
     * @return Page<Company> contains companies wanted
     * @throws DAOException if SQL fails
     */
    Page<Company> getPage(int page) throws DAOException;

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
     * Create a company.
     * @param c company to create
     * @return the id of the company
     * @throws DAOException if fail
     */
    int create(Company c) throws DAOException;

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     * @throws DAOException if fail
     */
    boolean delete(int id) throws DAOException;

}
