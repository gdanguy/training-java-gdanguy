package service;

import model.Page;
import model.company.Company;
import model.dao.DAOException;
import model.dao.company.CompanyDAO;
import model.dao.computer.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.validator.Validator;

import java.util.ArrayList;

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
        try {
            return companyDAO.getPage(page);
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
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
        try {
            return companyDAO.get(id);
        } catch (DAOException e) {
            return null;
        }
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    public ArrayList<Company> listAll() {
        try {
            return companyDAO.getAll();
        } catch (DAOException e) {
            logger.error(e.toString());
        }
        return null;
    }

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean delete(int id) {
        if (id > 0) {
            try {
                //Transaction
                computerDAO.deleteIdCompany(id);
                companyDAO.delete(id);
                return true;
            } catch (DAOException e) {
                logger.error(e.toString());
            }
        }
        return false;
    }


    /**
     * Create a company.
     * @param c company to create
     * @return the id of the company
     */
    public int create(Company c) {
        if (c != null && Validator.validCompanyStrict(c) == null) {
            try {
                return companyDAO.create(c);
            } catch (DAOException e) {
                logger.error(e.toString());
            }
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
