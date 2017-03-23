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
     */
    public int countComputers() {
        try {
            ComputerDAOImpl db = ComputerDAO.getInstance();
            return db.countComputers();
        } catch (DAOException e) {
            logger.error(e.toString());
            return ECHEC_FLAG;
        }
    }

    /**
     * Get the first page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @return the first page of Company or Computer
     */
    public Pages<?> list(String type) {
        return list(type, 0);
    }

    /**
     * Get a page of Company or Computer.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     * @param page int
     * @return a page of Company or Computer
     */
    public Pages<?> list(String type, int page) {
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
     */
    public Pages<Computer> listComputers(int page) {
        return listComputers(page, Pages.PAGE_SIZE);
    }

    /**
     * Get a page of Computer with a size of sizePage.
     * @param page int
     * @param sizePage int
     * @return a page of Computer
     */
    public Pages<Computer> listComputers(int page, int sizePage) {
        logger.info("List all Computers");
        try {
            ComputerDAOImpl db = ComputerDAO.getInstance();
            Pages<Computer> result = db.getPageComputer(page, sizePage);
            return result;
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Get a page of Computer with a search.
     * @param search word research
     * @return a page of Computer
     */
    public Pages<Computer> listComputers(String search) {
        logger.info("List all Computers");
        try {
            ComputerDAOImpl db = ComputerDAO.getInstance();
            Pages<Computer> result = db.getPageComputer(search);
            return result;
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Get a page of Company.
     * @param page int
     * @return a page of Company
     */
    public Pages<Company> listCompanies(int page) {
        logger.info("List all Companies");
        try {
            CompanyDAOImpl db = CompanyDAO.getInstance();
            Pages<Company> result = db.getPageCompanies(page);
            return result;
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Get details of one Computer.
     * @param id of the Computer
     * @return String
     */
    public String showComputerdetails(int id) {
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
     */
    public Computer getComputer(int id) {
        try {
            ComputerDAOImpl db = ComputerDAO.getInstance();
            return db.getComputerDetails(id);
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * Create a Computer in DataBase.
     * @param computer to insert
     * @return if computer was added
     * @throws NumberFormatException if bad parameter
     * @throws DAOException if model bug
     */
    public int createComputer(Computer computer) {
        logger.info("Create a computer, " + computer);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        try {
            Computer result = db.createComputer(computer);
            if (result != null) {
                return result.getId();
            }
        } catch (DAOException e) {
            logger.error(e.toString());
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return ECHEC_FLAG;
    }

    /**
     * Update a Computer in DataBase.
     * @param computer to insert
     * @return if computer was uptaded
     */
    public boolean updateComputer(Computer computer) {
        logger.info("Update a Computer, new : " + computer);
        ComputerDAOImpl db = ComputerDAO.getInstance();
        try {
            Computer computerUpdated = db.updateComputer(computer);
            if (computerUpdated != null) {
               return true;
            }
        } catch (DAOException e) {
            logger.error(e.toString());
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return false;
    }

    /**
     * Delete a Computer in DataBase.
     * @param id of the Computer
     * @return "Delete a computer, id = " + id
     */
    public String deleteComputer(int id) {
        logger.info("Delete a computer, id = " + id);
        if (id < 0) {
            logger.error("Invalid ID");
            return "Invalid ID";
        } else {
            try {
                ComputerDAOImpl db = ComputerDAO.getInstance();
                return db.deleteComputer(id);
            } catch (DAOException e) {
                logger.error(e.toString());
                return null;
            }
        }
    }

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     */
    public Company getCompany(int id) {
        if (id < 0) {
           return null;
        }
        try {
            CompanyDAOImpl db = CompanyDAO.getInstance();
            return db.getCompany(id);
        } catch (DAOException e) {
            return null;
        }
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    public ArrayList<Company> listAllCompanies() {
        try {
            CompanyDAOImpl db = CompanyDAO.getInstance();
            return db.getAllCompany();
        } catch (DAOException e) {
            logger.error(e.toString());
            return null;
        }
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
