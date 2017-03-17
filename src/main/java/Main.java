
import java.sql.SQLException;

import cli.CLIControleur;


public class Main {
    /**
     * Run the CLI.
     * @param args no use
     * @throws SQLException if SQL fail
     */
    public static void main(String[] args) throws SQLException {
        CLIControleur cli = new CLIControleur();
        cli.displayUserInterface();
    }
}
