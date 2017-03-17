package service;

import java.sql.SQLException;

import model.Pages;
import model.company.Company;
import model.computer.Computer;

public interface DAOService {
    String TYPE_COMPUTER = "computer";
    String TYPE_COMPANY = "company";
    /**
     * Get the first page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @return the first page of Company or Computer
     * @throws SQLException if SQL fail
     */
    Pages<?> list(String type) throws SQLException;

    /**
     * Get a page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @param page int
     * @return a page of Company or Computer
     * @throws SQLException if SQL fail
     */
    Pages<?> list(String type, int page) throws SQLException;

    /**
     * Get a page of Computer.
     * @param page int
     * @return a page of Computer
     * @throws SQLException if SQL fail
     */
    Pages<Computer> listComputers(int page) throws SQLException;

    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     * @throws SQLException if SQL fail
     */
    Pages<Company> listCompanies(int page) throws SQLException;

    /**
     * Get details of one Computer.
     * @param id of the Computer
     * @return String
     * @throws SQLException if SQL fail
     */
    String showComputerdetails(int id) throws SQLException;

    /**
     * Get one Computer.
     * @param id of the Computer
     * @return Computer
     * @throws SQLException if SQL fail
     */
    Computer getComputer(int id) throws SQLException;

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     * @throws SQLException if SQL fail
     * @throws NumberFormatException if bad parameter
     */
    String createComputer(Computer computer) throws SQLException, NumberFormatException;

    /**
     * Update a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     * @throws SQLException if SQL fail
     * @throws NumberFormatException if bad parameter
     */
    String updateComputer(Computer computer) throws SQLException, NumberFormatException;

    /**
     * Delete a Computer in DataBase.
     * @param id of the Computer
     * @return "Delete a computer, id = " + id
     * @throws SQLException if SQL fail
     */
    String deleteComputer(int id) throws SQLException;
}
