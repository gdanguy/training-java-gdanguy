package service;

import controller.DashboardServlet;
import model.Page;
import model.computer.Computer;
import model.dao.DAOException;
import model.dao.DAOFactory;
import model.dao.computer.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public enum ComputerServiceImpl implements ComputerService {
    INSTANCE;
    private Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private ComputerDAO computerDAO = ComputerDAO.getInstance();

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     */
    public int count() {
        try {
            daoFactory.open();
            return computerDAO.count();
        } catch (DAOException e) {
            logger.error(e.toString());
            return ECHEC_FLAG;
        } finally {
            daoFactory.close();
        }
    }


    /**
     * Get a page of Computer.
     * @param page int
     * @return a page of Computer
     */
    public Page<Computer> list(int page) {
        return list(page, Page.PAGE_SIZE);
    }

    /**
     * Get a page of Computer with a size of sizePage.
     * @param page     int
     * @param sizePage int
     * @return a page of Computer
     */
    public Page<Computer> list(int page, int sizePage) {
        return list(page, sizePage, DashboardServlet.ORDER_NULL);
    }

    /**
     * Get a page of Computer with a size of sizePage and a order.
     * @param page     int
     * @param sizePage int
     * @param order order
     * @return a page of Computer
     */
    public Page<Computer> list(int page, int sizePage, String order) {
        logger.info("List all Computers");
        if (page < 0) {
            logger.error("Invalid Page Number");
            return null;
        }
        int p = page;
        try {
            daoFactory.open();
            Page<Computer> result = computerDAO.getPage(p, sizePage, order);
            while (result.getListPage().size() == 0) {
                result = computerDAO.getPage(--p, sizePage, order);
            }
            return result;
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        } finally {
            daoFactory.close();
        }
    }

    /**
     * Get a page of Computer with a search.
     * @param search word research
     * @return a page of Computer
     */
    public Page<Computer> list(String search) {
        logger.info("List all Computers");
        try {
            daoFactory.open();
            return computerDAO.getPage(search);
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        } finally {
            daoFactory.close();
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
            daoFactory.open();
            return computerDAO.getDetails(id);
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        } finally {
            daoFactory.close();
        }
    }

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return if computer was added
     * @throws NumberFormatException if bad parameter
     * @throws DAOException          if model bug
     */
    public int create(Computer computer) {
        logger.info("Create a computer, " + computer);
        try {
            daoFactory.open();
            Computer result = computerDAO.create(computer);
            if (result != null) {
                return result.getId();
            }
        } catch (DAOException e) {
            logger.error(e.toString());
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        } finally {
            daoFactory.close();
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
        try {
            daoFactory.open();
            return computerDAO.update(computer);
        } catch (DAOException e) {
            logger.error(e.toString());
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        } finally {
            daoFactory.close();
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
                daoFactory.open();
                return computerDAO.delete(id);
            } catch (DAOException e) {
                logger.error(e.toString());
                return null;
            } finally {
                daoFactory.close();
            }
        }
    }

    /**
     * Delete Computers in DataBase.
     * @param listId list of the Computer
     * @return String if succes, null else
     */
    public String delete(List<Integer> listId) {
        logger.info("Delete a computer, list : " + listId);
        if (listId.size() == 0) {
            logger.error("Invalid ID (no id)");
            return "Invalid ID (no id)";
        }
        for (Integer i : listId) {
            if (i == null) {
                logger.error("Invalid ID (null)");
                return "Invalid ID (null)";
            }
        }
        try {
            daoFactory.open();
            return computerDAO.delete(listId);
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        } finally {
            daoFactory.close();
        }
    }

    /**
     * Get the first computer of the DataBase.
     * @return Computer
     */
    public Computer getFirst() {
        try {
            daoFactory.open();
            return computerDAO.getFirst();
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        } finally {
            daoFactory.close();
        }
    }

    /**
     * Delete the last computer added in the DAO.
     */
    public void deleteLast() {
        try {
            daoFactory.open();
            computerDAO.deleteLast();
        } catch (DAOException e) {
            logger.error(e.toString());
        } finally {
            daoFactory.close();
        }
    }
}
