package service;

import model.Page;
import model.company.Company;
import model.dao.company.CompanyDAO;
import model.dao.computer.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.validator.Validateur;

import java.util.ArrayList;

@Service
@javax.transaction.Transactional
public class CompanyServiceImpl implements CompanyService {
    private Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private ComputerDAO computerDAO;


    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     */
    public Page<Company> list(int page) {
        logger.info("List all Companies");
        if (page < 0) {
            return null;
        }
        return companyDAO.getPage(page);
    }

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     */
    public Company get(int id) {
        if (id < 0) {
            return null;
        }
        return companyDAO.get(id);
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    public ArrayList<Company> listAll() {
        return companyDAO.getAll();
    }

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     */
    @Transactional
    public boolean delete(int id) {
        logger.debug("Deleting Company of ID : " + id);
        if (id > 0) {
            //Transaction
            computerDAO.deleteIdCompany(id);
            companyDAO.delete(id);
            return true;
        }
        return false;
    }


    /**
     * Create a company.
     * @param c company to create
     * @return the id of the company
     */
    public int create(Company c) {
        if (c != null && Validateur.validCompanyStrict(c) == null) {
            return companyDAO.create(c);
        }
        return ECHEC_FLAG;
    }

    /**
     * Delete the last company.
     * @return true if succes, false else.
     */
    public boolean deleteLast() {
        ArrayList<Company> list = listAll();
        int id = list.get(list.size() - 1).getId();
        return delete(id);
    }


}
