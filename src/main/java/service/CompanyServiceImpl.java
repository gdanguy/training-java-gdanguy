package service;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import model.Page;
import model.company.Company;
import model.dao.company.CompanyDAO;
import model.dao.company.CompanyDAOImpl;
import model.dao.DAOException;

public enum CompanyServiceImpl implements CompanyService {
    INSTANCE;
    private Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     */
    public Page<Company> list(int page) {
        logger.info("List all Companies");
        try {
            CompanyDAOImpl db = CompanyDAO.getInstance();
            Page<Company> result = db.getPage(page);
            return result;
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
            CompanyDAOImpl db = CompanyDAO.getInstance();
            return db.get(id);
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
            CompanyDAO db = CompanyDAO.getInstance();
            return db.getAll();
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     */
    public boolean delete(int id) {
        try {
            CompanyDAO dao = CompanyDAO.getInstance();
            dao.delete(id);
            return true;
        } catch (DAOException e) {
            logger.error(e.toString());
            return false;
        }
    }


    /**
     * Create a company.
     * @param c company to create
     * @return the id of the company
     */
    public int create(Company c) {
        try {
            CompanyDAO dao = CompanyDAO.getInstance();
            return dao.create(c);
        } catch (DAOException e) {
            logger.error(e.toString());
            return ECHEC_FLAG;
        }
    }
}
