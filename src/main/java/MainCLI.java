import cli.CLIControleur;
import model.dao.DAOException;


public class MainCLI {
    /**
     * Run the CLI.
     * @param args no use
     * @throws DAOException if SQL fail
     */
    public static void main(String[] args) throws DAOException {
        CLIControleur cli = new CLIControleur();
        cli.displayUserInterface();
    }
}
