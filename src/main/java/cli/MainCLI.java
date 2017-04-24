package cli;

import model.dao.DAOException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:../webapp/WEB-INF/applicationContext.xml"})
public class MainCLI {
    @Autowired
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
