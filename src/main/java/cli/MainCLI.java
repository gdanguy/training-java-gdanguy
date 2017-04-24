package cli;

import model.dao.DAOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainCLI {
    /**
     * Run the CLI.
     * @param args no use
     * @throws DAOException if SQL fail
     */
    public static void main(String[] args) throws DAOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/webapp/WEB-INF/applicationContext.xml");
        CLIControler cli = (CLIControler) context.getBean("cliControler");
        cli.displayUserInterface();
    }
}
