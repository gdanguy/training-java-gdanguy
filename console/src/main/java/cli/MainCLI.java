package cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainCLI {
    /**
     * Run the CLI.
     * @param args no use
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/webapp/WEB-INF/applicationContext.xml");
        CLIControler cli = (CLIControler) context.getBean("cliControler");
        cli.displayUserInterface();
    }
}
