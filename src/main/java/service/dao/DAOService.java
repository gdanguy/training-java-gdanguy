package service.dao;

import java.util.ArrayList;

import model.Pages;
import model.company.Company;
import model.computer.Computer;

public interface DAOService {
    String TYPE_COMPUTER = "computer";
    String TYPE_COMPANY = "company";
    int ECHEC_FLAG = -1;


    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     */
    int countComputers();

    /**
     * Get the first page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @return the first page of Company or Computer
     */
    Pages<?> list(String type);

    /**
     * Get a page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @param page int
     * @return a page of Company or Computer
     */
    Pages<?> list(String type, int page);

    /**
     * Get a page of Computer.
     * @param page int
     * @return a page of Computer
     */
    Pages<Computer> listComputers(int page);

    /**
     * Get a page of Computer with a size of sizePage.
     * @param page int
     * @param sizePage int
     * @return a page of Computer
     */
    Pages<Computer> listComputers(int page, int sizePage);

    /**
     * Get a page of Computer with a search.
     * @param search word researched
     * @return a page of Computer
     */
    Pages<Computer> listComputers(String search);

    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     */
    Pages<Company> listCompanies(int page);

    /**
     * Get details of one Computer.
     * @param id of the Computer
     * @return String
     */
    String showComputerdetails(int id);

    /**
     * Get one Computer.
     * @param id of the Computer
     * @return Computer
     */
    Computer getComputer(int id);

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     */
    int createComputer(Computer computer);

    /**
     * Update a Computer in DataBase.
     * @param computer to insert
     * @return if computer was uptaded
     */
    boolean updateComputer(Computer computer);

    /**
     * Delete a Computer in DataBase.
     * @param id of the Computer
     * @return "Delete a computer, id = " + id
     */
    String deleteComputer(int id);

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     */
    Company getCompany(int id);

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    ArrayList<Company> listAllCompanies();

    /**
     * Delete the last computer added in the DAO.
     */
    void deleteLastComputer();
}
