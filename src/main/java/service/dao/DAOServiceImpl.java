package service.dao;

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
import model.dao.DAOException;

public class DAOServiceImpl implements DAOService {
    private Logger logger = LoggerFactory.getLogger(DAOServiceImpl.class);

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     * @throws DAOException if model bug
     */
    public int countComputers() throws DAOException {
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.countComputers();
    }

    /**
     * Get the first page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @return the first page of Company or Computer
     * @throws DAOException if model bug
     */
    public Pages<?> list(String type) throws DAOException {
        return list(type, 0);
    }

    /**
     * Get a page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @param page int
     * @return a page of Company or Computer
     * @throws DAOException if model bug
     */
    public Pages<?> list(String type, int page) throws DAOException {

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
     * @throws DAOException if model bug
     */
    public Pages<Computer> listComputers(int page) throws DAOException {
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
     * @throws DAOException if model bug
     */
    public Pages<Computer> listComputers(int page, int sizePage) throws DAOException {
        logger.info("List all Computers");
        ComputerDAOImpl db = ComputerDAO.getInstance();
        Pages<Computer> result = db.getPageComputer(page, sizePage);
        return result;
    }

    /**
     * Get a page of Computer with a search.
     * @param search word research
     * @return a page of Computer
     * @throws DAOException if model bug
     */
    public Pages<Computer> listComputers(String search) throws DAOException {
        logger.info("List all Computers");
        ComputerDAOImpl db = ComputerDAO.getInstance();
        Pages<Computer> result = db.getPageComputer(search);
        return result;
    }

    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     * @throws DAOException if model bug
     */
    public Pages<Company> listCompanies(int page) throws DAOException {
        logger.info("List all Companies");
        CompanyDAOImpl db = CompanyDAO.getInstance();
        Pages<Company> result = db.getPageCompanies(page);
        return result;
    }

    /**
     * Get details of one Computer.
     * @param id of the Computer
     * @return String
     * @throws DAOException if model bug
     */
    public String showComputerdetails(int id) throws DAOException {
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
     * @throws DAOException if model bug
     */
    public Computer getComputer(int id) throws DAOException {
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.getComputerDetails(id);
    }

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     * @throws NumberFormatException if bad parameter
     * @throws DAOException if model bug
     */
    public String createComputer(Computer computer)  throws DAOException, NumberFormatException {
        logger.info("Create a computer, " + computer);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.createComputer(computer).toStringDetails();
    }

    /**
     * Update a Computer in DataBase.
     * @param computer to insert
     * @return Computer in a String format
     * @throws NumberFormatException if bad parameter
     * @throws DAOException if model bug
     */
    public String updateComputer(Computer computer) throws DAOException, NumberFormatException {
        logger.info("Update a Computer, new : " + computer);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.updateComputer(computer).toStringDetails();
    }

    /**
     * Delete a Computer in DataBase.
     * @param id of the Computer
     * @return "Delete a computer, id = " + id
     * @throws DAOException if model bug
     */
    public String deleteComputer(int id) throws DAOException {
        logger.info("Delete a computer, id = " + id);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        return db.deleteComputer(id);
    }

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     * @throws DAOException if model bug
     */
    public Company getCompany(int id) throws DAOException {
        if (id < 0) {
           return null;
        }
        CompanyDAOImpl db = CompanyDAO.getInstance();
        return db.getCompany(id);
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     * @throws DAOException if model bug
     */
    public ArrayList<Company> listAllCompanies() throws DAOException {
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
