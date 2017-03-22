package service.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.computer.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import model.Pages;
import model.company.Company;
import model.computer.Computer;
import model.dao.company.CompanyDAO;
import model.dao.company.CompanyDAOImpl;
import model.dao.computer.ComputerDAO;
import model.dao.computer.ComputerDAOImpl;

public class DAOServiceImpl implements DAOService {
    private Logger logger = LoggerFactory.getLogger(DAOServiceImpl.class);

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     * @throws SQLException if no result
     */
    public int countComputers() throws SQLException {
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.countComputers();
    }

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
     * Get a page of Computer with a size of sizePage.
     * @param page int
     * @param sizePage int
     * @return a page of Computer
     * @throws SQLException if SQL fail
     */
    public Pages<Computer> listComputers(int page, int sizePage) throws SQLException {
        logger.info("List all Computers");
        ComputerDAOImpl db = ComputerDAO.getInstance();
        Pages<Computer> result = db.getPageComputer(page, sizePage);
        return result;
    }

    /**
     * Get a page of Computer with a search.
     * @param search word research
     * @return a page of Computer
     * @throws SQLException if SQL fail
     */
    public Pages<Computer> listComputers(String search) throws SQLException {
        logger.info("List all Computers");
        ComputerDAOImpl db = ComputerDAO.getInstance();
        Pages<Computer> result = db.getPageComputer(search);
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

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     * @throws SQLException if SQL fails
     */
    public Company getCompany(int id) throws SQLException {
        if (id < 0) {
           return null;
        }
        CompanyDAOImpl db = CompanyDAO.getInstance();
        return db.getCompany(id);
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     * @throws SQLException if SQL fail
     */
    public ArrayList<Company> listAllCompanies() throws SQLException {
        CompanyDAOImpl db = CompanyDAO.getInstance();
        return db.getAllCompany();
    }

    /**
     * Convert Pages<Computer> to Pages<ComputerDTO>.
     * @param pages Pages of Computer
     * @return the Pages<ComputerDTO> generated
     */
    public Pages<ComputerDTO> convertComputerToComputerDTO(Pages<Computer> pages)  {
        return new Pages<>(convertComputerToComputerDTO(pages.getListPage()), pages.getPageSize());
    }

    /**
     * Convert ArrayList<Computer> to ArrayList<ComputerDTO>.
     * @param list ArrayList of Computer
     * @return the ArrayList<ComputerDTO> generated
     */
    public ArrayList<ComputerDTO> convertComputerToComputerDTO(ArrayList<Computer> list) {
        ArrayList<ComputerDTO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(new ComputerDTO(list.get(i)));
        }
        return result;
    }
}
