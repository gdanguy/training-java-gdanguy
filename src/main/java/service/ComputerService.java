package service;

import model.Page;
import model.computer.Computer;


public interface ComputerService {
    int ECHEC_FLAG = -1;

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     */
    int count();

    /**
     * Get a page of Computer.
     * @param page int
     * @return a page of Computer
     */
    Page<Computer> list(int page);

    /**
     * Get a page of Computer with a size of sizePage.
     * @param page int
     * @param sizePage int
     * @return a page of Computer
     */
    Page<Computer> list(int page, int sizePage);

    /**
     * Get a page of Computer with a search.
     * @param search word research
     * @return a page of Computer
     */
    Page<Computer> list(String search);

    /**
     * Get details of one Computer.
     * @param id of the Computer
     * @return String
     */
    String showdetails(int id);

    /**
     * Get one Computer.
     * @param id of the Computer
     * @return Computer
     */
    Computer get(int id);

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     */
    int create(Computer computer);

    /**
     * Update a Computer in DataBase.
     * @param computer to insert
     * @return if computer was uptaded
     */
    boolean update(Computer computer);

    /**
     * Delete a Computer in DataBase.
     * @param id of the Computer
     * @return "Delete a computer, id = " + id
     */
    String delete(int id);


    /**
     * Delete the last computer added in the DAO.
     */
    void deleteLast();

    /**
     * Get the first computer of the DataBase.
     * @return Computer
     */
    Computer getFirst();

    /**
     * Return the Instance of ComputerServiceImpl.
     * @return the Instance of ComputerServiceImpl
     */
    static ComputerServiceImpl getInstance() {
        return ComputerServiceImpl.INSTANCE;
    }
}
