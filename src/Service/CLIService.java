package Service;

import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.MysqlDataTruncation;

import configuration.Config;
import model.company.PagesCompanies;
import model.computer.Computer;
import model.computer.PagesComputers;
import model.db.company.CompanyDB;
import model.db.computer.ComputerDB;

public class CLIService {
	private static Logger logger = LoggerFactory.getLogger(CLIService.class);
	
	/**
	 * Returns a String containing the list of options
	 * @return 
	 */
	public static String listeOption(){
		String message = Config.LIST_COMPUTER+"\n";
		message+= Config.LIST_COMPANIES+"\n";
		message+= Config.SHOW_COMPUTER_DETAILS+"\n";
		message+= Config.CREATE_COMPUTER+"\n";
		message+= Config.UPDATE_COMPUTER+"\n";
		message+= Config.DELETE_COMPUTER+"\n";
		message+= Config.QUIT;
		return message;
		
	}	
	
	/**
	 * Return the user input.
	 * @param s
	 * @return
	 */
	public static String lireSaisieUtilisateur(Scanner s){
		return lireSaisieUtilisateur(s,null);
	}
	
	/**
	 * Displays the requested message and return the user input.
	 * @param s
	 * @param message
	 * @return
	 */
	public static String lireSaisieUtilisateur(Scanner s, String message){
		if( Config.LOGGER_MESSAGE )
			logger.info("Input needed");
		if( message != null ){
			System.out.println(message);
		}
		String str = s.nextLine();
		str.toLowerCase();
		return str;
	}
	
	/**
	 * Call the method corresponding to the parameter.
	 * @param action
	 * @param s
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String choixAction(String action, Scanner s) throws ClassNotFoundException, SQLException{
		if( Config.LOGGER_MESSAGE )
			logger.info("Choose action : '" + action+"'\n");
		int id;
		try{
			switch( action ){
				case Config.LIST_COMPUTER :
					listComputers().display(s);
					return "";
				case Config.LIST_COMPANIES :
					listCompanies().display(s);
					return "";
				case Config.SHOW_COMPUTER_DETAILS :
					id = Integer.parseInt(lireSaisieUtilisateur(s,"Enter computer ID : "));
					return showComputerdetails(id);
				case Config.CREATE_COMPUTER :
					Computer c = inputComputer(s);
					return createComputer(c);
				case Config.UPDATE_COMPUTER :
					id = Integer.parseInt(lireSaisieUtilisateur(s,"Enter computer ID : "));
					return updateComputer(inputComputer(s,getComputer(id),id));
				case Config.DELETE_COMPUTER :
					id = Integer.parseInt(lireSaisieUtilisateur(s,"Enter computer ID : "));
					return deleteComputer(id);
				case Config.HELP :
					return listeOption();
				case Config.QUIT :
					return Config.QUIT;
				default :
					return "Invalid input\n";
			}
		}catch( NullPointerException | NumberFormatException e ){
			if( Config.LOGGER_MESSAGE ){
				logger.error(e+"\n");
			}
    		return "Invalid input";
    	}catch( MysqlDataTruncation e2 ){
    		if( Config.LOGGER_MESSAGE ){
				logger.error(e2+"\n");
    		}
    		return "The date must be more recent on 1 January 1970 at 00:00:00";
    	}
	}
	
	
    private static Computer inputComputer(Scanner s) {
    	if( Config.LOGGER_MESSAGE )
			logger.info("Input new Computer");
    	try{
	    	String name = lireSaisieUtilisateur(s,"Enter computer Name : ");
	    	String introduced = lireSaisieUtilisateur(s,"Enter computer Introduced (format :YYYY-MM-DD hh:mm:ss or press enter) : ");
	    	String discontinued = lireSaisieUtilisateur(s,"Enter computer Discontinued (format :YYYY-MM-DD hh:mm:ss or press enter) : ");
	    	int companyId = Integer.parseInt(lireSaisieUtilisateur(s,"Enter computer Company id : "));
			return new Computer(-1,name,introduced,discontinued,companyId);
    	}catch(NullPointerException | NumberFormatException e){
    		if( Config.LOGGER_MESSAGE ){
				logger.error(e+"\n");
    		}
    		return null;
    	}
	}
    
    
    private static Computer inputComputer(Scanner s, Computer c, int id) {
    	if( Config.LOGGER_MESSAGE )
			logger.info("Input new Computer, old : "+c);
    	try{
	    	String name = lireSaisieUtilisateur(s,"Enter computer Name (before : "+c.getName()+") : ");
	    	String introduced = lireSaisieUtilisateur(s,"Enter computer Introduced (format :YYYY-MM-DD hh:mm:ss or press enter) (before : "+c.getIntroduced()+") : ");
	    	String discontinued = lireSaisieUtilisateur(s,"Enter computer Discontinued (format :YYYY-MM-DD hh:mm:ss or press enter) (before : "+c.getDiscontinued()+") : ");
	    	int companyId = Integer.parseInt(lireSaisieUtilisateur(s,"Enter computer Company id (before : "+c.getCompany_id()+"): "));
			return new Computer(id,name,introduced,discontinued,companyId);
    	}catch(NullPointerException | NumberFormatException e){
    		if( Config.LOGGER_MESSAGE ){
				logger.error(e+"\n");
    		}
    		return null;
    	}
	}

	/**
	 * List computers
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static PagesComputers listComputers() throws ClassNotFoundException, SQLException{
		if( Config.LOGGER_MESSAGE )
			logger.info("List all Computers");
		ComputerDB db = new ComputerDB();
		PagesComputers result = new PagesComputers(db.getAllComputer());
		return result;
	}
	
    /**
     * List companies
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public static PagesCompanies listCompanies() throws ClassNotFoundException, SQLException{
		if( Config.LOGGER_MESSAGE )
			logger.info("List all Companies");
		CompanyDB db = new CompanyDB();
		PagesCompanies result = new PagesCompanies(db.getCompanies());
		return result;
	}
	
    /**
     * Show computer details (the detailed information of only one computer)
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public static String showComputerdetails(int id) throws ClassNotFoundException, SQLException{
		if( Config.LOGGER_MESSAGE )
			logger.info("Get a computers, id ="+id);
		Computer result = getComputer(id);
		if( result == null ){
			return "No computer corresponding in the database";
		}else{
			return result.toStringDetails();
		}
	}
	
	private static Computer getComputer(int id) throws ClassNotFoundException, SQLException{
		ComputerDB db = new ComputerDB();
		return db.getComputerDetails(id);
	}
	
	/**
	 * Create a computer, the id of parameter is no matter.
	 * @param computer
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String createComputer(Computer computer) throws ClassNotFoundException, SQLException, NumberFormatException, MysqlDataTruncation{
		if( Config.LOGGER_MESSAGE )
			logger.info("Create a computer, "+computer);
		ComputerDB db = new ComputerDB();
		return db.createComputer(computer).toStringDetails();
	}
	
    /**
     * Update a computer
     * @param computer
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public static String updateComputer(Computer computer) throws ClassNotFoundException, SQLException, NumberFormatException, MysqlDataTruncation{
		if( Config.LOGGER_MESSAGE )
			logger.info("Update a Computer, new : "+computer);
		ComputerDB db = new ComputerDB();
		return db.updateComputer(computer).toStringDetails();
	}
	
	/**
	 * Delete a computer
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String deleteComputer(int id) throws ClassNotFoundException, SQLException{
		if( Config.LOGGER_MESSAGE )
			logger.info("Delete a computer, id = "+id);
		ComputerDB db = new ComputerDB();
		return db.deleteComputer(id);
	}
}
