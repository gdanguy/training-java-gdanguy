package cli;

import core.exception.CDBException;
import core.model.Company;
import core.model.Computer;
import core.utils.GenericBuilder;
import core.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component("cliController")
public class CliController {
    private Logger logger = LoggerFactory.getLogger(CliController.class);
    public static final Scanner SCANNER = new Scanner(System.in);

    public static final String NEXT_PAGE = "+";
    public static final String PREVIOUS_PAGE = "-";

    public static final String LIST_COMPUTER = "list computers";
    public static final String LIST_COMPANIES = "list companies";
    public static final String SHOW_COMPUTER_DETAILS = "show computer details";
    public static final String CREATE_COMPUTER = "create a computer";
    public static final String UPDATE_COMPUTER = "update a computer";
    public static final String DELETE_COMPUTER = "delete a computer";
    public static final String ADD_COMPANY = "create a company";
    public static final String DELETE_COMPANY = "delete a company";
    public static final String QUIT = "quit";
    public static final String HELP = "help";

    public static final DateTimeFormatter FORMATTEUR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String TYPE_COMPUTER = "computer";
    public static final String TYPE_COMPANY = "company";

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";    private static final String AUTHSTRING = USERNAME + ":" + PASSWORD;
    private static final String AUTHHEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Basic " + java.util.Base64.getEncoder().encodeToString(AUTHSTRING.getBytes());

    private Client client = ClientBuilder.newClient();
    private WebTarget myResource;

    /**
     * cli.MainCLI of the CLI.
     * @throws CDBException is SQL fail
     */
    public void displayUserInterface() throws CDBException {
        System.out.println("Welcome, type " + HELP + " for the list of commands");
        String result = "";
        do {
            String retourUtilisateur = lireSaisieUtilisateur("");
            result = choixAction(retourUtilisateur);
            System.out.println(result);
        } while (!result.equals(QUIT));
    }


    /**
     * Returns a String containing the list of options.
     * @return list of Option
     */
    public String listeOption() {
        String message = LIST_COMPUTER + "\n";
        message += LIST_COMPANIES + "\n";
        message += SHOW_COMPUTER_DETAILS + "\n";
        message += CREATE_COMPUTER + "\n";
        message += UPDATE_COMPUTER + "\n";
        message += DELETE_COMPUTER + "\n";
        message += ADD_COMPANY + "\n";
        message += DELETE_COMPANY + "\n";
        message += HELP + "\n";
        message += QUIT;
        return message;
    }

    /**
     * Return the user input.
     * @return String
     */
    public String lireSaisieUtilisateur() {
        return lireSaisieUtilisateur(null);
    }

    /**
     * Displays the requested message and return the user input.
     * @param message display
     * @return String
     */
    public String lireSaisieUtilisateur(String message) {
        logger.info("Input needed");
        if (message != null) {
            System.out.println(message);
        }
        String str = SCANNER.nextLine();
        str.toLowerCase();
        return str;
    }

