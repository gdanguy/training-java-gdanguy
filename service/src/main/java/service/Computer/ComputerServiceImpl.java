package service.Computer;

import core.exception.CDBException;
import core.exception.CodeError;
import core.model.Computer;
import core.utils.Constant;
import core.utils.Page;
import core.validator.Validateur;
import dao.computer.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {
    private Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
    private final ComputerDAO computerDAO;

    public static final String INVALID_ID = "Invalid ID";

    /**
     * .
     * @param computerDAO .
     */
    @Autowired
    public ComputerServiceImpl(ComputerDAO computerDAO) {
        this.computerDAO = computerDAO;
    }

    /**
     * Get the number of model.Computer.
     * @return the number of computer in DataBase
     */
    public int count() {
        return computerDAO.count();
    }


    /**
     * Get a page of model.Computer.
     * @param page int
     * @return a page of model.Computer
     */
    public Page<Computer> list(int page) {
        return list(page, Page.PAGE_SIZE);
    }

    /**
     * Get a page of model.Computer with a size of sizePage.
     * @param page     int
     * @param sizePage int
     * @return a page of model.Computer
     */
    public Page<Computer> list(int page, int sizePage) {
        return list(page, sizePage, Constant.ORDER_NULL);
    }

    /**
     * Get a page of model.Computer with a size of sizePage and a order.
     * @param page     int
     * @param sizePage int
     * @param order order
     * @return a page of model.Computer
     */
    public Page<Computer> list(int page, int sizePage, String order) {
        logger.info("List all Computers");
        if (page < 0) {
            logger.error("Invalid Page Number : " + page);
            throw new CDBException(CodeError.INVALID_PAGE);
        }
        int p = page;
        Page<Computer> result = computerDAO.getPage(p, sizePage, order);
        while (result.getListPage().size() == 0) {
            result = computerDAO.getPage(--p, sizePage, order);
        }
        return result;
    }

    /**
     * Get a page of model.Computer with a search.
     * @param search word research
     * @return a page of model.Computer
     */
    public Page<Computer> list(String search) {
        logger.info("List all Computers");
        Page result = computerDAO.getPage(search);
        return result;
    }

    /**
     * Get details of one model.Computer.
     * @param id of the model.Computer
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
     * Get one model.Computer.
     * @param id of the model.Computer
     * @return model.Computer
     */
    public Computer get(int id) {
        Computer result = computerDAO.getDetails(id);
        return result;
    }

    /**
     * Create a model.Computer in DataBase.
     * @param computer to insert
     * @return if computer was added
     */
    public int create(Computer computer) {
        logger.info("Create a computer, " + computer);
        Computer result;
        if (computer != null && Validateur.validComputer(computer) == null) {
            result = computerDAO.create(computer);
            if (result != null) {
                return result.getId();
            }
        }
        throw new CDBException(CodeError.COMPUTER_CREATE_BAD_PARAMETERS);
    }

    /**
     * Update a model.Computer in DataBase.
     * @param computer to insert
     * @return if computer was uptaded
     */
    public boolean update(Computer computer) {
        logger.info("Update a model.Computer, new : " + computer);
        if (computer != null && Validateur.validComputer(computer) == null) {
            return computerDAO.update(computer);
        }
        throw new CDBException(CodeError.COMPUTER_EDIT);
    }

    /**
     * Delete a model.Computer in DataBase.
     * @param id of the model.Computer
     * @return "Delete a computer, id = " + id
     */
    public String delete(int id) {
        logger.info("Delete a computer, id = " + id);
        if (id <= 0) {
            logger.error(INVALID_ID);
            throw new CDBException(CodeError.COMPUTER_ID_INVALID);
        } else {
            String result = computerDAO.delete(id);
            return result;
        }
    }

    /**
     * Delete Computers in DataBase.
     * @param listId list of the model.Computer
     */
    public void delete(List<Integer> listId) {
        logger.info("Delete a computer, list : " + listId);
        if (listId == null || listId.size() == 0) {
            logger.error("Invalid ID (no list id)");
            throw new CDBException(CodeError.COMPUTER_DELETE_LIST_EMPTY);
        }
        for (Integer i : listId) {
            if (i == null) {
                logger.error("Invalid ID (null)");
                throw new CDBException(CodeError.COMPUTER_DELETE_NULL_ID);
            }
        }
        computerDAO.delete(listId);
    }

    /**
     * Get the first computer of the DataBase.
     * @return model.Computer
     */
    public Computer getFirst() {
        return computerDAO.getFirst();
    }

    /**
     * Delete the last computer added in the DAO.
     */
    public void deleteLast() {
        computerDAO.deleteLast();
    }


}
