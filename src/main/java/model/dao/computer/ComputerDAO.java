package model.dao.computer;

import model.Page;
import model.computer.Computer;

import java.util.List;

public interface ComputerDAO {

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     */
    int count();

    /**
     * This method returns the page of computers.
     * @param page corresponds to the page's number to be retrieved
     * @return Page<Computer> corresponds to the page
     */
    Page<Computer> getPage(int page);

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param page     corresponds to the page's number to be retrieved
     * @param sizePage size of a page
     * @param order    order of a page
     * @return Page<Computer> corresponds to the page
     */
    Page<Computer> getPage(int page, int sizePage, String order);

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param search word researched
     * @return Page<Computer> corresponds to the page
     */
    Page<Computer> getPage(String search);

    /**
     * This method returns a computer identified by its id.
     * @param id of the computer
     * @return ths Computer wanted
     */
    Computer getDetails(int id);

    /**
     * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
     * @param computer to add in the database
     * @return Computer generated
     */
    Computer create(Computer computer);

    /**
     * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
     * @param computer modifiedComputer with the id of the one to be modified and with its new attributes.
     * @return if Computer is updated
     */
    boolean update(Computer computer);

    /**
     * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param id of the computer to delete
     * @return if succes : "Computer " + id + " is deleted" or if fail : "Delete Computer failed, no rows affected."
     */
    String delete(int id);

    /**
     * This method removes computers corresponding to the passed listID as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param listId list of the computer to delete
     * @return if succes : "Computers are deleted", else : "Delete Computer failed, no rows affected."
     */
    String delete(List<Integer> listId);

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
     * Delete all computer of one company.
     * @param id the id of the company
     */
    void deleteIdCompany(int id);

}