    /**
     * Call the method corresponding to the parameter.
     * @param action Action wanted
     * @return String
     * @throws CDBException if SQL fail
     */
    public String choixAction(String action) throws CDBException {
        logger.info("Choose action : '" + action + "'\n");
        int id;
        try {
            switch (action) {
                case LIST_COMPUTER:
                    displayListComputers();
                    return "";
                case LIST_COMPANIES:
                    displayListCompanies();
                    return "";
                case SHOW_COMPUTER_DETAILS:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    myResource = client.target("http://localhost:8085/rest/computer/" + id);
                    return myResource.request(MediaType.APPLICATION_JSON).header(AUTHHEADER, AUTHORIZATION_HEADER_VALUE).get(Computer.class).toStringDetails();
                case CREATE_COMPUTER:
                    Computer c = userInputComputer();
                    myResource = client.target("http://localhost:8085/rest/computer/add");
                    int result = myResource.request(MediaType.APPLICATION_JSON).header(AUTHHEADER, AUTHORIZATION_HEADER_VALUE).post(Entity.json(c), Integer.class);
                    if (result >= 0) {
                        return "model.Computer Created";
                    } else {
                        return "Error create computer";
                    }
                case UPDATE_COMPUTER:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    myResource = client.target("http://localhost:8085/rest/computer/" + id);
                    Computer old = myResource.request(MediaType.APPLICATION_JSON).header(AUTHHEADER, AUTHORIZATION_HEADER_VALUE).get(Computer.class);
                    Computer computer = userInputComputer(old, id);
                    myResource = client.target("http://localhost:8085/rest/computer/edit");
                    myResource.request(MediaType.APPLICATION_JSON).header(AUTHHEADER, AUTHORIZATION_HEADER_VALUE).post(Entity.json(computer));
                    return "Update computer done";
                case DELETE_COMPUTER:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    myResource = client.target("http://localhost:8085/rest/computer/delete/" + id);
                    return "computer delete";
                case ADD_COMPANY:
                    Company company = userInputCompany();
                    myResource = client.target("http://localhost:8085/rest/company/add");
                    id = myResource.request(MediaType.APPLICATION_JSON).header(AUTHHEADER, AUTHORIZATION_HEADER_VALUE).post(Entity.json(company), Integer.class);
                    return (GenericBuilder.of(Company::new)
                            .with(Company::setId, id)
                            .with(Company::setName, company.getName())
                            .build())
                            .toString() + " was created";
                case DELETE_COMPANY:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    myResource = client.target("http://localhost:8085/rest/company/delete/" + id);
                    return "model.Company with id = " + id + " was deleted";
                case HELP:
                    return listeOption();
                case QUIT:
                    return QUIT;
                default:
                    return "Invalid input\n";
            }
        } catch (NullPointerException | NumberFormatException e) {
            logger.error(e + "\n");
            return "Invalid input";
        }
    }

