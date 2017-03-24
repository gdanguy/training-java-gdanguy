package service;

import java.util.ArrayList;

import model.Pages;
import model.company.Company;

public interface CompanyService {
    int ECHEC_FLAG = -1;


    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     */
    Pages<Company> list(int page);

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
     * Return the Instance of CompanyServiceImpl.
     * @return the Instance of CompanyServiceImpl
     */
    static CompanyServiceImpl getInstance() {
        return CompanyServiceImpl.INSTANCE;
    }

}
