package cli;

import core.exception.CDBException;
import core.model.Company;
import core.model.Computer;
import core.utils.Constant;
import core.utils.GenericBuilder;
import core.utils.Page;
import map.CompanyMapperDTO;
import map.ComputerMapperDTO;
import map.company.CompanyDTO;
import map.computer.ComputerDTO;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(CliController.class);
    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String NEXT_PAGE = "+";
    private static final String PREVIOUS_PAGE = "-";

    private static final String LIST_COMPUTER = "list computers";
    private static final String LIST_COMPANIES = "list companies";
    private static final String SHOW_COMPUTER_DETAILS = "show computer details";
    private static final String CREATE_COMPUTER = "create a computer";
    private static final String UPDATE_COMPUTER = "update a computer";
    private static final String DELETE_COMPUTER = "delete a computer";
    private static final String ADD_COMPANY = "create a company";
    private static final String DELETE_COMPANY = "delete a company";
    private static final String QUIT = "quit";
    private static final String HELP = "help";

    private static final DateTimeFormatter FORMATTEUR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String TYPE_COMPUTER = "computer";
    private static final String TYPE_COMPANY = "company";
    private static final String URL_COMPUTER = "http://localhost:8085/api/computers";
    private static final String URL_COMPANY = "http://localhost:8085/api/companies";
    private static final Client CLIENT = ClientBuilder.newClient();
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String AUTH_STRING = USERNAME + ":" + PASSWORD;
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Basic " + java.util.Base64.getEncoder().encodeToString(AUTH_STRING.getBytes());

    private WebTarget myResource;
    private final ComputerMapperDTO computerMapperDTO = new ComputerMapperDTO();
    private final CompanyMapperDTO companyMapperDTO = new CompanyMapperDTO();

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
        LOGGER.info("Input needed");
        if (message != null) {
            System.out.println(message);
        }
        String str = SCANNER.nextLine();
        str = str.toLowerCase();
        return str;
    }

    /**
     * Call the method corresponding to the parameter.
     * @param action Action wanted
     * @return String
     * @throws CDBException if SQL fail
     */
    public String choixAction(String action) throws CDBException {
        LOGGER.info("Choose action : '" + action + "'\n");
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
                    myResource = CLIENT.target(URL_COMPUTER + "/{" + Constant.ID + "}").resolveTemplate(Constant.ID, id);
                    return computerMapperDTO.from(myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).get(ComputerDTO.class)).toStringDetails();
                case CREATE_COMPUTER:
                    ComputerDTO c = computerMapperDTO.to(userInputComputer());
                    myResource = CLIENT.target(URL_COMPUTER);
                    int result = myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).post(Entity.json(c), Integer.class);
                    if (result >= 0) {
                        return "Computer Created";
                    } else {
                        return "Error create computer";
                    }
                case UPDATE_COMPUTER:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    myResource = CLIENT.target(URL_COMPUTER + "/{" + Constant.ID + "}").resolveTemplate(Constant.ID, id);
                    Computer old = myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).get(Computer.class);
                    ComputerDTO computer = computerMapperDTO.to(userInputComputer(old, id));
                    myResource = CLIENT.target(URL_COMPUTER);
                    myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).put(Entity.json(computer));
                    return "Update computer done";
                case DELETE_COMPUTER:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    myResource = CLIENT.target(URL_COMPUTER);
                    myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).delete(/*Entity.json(id)*/);
                    return "computer delete";
                case ADD_COMPANY:
                    CompanyDTO company = companyMapperDTO.to(userInputCompany());
                    myResource = CLIENT.target(URL_COMPANY);
                    id = myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).post(Entity.json(company), Integer.class);
                    return (GenericBuilder.of(Company::new)
                            .with(Company::setId, id)
                            .with(Company::setName, company.getName())
                            .build())
                            .toString() + " was created";
                case DELETE_COMPANY:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    myResource = CLIENT.target(URL_COMPANY + "/{" + Constant.ID + "}").resolveTemplate(Constant.ID, id);
                    myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).delete();
                    return "Company with id = " + id + " was deleted";
                case HELP:
                    return listeOption();
                case QUIT:
                    return QUIT;
                default:
                    return "Invalid input\n";
            }
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.error(e + "\n");
            return "Invalid input";
        }
    }

    /**
     * Instructs the user to enter information to generate a computer.
     * @return return computer
     * @throws CDBException is SQL fail
     */
    private Computer userInputComputer() throws CDBException {
        LOGGER.info("User input new model.Computer");
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
            myResource = CLIENT.target(URL_COMPUTER + companyId);
            CompanyDTO company = myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).get(CompanyDTO.class);
            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, -1)
                    .with(Computer::setName, name)
                    .with(Computer::setIntroduced, intro)
                    .with(Computer::setDiscontinued, disco)
                    .with(Computer::setCompany, companyMapperDTO.from(company))
                    .build();
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.error(e + "\n");
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
        LOGGER.info("User input new model.Computer, old : " + oldComputer);
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
            myResource = CLIENT.target(URL_COMPUTER + companyId);
            CompanyDTO company = myResource.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE).get(CompanyDTO.class);

            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, id)
                    .with(Computer::setName, name)
                    .with(Computer::setIntroduced, intro)
                    .with(Computer::setDiscontinued, disco)
                    .with(Computer::setCompany, companyMapperDTO.from(company))
                    .build();
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.error(e + "\n");
            return null;
        }
    }

    /**
     * Instructs the user to enter information to generate a company.
     * @return return company
     * @throws CDBException is SQL fail
     */
    private Company userInputCompany() throws CDBException {
        LOGGER.info("User input new model.Company");
        try {
            String name = lireSaisieUtilisateur("Enter model.Company Name : ");
            return GenericBuilder.of(Company::new)
                    .with(Company::setId, -1)
                    .with(Company::setName, name)
                    .build();
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.error(e + "\n");
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
            myResource = CLIENT.target(URL_COMPANY + "/page");
        } else {
            myResource = CLIENT.target(URL_COMPUTER + "/page");
        }
        return myResource.queryParam(Constant.PAGE, page)
                .request(MediaType.APPLICATION_JSON)
                .header(AUTH_HEADER, AUTHORIZATION_HEADER_VALUE)
                .get(Page.class);
    }
}
