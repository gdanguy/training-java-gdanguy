package cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainCLI {
    /**
     * Run the CLI.
     * @param args no use
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-console.xml");
        CliController cli = (CliController) context.getBean("cliController");
        cli.displayUserInterface();
    }
}
