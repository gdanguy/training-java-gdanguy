package Service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.MysqlDataTruncation;
import model.Pages;
import model.company.Company;
import model.computer.Computer;
import model.db.company.CompanyDBImpl;
import model.db.computer.ComputerDBImpl;

public class DAOServiceImpl implements DAOService{
	private Logger logger = LoggerFactory.getLogger(DAOServiceImpl.class);
	public static final String TYPE_COMPUTER = "computer";
	public static final String TYPE_COMPANY = "company";

    public Pages<?> list(String type) throws ClassNotFoundException, SQLException {
    	return list(type,0);
    }
    
    public Pages<?> list(String type, int page) throws ClassNotFoundException, SQLException {
    	if(type.equals(TYPE_COMPANY))
    		return listCompanies(page);
    	else if(type.equals(TYPE_COMPUTER))
    		return listComputers(page);
    	return null;
    }
    
	/**
	 * Get a page computers
     * @param page
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Pages<Computer> listComputers(int page) throws ClassNotFoundException, SQLException{
		logger.info("List all Computers");
		ComputerDBImpl db = new ComputerDBImpl();
		Pages<Computer> result = db.getPageComputer(page);
		return result;
	}
	
    /**
     * Get a page of companies
     * @param page
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public Pages<Company> listCompanies(int page) throws ClassNotFoundException, SQLException{
		logger.info("List all Companies");
		CompanyDBImpl db = new CompanyDBImpl();
		Pages<Company> result = db.getPageCompanies(page);
		return result;
	}
	
    /**
     * Show computer details (the detailed information of only one computer)
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public String showComputerdetails(int id) throws ClassNotFoundException, SQLException{
		logger.info("Get a computers, id ="+id);
		Computer result = getComputer(id);
		if( result == null ){
			return "No computer corresponding in the database";
		}else{
			return result.toStringDetails();
		}
	}
	
	public Computer getComputer(int id) throws ClassNotFoundException, SQLException{
		ComputerDBImpl db = new ComputerDBImpl();
		return db.getComputerDetails(id);
	}
	
	/**
	 * Create a computer, the id of parameter is no matter.
	 * @param computer
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String createComputer(Computer computer) throws ClassNotFoundException, SQLException, NumberFormatException, MysqlDataTruncation{
		logger.info("Create a computer, "+computer);
		ComputerDBImpl db = new ComputerDBImpl();
		return db.createComputer(computer).toStringDetails();
	}
	
    /**
     * Update a computer
     * @param computer
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public String updateComputer(Computer computer) throws ClassNotFoundException, SQLException, NumberFormatException, MysqlDataTruncation{
		logger.info("Update a Computer, new : "+computer);
		ComputerDBImpl db = new ComputerDBImpl();
		return db.updateComputer(computer).toStringDetails();
	}
	
	/**
	 * Delete a computer
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String deleteComputer(int id) throws ClassNotFoundException, SQLException{
		logger.info("Delete a computer, id = "+id);
		ComputerDBImpl db = new ComputerDBImpl();
		return db.deleteComputer(id);
	}
}
