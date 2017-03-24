package model.dao.computer;

import model.Pages;
import model.computer.Computer;
import model.dao.DAOException;

public interface ComputerDAO {

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     * @throws DAOException if no result
     */
    int countComputers() throws DAOException;

    /**
     * This method returns the page of computers.
     * @param page corresponds to the page's number to be retrieved
     * @return Pages<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    Pages<Computer> getPageComputer(int page) throws DAOException;

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param page corresponds to the page's number to be retrieved
     * @param sizePage size of a page
     * @return Pages<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    Pages<Computer> getPageComputer(int page, int sizePage) throws DAOException;

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param search word researched
     * @return Pages<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    Pages<Computer> getPageComputer(String search) throws DAOException;

    /**
     * This method returns a computer identified by its id.
     * @param id of the computer
     * @return ths Computer wanted
     * @throws DAOException if SQL request fail
     */
    Computer getComputerDetails(int id) throws DAOException;

    /**
     * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
     * @param computer to add in the database
     * @return Computer generated
     * @throws DAOException if SQL fail
     */
    Computer createComputer(Computer computer) throws DAOException;

    /**
     * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
     * @param computer modifiedComputer with the id of the one to be modified and with its new attributes.
     * @return Computer updated
     * @throws DAOException if SQL fail
     */
    Computer updateComputer(Computer computer) throws DAOException;

    /**
     * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param id of the computer to delete
     * @return if succes : "Computer " + id + " is deleted" or if fail : "Delete Computer failed, no rows affected."
     * @throws DAOException if SQL fail
     */
    String deleteComputer(int id) throws DAOException;

    /**
     * Delete the last computer added in the DAO.
     * @throws DAOException if delete failed
     */
    void deleteLastComputer() throws DAOException;

    /**
     * Return the Instance of DAO.
     * @return the Instance of DAO
     */
    static ComputerDAOImpl getInstance() {
        return ComputerDAOImpl.INSTANCE;
    }
}
