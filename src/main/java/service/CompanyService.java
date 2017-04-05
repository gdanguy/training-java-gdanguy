package service;

import model.Page;
import model.company.Company;

import java.util.ArrayList;

public interface CompanyService {
    int ECHEC_FLAG = -1;


    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     */
    Page<Company> list(int page);

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
    ArrayList<Company> listAll();

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     */
    boolean delete(int id);


    /**
     * Create a company.
     * @param c company to create
     * @return the id of the company
     */
    int create(Company c);

    /**
     * Return the Instance of CompanyServiceImpl.
     * @return the Instance of CompanyServiceImpl
     */
    static CompanyServiceImpl getInstance() {
        return CompanyServiceImpl.INSTANCE;
    }

    /**
     * Delete the last company.
     * @return true if succes, false else.
     */
    boolean deleteLast();
}
