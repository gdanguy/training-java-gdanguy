package cli;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.slf4j.LoggerFactory;

import com.mysql.jdbc.MysqlDataTruncation;

import model.Pages;
import model.computer.Computer;
import model.dao.company.CompanyDAO;
import model.dao.company.CompanyDAOImpl;
import service.DAOServiceImpl;

public class CLIControleur {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(CLIControleur.class);
	public static final Scanner scanner = new Scanner(System.in);

	public static final String NEXT_PAGE = "+";
	public static final String PREVIOUS_PAGE = "-";
	
	public static final String LIST_COMPUTER = "list computers";
	public static final String LIST_COMPANIES = "list companies";
	public static final String SHOW_COMPUTER_DETAILS = "show computer details";
	public static final String CREATE_COMPUTER = "create a computer";
	public static final String UPDATE_COMPUTER = "update a computer";
	public static final String DELETE_COMPUTER = "delete a computer";
	public static final String QUIT = "quit";
	public static final String HELP = "help";
	
	public static final DateTimeFormatter FORMATTEUR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	private DAOServiceImpl service;
	
	public CLIControleur(){
		service = new DAOServiceImpl();
	}
	
	public void displayUserInterface() throws ClassNotFoundException, SQLException{
		System.out.println("Welcome, type "+HELP+" for the list of commands");
		String result="";
		do{
			String retourUtilisateur = lireSaisieUtilisateur("");
			result = choixAction(retourUtilisateur);
			System.out.println(result);
		}while(! result.equals(QUIT) );
	}
	
	
	/**
	 * Returns a String containing the list of options
	 * @return 
	 */
	public String listeOption(){
		String message = LIST_COMPUTER+"\n";
		message+= LIST_COMPANIES+"\n";
		message+= SHOW_COMPUTER_DETAILS+"\n";
		message+= CREATE_COMPUTER+"\n";
		message+= UPDATE_COMPUTER+"\n";
		message+= DELETE_COMPUTER+"\n";
		message+= HELP+"\n";
		message+= QUIT;
		return message;
	}
	
	/**
	 * Return the user input.
	 * @param s
	 * @return
	 */
	public String lireSaisieUtilisateur(){
		return lireSaisieUtilisateur(null);
	}
	
	/**
	 * Displays the requested message and return the user input.
	 * @param s
	 * @param message
	 * @return
	 */
	public String lireSaisieUtilisateur(String message){
		logger.info("Input needed");
		if( message != null ){
			System.out.println(message);
		}
		String str = scanner.nextLine();
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
	public String choixAction(String action) throws ClassNotFoundException, SQLException{
		logger.info("Choose action : '" + action+"'\n");
		int id;
		try{
			switch( action ){
				case LIST_COMPUTER :
					displayListComputers();
					return "";
				case LIST_COMPANIES :
					displayListCompanies();
					return "";
				case SHOW_COMPUTER_DETAILS :
					id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
					return service.showComputerdetails(id);
				case CREATE_COMPUTER :
					Computer c = userInputComputer();
					return service.createComputer(c);
				case UPDATE_COMPUTER :
					id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
					return service.updateComputer(userInputComputer(service.getComputer(id),id));
				case DELETE_COMPUTER :
					id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
					return service.deleteComputer(id);
				case HELP :
					return listeOption();
				case QUIT :
					return QUIT;
				default :
					return "Invalid input\n";
			}
		}catch( NullPointerException | NumberFormatException e ){
			logger.error(e+"\n");
    		return "Invalid input";
    	}catch( MysqlDataTruncation e2 ){
			logger.error(e2+"\n");
    		return "The date must be more recent on 1 January 1970 at 00:00:00";
    	}
	}
	
	private Computer userInputComputer() throws ClassNotFoundException, SQLException {
    	logger.info("User input new Computer");
    	try{
	    	String name = lireSaisieUtilisateur("Enter computer Name : ");
	    	String introduced = lireSaisieUtilisateur(
	    			"Enter computer Introduced (format :YYYY-MM-DD hh:mm:ss or press enter) : ");
	    	LocalDateTime intro = null;
	    	if( ! introduced.equals("") ){
	    		LocalDateTime.parse(introduced,FORMATTEUR);
	    	}
	    	String discontinued = lireSaisieUtilisateur(
	    			"Enter computer Discontinued (format :YYYY-MM-DD hh:mm:ss or press enter) : ");
	    	LocalDateTime disco = null;
	    	if( ! discontinued.equals("") ){
	    		disco = LocalDateTime.parse(discontinued,FORMATTEUR);
	    	}
	    	
	    	int companyId = Integer.parseInt(lireSaisieUtilisateur("Enter computer Company id : "));
			CompanyDAOImpl db = CompanyDAO.getInstance();
			return new Computer(-1,name,intro,disco,db.getCompany(companyId));
    	}catch(NullPointerException | NumberFormatException e){
			logger.error(e+"\n");
    		return null;
    	}
	}

    
    private Computer userInputComputer(Computer oldComputer, int id) throws ClassNotFoundException, SQLException {
    	logger.info("User input new Computer, old : "+oldComputer);
    	try{
	    	String name = lireSaisieUtilisateur("Enter computer Name (before : "+oldComputer.getName()+") : ");

	    	String introduced = lireSaisieUtilisateur(
	    			"Enter computer Introduced (format :YYYY-MM-DD hh:mm:ss or press enter) (before : "+oldComputer.getIntroduced()+") : ");
	    	LocalDateTime intro = null;
	    	if( ! introduced.equals("") ){
	    		intro = LocalDateTime.parse(introduced,FORMATTEUR);
	    	}
	    	String discontinued = lireSaisieUtilisateur(
	    			"Enter computer Discontinued (format :YYYY-MM-DD hh:mm:ss or press enter) (before : "+oldComputer.getDiscontinued()+") : ");
	    	LocalDateTime disco = null;
	    	if( ! discontinued.equals("") ){
	    		disco = LocalDateTime.parse(discontinued,FORMATTEUR);
	    	}
	    	
	    	int companyId = Integer.parseInt(lireSaisieUtilisateur("Enter computer Company id (before : "+oldComputer.getCompany()+"): "));
			CompanyDAOImpl db = CompanyDAO.getInstance();
	    	
			return new Computer(id,name,intro,disco,db.getCompany(companyId));
    	}catch(NullPointerException | NumberFormatException e){
			logger.error(e+"\n");
    		return null;
    	}
	}
    
    private void displayListCompanies() throws ClassNotFoundException, SQLException {
    	displayList(DAOServiceImpl.TYPE_COMPANY);
	}

	private void displayListComputers() throws ClassNotFoundException, SQLException {
    	displayList(DAOServiceImpl.TYPE_COMPUTER);
	}
	
	private void displayList(String type) throws ClassNotFoundException, SQLException{
		Pages<?> list = service.list(type);
		if( list.isEmpty() ){
			System.out.println("No item wanted in the database");
		}else{
			System.out.println(list);
			boolean quit;
			do{
				quit = true;
				String input = lireSaisieUtilisateur("Type '"+NEXT_PAGE+"' for next page, '"+PREVIOUS_PAGE+"' for previous page, other for quit");
				if( input.equals(NEXT_PAGE) ){
					list = service.list(type,list.getNextPage());
					quit = false;
					if( list.isEmpty() ){
						list = service.listCompanies(list.getPreviousPage());
					}
					System.out.println(list);
				}else if( input.equals(PREVIOUS_PAGE) ){
					list = service.listCompanies(list.getPreviousPage());
					quit = false;
					System.out.println(list);
				}
			}while( !quit );
		}
	}
}
