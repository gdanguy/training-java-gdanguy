package model.dao.company;

import model.Page;
import model.company.Company;

import java.util.ArrayList;

public interface CompanyDAO {

    /**
     * Get a page of companies.
     * @param page who pages is needed
     * @return Page<Company> contains companies wanted
     */
    Page<Company> getPage(int page);

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     */
    Company get(int id);

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    ArrayList<Company> getAll();

    /**
     * Get the number of Companies.
     * @return the number of companies in DataBase
     */
    int count();

    /**
     * Create a company.
     * @param c company to create
     * @return the id of the company
     */
    int create(Company c);

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     */
    boolean delete(int id);

}
