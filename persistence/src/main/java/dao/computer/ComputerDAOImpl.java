package dao.computer;

import core.exception.CDBException;
import core.exception.CodeError;
import core.model.Computer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import core.utils.Constant;
import core.utils.Page;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ComputerDAOImpl implements ComputerDAO {
    private Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
    private SessionFactory sessionFactory;

    /**
     * .
     * @param sessionFactory .
     */
    @Autowired
    public ComputerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Get the number of model.Computer.
     * @return the number of computer in DataBase
     * @throws CDBException if no result
     */
    public int count() throws CDBException {
        logger.info("Count computers");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Computer C");
        return query.list().size();
    }

    /**
     * This method returns the page of computers.
     * @param page corresponds to the page's number to be retrieved
     * @return Page<model.Computer> corresponds to the page
     */
    public Page<Computer> getPage(int page) {
        return getPage(page, Page.PAGE_SIZE, Constant.ORDER_NULL);
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param page     corresponds to the page's number to be retrieved
     * @param sizePage size of a page
     * @param order    order of a page
     * @return Page<model.Computer> corresponds to the page
     */
    public Page<Computer> getPage(int page, int sizePage, String order) {
        logger.info("Get page " + page + ", computers of " + sizePage);
        String orderBy = getOrder(order);
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery(" FROM Computer C "
                    + orderBy);
            query.setFirstResult(page * sizePage);
            query.setMaxResults(sizePage);
            List<Computer> computers = query.getResultList();
            return new Page<Computer>(computers, page, sizePage);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_NOT_FOUND);
        }
    }

    /**
     * Get the ORDER BY of the request.
     * @param order of the page
     * @return String
     */
    private String getOrder(String order) {
        String result = " ORDER BY ";
        switch (order) {
            case Constant.ORDER_NAME_ASC:
                return (result + " name ASC");
            case Constant.ORDER_NAME_DESC:
                return (result + " name DESC");
            case Constant.ORDER_INTRO_ASC:
                return (result + " introduced ASC");
            case Constant.ORDER_INTRO_DESC:
                return (result + " introduced DESC");
            case Constant.ORDER_DISCO_ASC:
                return (result + " discontinued ASC");
            case Constant.ORDER_DISCO_DESC:
                return (result + " discontinued DESC");
            case Constant.ORDER_COMPANY_ASC:
                return (result + " company_name ASC");
            case Constant.ORDER_COMPANY_DESC:
                return (result + " company_name DESC");
            default:
                return "";
        }
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param search word researched
     * @return Page<model.Computer> corresponds to the page
     */
    public Page<Computer> getPage(String search) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("select C From Computer C "
                    + "left outer join C.company as Cpn "
                    + "WHERE C.name like '%" + search +"%' "
                    + "OR  Cpn.name like '%" + search +"%' "
            );
            List<Computer> computers = query.getResultList();
            return new Page<Computer>(computers, 0);
        } catch (HibernateException e) {
            throw new CDBException(CodeError.COMPUTER_NOT_FOUND);
        }
    }

    /**
     * This method returns a computer identified by its id.
     * @param id of the computer
     * @return ths model.Computer wanted
     */
    public Computer getDetails(int id) {
        logger.info("Get computer : " + id);
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery(" FROM Computer C WHERE C.id = :id");
            query.setParameter("id", id);
            return (Computer) query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_NOT_FOUND);
        }
    }

    /**
     * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
     * @param computer to add in the database
     * @return model.Computer generated
     */
    public Computer create(Computer computer) {
        logger.info("Create computer : " + computer);
        if (computer == null) {
            logger.error("Error creating model.Computer, computer == null");
            throw new CDBException(CodeError.COMPUTER_IS_NULL);
        }
        Session session = sessionFactory.getCurrentSession();
        Integer id;
        try {
            id = (Integer) session.save(computer);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_CREATE_BAD_PARAMETERS);
        }
        return computer;
    }

    /**
     * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
     * @param modifiedComputer with the id of the one to be modified and with its new attributes.
     * @return if model.Computer is updated
     */
    public boolean update(Computer modifiedComputer) {
        logger.info("Update a computer : " + modifiedComputer);
        Session session = sessionFactory.getCurrentSession();
        try {
            Computer computer = session.get(Computer.class, modifiedComputer.getId());
            computer.setName(modifiedComputer.getName());
            computer.setIntroduced(modifiedComputer.getIntroduced());
            computer.setDiscontinued(modifiedComputer.getDiscontinued());
            computer.setCompany(modifiedComputer.getCompany());
            session.update(computer);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_EDIT);
        }
    }

    /**
     * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param id of the computer to delete
     * @return if succes : "model.Computer " + id + " is deleted" or if fail : "Delete model.Computer failed, no rows affected."
     */
    public String delete(int id) {
        logger.info("Delete a computer : " + id);
        Session session = sessionFactory.getCurrentSession();
        try {
            Computer computer = session.get(Computer.class, id);
            session.delete(computer);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_DELETE);
        }
        return "model.Computer " + id + " is deleted";
    }

    /**
     * This method removes computers corresponding to the passed listID as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param listId list of the computer to delete
     */
    public void delete(List<Integer> listId) {
        logger.info("Delete computers : " + listId);
        Session session = sessionFactory.getCurrentSession();
        try {
            for (Integer i : listId) {
                Computer computer = session.get(Computer.class, i);
                session.delete(computer);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_DELETE);
        }
    }

    /**
     * Delete the last computer added in the DAO.
     */
    public void deleteLast() {
        delete(getLastId());
    }


    /**
     * Delete all computer of one company.
     * @param id the id of the company
     */
    public void deleteIdCompany(int id) {
        logger.info("Delete computer of the company : " + id);
        if (id <= 0) {
            throw new CDBException(CodeError.COMPUTER_COMPANY_ID_INVALID);
        }
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("DELETE FROM Computer C where C.company.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_DELETE);
        }
    }

    /**
     * Get the first computer of the DataBase.
     * @return model.Computer
     */
    public Computer getFirst() {
        int id = getFirstId();
        return getDetails(id);
    }

    /**
     * getLastId.
     * @return int
     */
    private int getLastId() {
        return getFirstORLastId(true);
    }

    /**
     * getFirstId.
     * @return int
     */
    private int getFirstId() {
        return getFirstORLastId(false);
    }

    /**
     * getFirstORLastId.
     * @param last true if you want the last id, false if you want the first
     * @return int
     */
    private int getFirstORLastId(boolean last) {
        String ordre = "";
        if (last) {
            ordre = " DESC ";
        }
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("SELECT id FROM Computer ORDER BY id " + ordre);
            query.setMaxResults(1);
            Integer id = (Integer) query.getSingleResult();
            if (id == null) {
                throw new CDBException(CodeError.COMPUTER_NOT_FOUND);
            }
            return id;
        } catch (CDBException e) {
            e.printStackTrace();
            throw new CDBException(CodeError.COMPUTER_NOT_FOUND);
        }

    }
}
