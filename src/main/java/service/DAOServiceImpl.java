package service;

import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import model.Pages;
import model.company.Company;
import model.computer.Computer;
import model.dao.company.CompanyDAO;
import model.dao.company.CompanyDAOImpl;
import model.dao.computer.ComputerDAO;
import model.dao.computer.ComputerDAOImpl;

public class DAOServiceImpl implements DAOService {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(DAOServiceImpl.class);

    /**
     * Get the first page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @return the first page of Company or Computer
     * @throws SQLException if SQL fail
     */
    public Pages<?> list(String type) throws SQLException {
        return list(type, 0);
    }

    /**
     * Get a page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @param page int
     * @return a page of Company or Computer
     * @throws SQLException if SQL fail
     */
    public Pages<?> list(String type, int page) throws SQLException {

        if (type.equals(TYPE_COMPANY)) {
            return listCompanies(page);
        } else if (type.equals(TYPE_COMPUTER)) {
            return listComputers(page);
        }
        return null;
    }

    /**
     * Get a page of Computer.
     * @param page int
     * @return a page of Computer
     * @throws SQLException if SQL fail
     */
    public Pages<Computer> listComputers(int page) throws SQLException {
        logger.info("List all Computers");
        ComputerDAOImpl db = ComputerDAO.getInstance();
        Pages<Computer> result = db.getPageComputer(page);
        return result;
    }

    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     * @throws SQLException if SQL fail
     */
    public Pages<Company> listCompanies(int page) throws SQLException {
        logger.info("List all Companies");
        CompanyDAOImpl db = CompanyDAO.getInstance();
        Pages<Company> result = db.getPageCompanies(page);
        return result;
    }

    /**
     * Get details of one Computer.
     * @param id of the Computer
     * @return String
     * @throws SQLException if SQL fail
     */
    public String showComputerdetails(int id) throws SQLException {
        logger.info("Get a computers, id =" + id);
        Computer result = getComputer(id);
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
     * @throws SQLException if SQL fail
     */
    public Computer getComputer(int id) throws SQLException {
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.getComputerDetails(id);
    }

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     * @throws SQLException if SQL fail
     * @throws NumberFormatException if bad parameter
     */
    public String createComputer(Computer computer) throws SQLException, NumberFormatException {
        logger.info("Create a computer, " + computer);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.createComputer(computer).toStringDetails();
    }

    /**
     * Update a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     * @throws SQLException if SQL fail
     * @throws NumberFormatException if bad parameter
     */
    public String updateComputer(Computer computer) throws SQLException, NumberFormatException {
        logger.info("Update a Computer, new : " + computer);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.updateComputer(computer).toStringDetails();
    }

    /**
     * Delete a Computer in DataBase.
     * @param id of the Computer
     * @return "Delete a computer, id = " + id
     * @throws SQLException if SQL fail
     */
    public String deleteComputer(int id) throws SQLException {
        logger.info("Delete a computer, id = " + id);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.deleteComputer(id);
    }
}
