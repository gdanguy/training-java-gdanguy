package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import configuration.Config;
import model.company.Company;
import model.computer.Computer;
import model.db.company.CompanyDB;
import model.db.computer.ComputerDB;

public class CLIService {
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
		int id;
		try{
			switch(action){
				case Config.LIST_COMPUTER :
					return listComputers();
				case Config.LIST_COMPANIES :
					return listCompanies();
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
					return "Invalid input";
			}
		}catch(NullPointerException | NumberFormatException e){
    		System.err.println(e);
    		return "Invalid input";
    	}
	}
	
	
    private static Computer inputComputer(Scanner s) {
    	try{
	    	String name = lireSaisieUtilisateur(s,"Enter computer Name : ");
	    	String introduced = lireSaisieUtilisateur(s,"Enter computer Introduced (format :YYYY-MM-DD hh:mm:ss or press enter) : ");
	    	String discontinued = lireSaisieUtilisateur(s,"Enter computer Discontinued (format :YYYY-MM-DD hh:mm:ss or press enter) : ");
	    	int companyId = Integer.parseInt(lireSaisieUtilisateur(s,"Enter computer Company id : "));
			return new Computer(-1,name,introduced,discontinued,companyId);
    	}catch(NullPointerException | NumberFormatException e){
    		System.err.println(e);
    		return null;
    	}
	}
    
    
    private static Computer inputComputer(Scanner s, Computer c, int id) {
    	try{
	    	String name = lireSaisieUtilisateur(s,"Enter computer Name (before : "+c.getName()+") : ");
	    	String introduced = lireSaisieUtilisateur(s,"Enter computer Introduced (format :YYYY-MM-DD hh:mm:ss or press enter) (before : "+c.getIntroduced()+") : ");
	    	String discontinued = lireSaisieUtilisateur(s,"Enter computer Discontinued (format :YYYY-MM-DD hh:mm:ss or press enter) (before : "+c.getDiscontinued()+") : ");
	    	int companyId = Integer.parseInt(lireSaisieUtilisateur(s,"Enter computer Company id (before : "+c.getCompany_id()+"): "));
			return new Computer(id,name,introduced,discontinued,companyId);
    	}catch(NullPointerException | NumberFormatException e){
    		System.err.println(e);
    		return null;
    	}
	}

	/**
	 * List computers
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String listComputers() throws ClassNotFoundException, SQLException{
		ComputerDB db = new ComputerDB();
		ArrayList<Computer> result = db.getComputer();
		if( result == null ){
			return "No computer in the database";
		}else{
			return result.toString();
		}
	}
	
    /**
     * List companies
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public static String listCompanies() throws ClassNotFoundException, SQLException{
		CompanyDB db = new CompanyDB();
		ArrayList<Company> result = db.getCompanies();
		if( result == null ){
			return "No computer in the database";
		}else{
			return result.toString();
		}
	}
	
    /**
     * Show computer details (the detailed information of only one computer)
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public static String showComputerdetails(int id) throws ClassNotFoundException, SQLException{
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
	public static String createComputer(Computer computer) throws ClassNotFoundException, SQLException, NumberFormatException{
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
	public static String updateComputer(Computer computer) throws ClassNotFoundException, SQLException, NumberFormatException{
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
		ComputerDB db = new ComputerDB();
		return db.deleteComputer(id);
	}
}
