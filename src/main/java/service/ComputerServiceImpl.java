package service;

import model.Pages;
import model.computer.Computer;
import model.dao.DAOException;
import model.dao.computer.ComputerDAO;
import model.dao.computer.ComputerDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum ComputerServiceImpl implements ComputerService {
    INSTANCE;
    private Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     */
    public int count() {
        try {
            ComputerDAOImpl db = ComputerDAO.getInstance();
            return db.count();
        } catch (DAOException e) {
            logger.error(e.toString());
            return ECHEC_FLAG;
        }
    }


    /**
     * Get a page of Computer.
     * @param page int
     * @return a page of Computer
     */
    public Pages<Computer> list(int page) {
        return list(page, Pages.PAGE_SIZE);
    }

    /**
     * Get a page of Computer with a size of sizePage.
     * @param page int
     * @param sizePage int
     * @return a page of Computer
     */
    public Pages<Computer> list(int page, int sizePage) {
        logger.info("List all Computers");
        try {
            ComputerDAOImpl db = ComputerDAO.getInstance();
            Pages<Computer> result = db.getPage(page, sizePage);
            return result;
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Get a page of Computer with a search.
     * @param search word research
     * @return a page of Computer
     */
    public Pages<Computer> list(String search) {
        logger.info("List all Computers");
        try {
            ComputerDAOImpl db = ComputerDAO.getInstance();
            Pages<Computer> result = db.getPage(search);
            return result;
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Get details of one Computer.
     * @param id of the Computer
     * @return String
     */
    public String showdetails(int id) {
        logger.info("Get a computers, id =" + id);
        Computer result = get(id);
        if (result == null) {
            return "No computer corresponding in the database";
        } else {
            return result.toStringDetails();
        }
    }

    /**
     * Get one Computer.
     * @param id of the Computer
     * @return Computer
     */
    public Computer get(int id) {
        try {
            ComputerDAOImpl db = ComputerDAO.getInstance();
            return db.getDetails(id);
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return if computer was added
     * @throws NumberFormatException if bad parameter
     * @throws DAOException if model bug
     */
    public int create(Computer computer) {
        logger.info("Create a computer, " + computer);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        try {
            Computer result = db.create(computer);
            if (result != null) {
                return result.getId();
            }
        } catch (DAOException e) {
            logger.error(e.toString());
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return ECHEC_FLAG;
    }

    /**
     * Update a Computer in DataBase.
     * @param computer to insert
     * @return if computer was uptaded
     */
    public boolean update(Computer computer) {
        logger.info("Update a Computer, new : " + computer);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        try {
            Computer computerUpdated = db.update(computer);
            if (computerUpdated != null) {
               return true;
            }
        } catch (DAOException e) {
            logger.error(e.toString());
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return false;
    }

    /**
     * Delete a Computer in DataBase.
     * @param id of the Computer
     * @return "Delete a computer, id = " + id
     */
    public String delete(int id) {
        logger.info("Delete a computer, id = " + id);
        if (id < 0) {
            logger.error("Invalid ID");
            return "Invalid ID";
        } else {
            try {
                ComputerDAOImpl db = ComputerDAO.getInstance();
                return db.delete(id);
            } catch (DAOException e) {
                logger.error(e.toString());
                return null;
            }
        }
    }

    /**
     * Get the first computer of the DataBase.
     * @return Computer
     */
    public Computer getFirst() {
        try {
            ComputerDAO db = ComputerDAO.getInstance();
            return db.getFirst();
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Delete the last computer added in the DAO.
     */
    public void deleteLast() {
        try {
            ComputerDAO dao = ComputerDAO.getInstance();
            dao.deleteLast();
        } catch (DAOException e) {
            logger.error(e.toString());
        }
    }
}
