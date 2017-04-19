package cli;

import model.dao.DAOException;


public class MainCLI {
    public CLIControleur cli;

    public void setCli(CLIControleur cli) {
        this.cli = cli;
    }

    /**
     * Run the CLI.
     * @param args no use
     * @throws DAOException if SQL fail
     */
    public static void main(String[] args) throws DAOException {
        MainCLI mainCLI = new MainCLI();
        mainCLI.cli.displayUserInterface();
    }
}
