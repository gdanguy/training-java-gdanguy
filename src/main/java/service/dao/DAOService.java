package service.dao;

import java.util.ArrayList;

import model.Pages;
import model.company.Company;
import model.computer.Computer;
import model.dao.DAOException;
import model.dto.computer.ComputerDTO;

public interface DAOService {
    String TYPE_COMPUTER = "computer";
    String TYPE_COMPANY = "company";


    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     * @throws DAOException if model bug
     */
    int countComputers() throws DAOException;

    /**
     * Get the first page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @return the first page of Company or Computer
     * @throws DAOException if model bug
     */
    Pages<?> list(String type) throws DAOException;

    /**
     * Get a page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @param page int
     * @return a page of Company or Computer
     * @throws DAOException if model bug
     */
    Pages<?> list(String type, int page) throws DAOException;

    /**
     * Get a page of Computer.
     * @param page int
     * @return a page of Computer
     * @throws DAOException if model bug
     */
    Pages<Computer> listComputers(int page) throws DAOException;

    /**
     * Get a page of Computer with a size of sizePage.
     * @param page int
     * @param sizePage int
     * @return a page of Computer
     * @throws DAOException if model bug
     */
    Pages<Computer> listComputers(int page, int sizePage) throws DAOException;

    /**
     * Get a page of Computer with a search.
     * @param search word researched
     * @return a page of Computer
     * @throws DAOException if model bug
     */
    Pages<Computer> listComputers(String search) throws DAOException;

    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     * @throws DAOException if model bug
     */
    Pages<Company> listCompanies(int page) throws DAOException;

    /**
     * Get details of one Computer.
     * @param id of the Computer
     * @return String
     * @throws DAOException if model bug
     */
    String showComputerdetails(int id) throws DAOException;

    /**
     * Get one Computer.
     * @param id of the Computer
     * @return Computer
     * @throws DAOException if model bug
     */
    Computer getComputer(int id) throws DAOException;

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     * @throws NumberFormatException if bad parameter
     * @throws DAOException if model bug
     */
    String createComputer(Computer computer) throws DAOException, NumberFormatException;

    /**
     * Update a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     * @throws NumberFormatException if bad parameter
     * @throws DAOException if model bug
     */
    String updateComputer(Computer computer) throws DAOException, NumberFormatException;

    /**
     * Delete a Computer in DataBase.
     * @param id of the Computer
     * @return "Delete a computer, id = " + id
     * @throws DAOException if model bug
     */
    String deleteComputer(int id) throws DAOException;

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     * @throws DAOException if model bug
     */
    Company getCompany(int id) throws DAOException;

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     * @throws DAOException if model bug
     */
    ArrayList<Company> listAllCompanies() throws DAOException;

    /**
     * Convert Pages<Computer> to Pages<ComputerDTO>.
     * @param pages Pages of Computer
     * @return the Pages<ComputerDTO> generated
     */
    Pages<ComputerDTO> convertComputerToComputerDTO(Pages<Computer> pages);

    /**
     * Convert ArrayList<Computer> to ArrayList<ComputerDTO>.
     * @param list ArrayList of Computer
     * @return the ArrayList<ComputerDTO> generated
     */
    ArrayList<ComputerDTO> convertComputerToComputerDTO(ArrayList<Computer> list);
}
