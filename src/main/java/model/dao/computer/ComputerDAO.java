package model.dao.computer;

import java.sql.SQLException;

import model.Pages;
import model.computer.Computer;

public interface ComputerDAO {

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     * @throws SQLException if no result
     */
    int countComputers() throws SQLException;

    /**
     * This method returns the page of computers.
     * @param page corresponds to the page's number to be retrieved
     * @return Pages<Computer> corresponds to the page
     * @throws SQLException if SQL request fail
     */
    Pages<Computer> getPageComputer(int page) throws SQLException;

    /**
     * This method returns a computer identified by its id.
     * @param id of the computer
     * @return ths Computer wanted
     * @throws SQLException if SQL request fail
     */
    Computer getComputerDetails(int id) throws SQLException;

    /**
     * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
     * @param computer to add in the database
     * @return Computer generated
     * @throws SQLException if SQL fail
     */
    Computer createComputer(Computer computer) throws SQLException;

    /**
     * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
     * @param computer modifiedComputer with the id of the one to be modified and with its new attributes.
     * @return Computer updated
     * @throws SQLException if SQL fail
     */
    Computer updateComputer(Computer computer) throws SQLException;

    /**
     * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param id of the computer to delete
     * @return if succes : "Computer " + id + " is deleted" or if fail : "Delete Computer failed, no rows affected."
     * @throws SQLException if SQL fail
     */
    String deleteComputer(int id) throws SQLException;

    /**
     * Return the Instance of DAO.
     * @return the Instance of DAO
     */
    static ComputerDAOImpl getInstance() {
        return ComputerDAOImpl.INSTANCE;
    }
}
