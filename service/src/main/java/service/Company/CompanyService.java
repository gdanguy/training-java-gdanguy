package service.Company;


import core.model.Company;
import core.utils.Page;

import java.util.List;

public interface CompanyService {
    int ECHEC_FLAG = -1;


    /**
     * Get a page of model.Company.
     * @param page int
     * @return a page of model.Company
     */
    Page<Company> list(int page);

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return model.Company wanted
     */
    Company get(int id);

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    List<Company> listAll();

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
     * Delete the last company.
     * @return true if succes, false else.
     */
    boolean deleteLast();
}
