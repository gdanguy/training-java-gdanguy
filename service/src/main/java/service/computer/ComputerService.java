package service.computer;

import core.model.Computer;
import core.utils.Page;

import java.util.List;

public interface ComputerService {
    int ECHEC_FLAG = -1;

    /**
     * Get the number of model.Computer.
     * @return the number of computer in DataBase
     */
    int count();

    /**
     * Get a page of model.Computer.
     * @param page int
     * @return a page of model.Computer
     */
    Page<Computer> list(int page);

    /**
     * Get a page of model.Computer with a size of sizePage.
     * @param page     int
     * @param sizePage int
     * @return a page of model.Computer
     */
    Page<Computer> list(int page, int sizePage);

    /**
     * Get a page of model.Computer with a size of sizePage and a order.
     * @param page     int
     * @param sizePage int
     * @param order order of a page
     * @return a page of model.Computer
     */
    Page<Computer> list(int page, int sizePage, String order);

    /**
     * Get a page of model.Computer with a search.
     * @param search word research
     * @return a page of model.Computer
     */
    Page<Computer> list(String search);

    /**
     * Get details of one model.Computer.
     * @param id of the model.Computer
     * @return String
     */
    String showdetails(int id);

    /**
     * Get one model.Computer.
     * @param id of the model.Computer
     * @return model.Computer
     */
    Computer get(int id);

    /**
     * Get All Computer.
     * @return List<Computer>
     */
    List<Computer> getAll();

    /**
     * Create a model.Computer in DataBase.
     * @param computer to insert
     * @return model.Computer in a String format
     */
    int create(Computer computer);

    /**
     * Update a model.Computer in DataBase.
     * @param computer to insert
     * @return if computer was uptaded
     */
    boolean update(Computer computer);

    /**
     * Delete a model.Computer in DataBase.
     * @param id of the model.Computer
     * @return "Delete a computer, id = " + id
     */
    String delete(int id);

    /**
     * Delete Computers in DataBase.
     * @param listId list of the model.Computer
     */
    void delete(List<Integer> listId);


    /**
     * Delete the last computer in the DAO.
     */
    void deleteLast();

    /**
     * Get the first computer of the DataBase.
     * @return model.Computer
     */
    Computer getFirst();


}
