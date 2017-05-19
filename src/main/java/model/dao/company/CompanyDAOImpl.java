package model.dao.company;

import exception.CDBException;
import exception.CodeError;
import model.GenericBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import model.Page;
import model.company.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
    private Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
    private SessionFactory sessionFactory;

    /**
     * .
     * @param sessionFactory .
     */
    @Autowired
    public CompanyDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Get the number of Companies.
     * @return the number of companies in DataBase
     */
    public int count()  {
        logger.info("Count computers");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Company");
        return query.list().size();
    }

    /**
     * Get a page of companies.
     * @param page who pages is needed
     * @return Page<Company> contains companies wanted
     */
    public Page<Company> getPage(int page) {
        logger.info("Get all companies");
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("FROM Company ");
            query.setFirstResult(page * Page.PAGE_SIZE);
            query.setMaxResults(Page.PAGE_SIZE);
            List<Company> companies = query.getResultList();
            return new Page<Company>(companies, page);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPANY_NOT_FOUND);
        }
    }

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     */
    public Company get(int id) {
        logger.info("Get all companies");
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("FROM Company C WHERE C.id = :company_id");
            query.setParameter("company_id", id);
            return (Company) query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPANY_NOT_FOUND);
        }
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    public List<Company> getAll() {
        logger.info("Get all companies");
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("FROM Company ");
            List<Company> companies = query.getResultList();
            return companies;
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPANY_NOT_FOUND);
        }
    }

    /**
     * Create a company.
     * @param c company to create
     * @return the id of the company
     */
    public int create(Company c) {
        logger.info("Create computer : " + c);
        Session session = sessionFactory.getCurrentSession();
        try {
            Integer id = (Integer) session.save(GenericBuilder.of(Company::new).with(Company::setName, c.getName()).build());
            if (id == null) {
                logger.error("Error creating Company, bad parameters, " + c.toString());
                throw new CDBException(CodeError.COMPANY_CREATE);
            }
            return id.intValue();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPANY_CREATE);
        }
    }

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     */
    public boolean delete(int id) {
        logger.info("Delete a company : " + id);
        Session session = sessionFactory.getCurrentSession();
        if (id < 1) {
            throw new CDBException(CodeError.COMPANY_DELETE);
        }
        try {
            Company company = session.get(Company.class, id);
            session.delete(company);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_DELETE);
        }
        return true;
    }
}
