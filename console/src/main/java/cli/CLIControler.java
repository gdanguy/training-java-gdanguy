package cli;

import core.exception.CDBException;
import dao.company.CompanyDAO;
import core.model.Company;
import core.model.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.company.CompanyServiceImpl;
import service.computer.ComputerServiceImpl;
import core.utils.GenericBuilder;
import core.utils.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CLIControler {
    private Logger logger = LoggerFactory.getLogger(CLIControler.class);
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

    private CompanyServiceImpl serviceCompany;
    private ComputerServiceImpl serviceComputer;
    private CompanyDAO db;

    public void setServiceCompany(CompanyServiceImpl serviceCompany) {
        this.serviceCompany = serviceCompany;
    }

    public void setServiceComputer(ComputerServiceImpl serviceComputer) {
        this.serviceComputer = serviceComputer;
    }

    public void setDb(CompanyDAO db) {
        this.db = db;
    }

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
                    return serviceComputer.get(id).toStringDetails();
                case CREATE_COMPUTER:
                    Computer c = userInputComputer();
                    if (serviceComputer.create(c) >= 0) {
                        return "model.Computer Created";
                    } else {
                        return "Error create computer";
                    }
                case UPDATE_COMPUTER:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    if (serviceComputer.update(userInputComputer(serviceComputer.get(id), id))) {
                        return "Update computer done";
                    } else {
                        return "Error update computer";
                    }
                case DELETE_COMPUTER:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    return serviceComputer.delete(id);
                case ADD_COMPANY:
                    Company company = userInputCompany();
                    id = serviceCompany.create(company);
                    //return new model.Company(id, company.getName()).toString() + " was created";
                    return (GenericBuilder.of(Company::new)
                            .with(Company::setId, id)
                            .with(Company::setName, company.getName())
                            .build())
                            .toString() + " was created";
                case DELETE_COMPANY:
                    id = Integer.parseInt(lireSaisieUtilisateur("Enter computer ID : "));
                    if (serviceCompany.delete(id)) {
                        return "model.Company with id = " + id + " was deleted";
                    } else {
                        return "model.Company with id = " + id + " wasn't deleted";
                    }
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
            //CompanyDAOImpl db = CompanyDAO.getInstance();
            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, -1)
                    .with(Computer::setName, name)
                    .with(Computer::setIntroduced, intro)
                    .with(Computer::setDiscontinued, disco)
                    .with(Computer::setCompany, db.get(companyId))
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
            //CompanyDAOImpl db = CompanyDAO.getInstance();

            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, id)
                    .with(Computer::setName, name)
                    .with(Computer::setIntroduced, intro)
                    .with(Computer::setDiscontinued, disco)
                    .with(Computer::setCompany, db.get(companyId))
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
        Page<?> list;
        if (type.equals(TYPE_COMPANY)) {
            list = serviceCompany.list(0);
        } else {
            list = serviceComputer.list(0);
        }

        if (list.isEmpty()) {
            System.out.println("No item wanted in the database");
        } else {
            System.out.println(list);
            boolean quit;
            do {
                quit = true;
                String input = lireSaisieUtilisateur("Type '" + NEXT_PAGE + "' for next page, '" + PREVIOUS_PAGE + "' for previous page, other for quit");
                if (input.equals(NEXT_PAGE)) {
                    if (type.equals(TYPE_COMPANY)) {
                        list = serviceCompany.list(list.getNextPage());
                    } else {
                        list = serviceComputer.list(list.getNextPage());
                    }
                    quit = false;
                    if (list.isEmpty()) {
                        if (type.equals(TYPE_COMPANY)) {
                            list = serviceCompany.list(list.getPreviousPage());
                        } else {
                            list = serviceComputer.list(list.getPreviousPage());
                        }
                    }
                    System.out.println(list);
                } else if (input.equals(PREVIOUS_PAGE)) {
                    if (type.equals(TYPE_COMPANY)) {
                        list = serviceCompany.list(list.getPreviousPage());
                    } else {
                        list = serviceComputer.list(list.getPreviousPage());
                    }
                    quit = false;
                    System.out.println(list);
                }
            } while (!quit);
        }
    }
}