    /**
     * Instructs the user to enter information to generate a computer.
     * @return return computer
     * @throws CDBException is SQL fail
     */
    private Computer userInputComputer() throws CDBException {
        logger.info("User input new model.Computer");
        try {
            String name = lireSaisieUtilisateur("Enter computer Name : ");
            String introduced = lireSaisieUtilisateur(
                    "Enter computer Introduced (format :YYYY-MM-DD hh:mm:ss or press enter) : ");
            LocalDateTime intro = null;
            if (!introduced.equals("")) {
                LocalDateTime.parse(introduced, FORMATTEUR);
            }
            String discontinued = lireSaisieUtilisateur(
                    "Enter computer Discontinued (format :YYYY-MM-DD hh:mm:ss or press enter) : ");
            LocalDateTime disco = null;
            if (!discontinued.equals("")) {
                disco = LocalDateTime.parse(discontinued, FORMATTEUR);
            }

            int companyId = Integer.parseInt(lireSaisieUtilisateur("Enter computer model.Company id : "));
            myResource = client.target("http://localhost:8085/rest/computer/" + companyId);
            Company company = myResource.request(MediaType.APPLICATION_JSON).header(AUTHHEADER, AUTHORIZATION_HEADER_VALUE).get(Company.class);
            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, -1)
                    .with(Computer::setName, name)
                    .with(Computer::setIntroduced, intro)
                    .with(Computer::setDiscontinued, disco)
                    .with(Computer::setCompany, company)
                    .build();
        } catch (NullPointerException | NumberFormatException e) {
            logger.error(e + "\n");
            return null;
        }
    }

    /**
     * Instructs the user to enter information to update a computer.
     * @param oldComputer old information
     * @param id          id of the computer
     * @return model.Computer
     * @throws CDBException is SQL fail
     */
    private Computer userInputComputer(Computer oldComputer, int id) throws CDBException {
        logger.info("User input new model.Computer, old : " + oldComputer);
        try {
            String name = lireSaisieUtilisateur("Enter computer Name (before : " + oldComputer.getName() + ") : ");

            String introduced = lireSaisieUtilisateur(
                    "Enter computer Introduced (format :YYYY-MM-DD hh:mm:ss or press enter) (before : " + oldComputer.getIntroduced() + ") : ");
            LocalDateTime intro = null;
            if (!introduced.equals("")) {
                intro = LocalDateTime.parse(introduced, FORMATTEUR);
            }
            String discontinued = lireSaisieUtilisateur(
                    "Enter computer Discontinued (format :YYYY-MM-DD hh:mm:ss or press enter) (before : " + oldComputer.getDiscontinued() + ") : ");
            LocalDateTime disco = null;
            if (!discontinued.equals("")) {
                disco = LocalDateTime.parse(discontinued, FORMATTEUR);
            }

            int companyId = Integer.parseInt(lireSaisieUtilisateur("Enter computer model.Company id (before : " + oldComputer.getCompany() + "): "));
            myResource = client.target("http://localhost:8085/rest/computer/" + companyId);
            Company company = myResource.request(MediaType.APPLICATION_JSON).header(AUTHHEADER, AUTHORIZATION_HEADER_VALUE).get(Company.class);

            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, id)
                    .with(Computer::setName, name)
                    .with(Computer::setIntroduced, intro)
                    .with(Computer::setDiscontinued, disco)
                    .with(Computer::setCompany, company)
                    .build();
        } catch (NullPointerException | NumberFormatException e) {
            logger.error(e + "\n");
            return null;
        }
    }

    /**
     * Instructs the user to enter information to generate a company.
     * @return return company
     * @throws CDBException is SQL fail
     */
    private Company userInputCompany() throws CDBException {
        logger.info("User input new model.Company");
        try {
            String name = lireSaisieUtilisateur("Enter model.Company Name : ");
            return GenericBuilder.of(Company::new)
                    .with(Company::setId, -1)
                    .with(Company::setName, name)
                    .build();
        } catch (NullPointerException | NumberFormatException e) {
            logger.error(e + "\n");
            return null;
        }
    }

    /**
     * display the List of Companies.
     */
    private void displayListCompanies() {
        displayList(TYPE_COMPANY);
    }

    /**
     * display the List of model.Computer.
     */
    private void displayListComputers() {
        displayList(TYPE_COMPUTER);
    }

    /**
     * display list of model.Computer or model.Company.
     * @param type TYPE_COMPUTER or TYPE_COMPANY
     */
    private void displayList(String type) {
        Page<?> list = getPage(type, 0);
        if (list.isEmpty()) {
            System.out.println("No item wanted in the database");
        } else {
            System.out.println(list);
            boolean quit;
            do {
                quit = true;
                String input = lireSaisieUtilisateur("Type '" + NEXT_PAGE + "' for next page, '" + PREVIOUS_PAGE + "' for previous page, other for quit");
                if (input.equals(NEXT_PAGE)) {
                    list = getPage(type, list.getNextPage());
                    quit = false;
                    if (list.isEmpty()) {
                        list = getPage(type, list.getPreviousPage());
                    }
                    System.out.println(list);
                } else if (input.equals(PREVIOUS_PAGE)) {
                    list = getPage(type, list.getPreviousPage());
                    quit = false;
                    System.out.println(list);
                }
            } while (!quit);
        }
    }

    /**
     * Get a page.
     * @param type .
     * @param page .
     * @return .
     */
    private Page getPage(String type, int page) {
        if (type.equals(TYPE_COMPANY)) {
            myResource = client.target("http://localhost:8085/rest/company/page/" + page);
        } else {
            myResource = client.target("http://localhost:8085/rest/computer/page/" + page);
        }
        return myResource.request(MediaType.APPLICATION_JSON).header(AUTHHEADER, AUTHORIZATION_HEADER_VALUE).get(Page.class);
    }
}
