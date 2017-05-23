package service.Company;

import core.exception.CDBException;
import core.exception.CodeError;
import dao.company.CompanyDAO;
import dao.computer.ComputerDAO;
import core.model.Company;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.utils.Page;
import core.validator.Validateur;

import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private final CompanyDAO companyDAO;
    private final ComputerDAO computerDAO;
    private final SessionFactory sessionFactory;

    /**
     * .
     * @param companyDAO .
     * @param computerDAO .
     * @param sessionFactory .
     */
    @Autowired
    public CompanyServiceImpl(CompanyDAO companyDAO, ComputerDAO computerDAO, SessionFactory sessionFactory) {
        this.companyDAO = companyDAO;
        this.computerDAO = computerDAO;
        this.sessionFactory = sessionFactory;
    }


    /**
     * Get a page of model.Company.
     * @param page int
     * @return a page of model.Company
     */
    public Page<Company> list(int page) {
        logger.info("List all Companies");
        if (page < 0) {
            throw new CDBException(CodeError.COMPANY_NOT_FOUND);
        }
        Page result = companyDAO.getPage(page);
        return result;
    }

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return model.Company wanted
     */
    public Company get(int id) {
        if (id == CompanyService.ECHEC_FLAG) {
            return null;
        } else if (id < 0) {
            throw new CDBException(CodeError.COMPANY_NOT_FOUND);
        }
        return companyDAO.get(id);
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    public List<Company> listAll() {
        return companyDAO.getAll();
    }

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     */
    @Transactional
    public boolean delete(int id) {
        logger.debug("Deleting model.Company of ID : " + id);
        if (id <= 0) {
            throw new CDBException(CodeError.COMPUTER_COMPANY_ID_INVALID);
        }
        //Transaction
        Transaction tx = null;
        try {
            tx = sessionFactory.getCurrentSession().beginTransaction();
            computerDAO.deleteIdCompany(id);
            companyDAO.delete(id);
            tx.commit();
        } catch (CDBException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        return true;
    }


    /**
     * Create a company.
     * @param c company to create
     * @return the id of the company
     */
    public int create(Company c) {
        if (c != null && Validateur.validCompanyStrict(c) == null) {
            int result = companyDAO.create(c);
            return result;
        }
        throw new CDBException(CodeError.COMPANY_CREATE);
    }

    /**
     * Delete the last company.
     * @return true if succes, false else.
     */
    public boolean deleteLast() {
        List<Company> list = listAll();
        int id = list.get(list.size() - 1).getId();
        return delete(id);
    }


}
