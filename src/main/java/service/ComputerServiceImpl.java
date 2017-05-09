package service;

import controller.DashboardController;
import model.Page;
import model.computer.Computer;
import model.dao.DAOException;
import model.dao.computer.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.validator.Validator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {
    private Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
    @Autowired
    private ComputerDAO computerDAO;

    public static final String INVALID_ID = "Invalid ID";

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     */
    public int count() {
        try {
            return computerDAO.count();
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
        return list(page, sizePage, DashboardController.ORDER_NULL);
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
            Page<Computer> result = computerDAO.getPage(p, sizePage, order);
            while (result.getListPage().size() == 0) {
                result = computerDAO.getPage(--p, sizePage, order);
            }
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
    public Page<Computer> list(String search) {
        logger.info("List all Computers");
        try {
            return computerDAO.getPage(search);
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
            return computerDAO.getDetails(id);
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return if computer was added
     */
    public int create(Computer computer) {
        logger.info("Create a computer, " + computer);
        try {
            Computer result;
            if (computer != null && Validator.validComputer(computer) == null) {
                result = computerDAO.create(computer);
                if (result != null) {
                    return result.getId();
                }
            }
        } catch (DAOException | NumberFormatException e) {
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
        if (computer != null && Validator.validComputer(computer) == null) {
            try {
                return computerDAO.update(computer);
            } catch (DAOException | NumberFormatException e) {
                logger.error(e.toString());
            }
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
        if (id <= 0) {
            logger.error(INVALID_ID);
            return INVALID_ID;
        } else {
            try {
                return computerDAO.delete(id);
            } catch (DAOException e) {
                logger.error(e.toString());
                return INVALID_ID;
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
        if (listId == null || listId.size() == 0) {
            logger.error("Invalid ID (no list id)");
            return "Invalid ID (no list id)";
        }
        for (Integer i : listId) {
            if (i == null) {
                logger.error("Invalid ID (null)");
                return "Invalid ID (null)";
            }
        }
        try {
            return computerDAO.delete(listId);
        } catch (DAOException e) {
            logger.error(e.toString());
            return "Delete Fail";
        }
    }

    /**
     * Get the first computer of the DataBase.
     * @return Computer
     */
    public Computer getFirst() {
        try {
            return computerDAO.getFirst();
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
            computerDAO.deleteLast();
        } catch (DAOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Delete the last computer in the DAO.
     */
    public void deleteMultiLast() {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Computer> listC = list("").getListPage();
        int i = listC.size();
        list.add(listC.get(i - 1).getId());
        list.add(listC.get(i - 2).getId());
        delete(list);
    }


}
