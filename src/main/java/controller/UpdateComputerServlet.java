package controller;

import model.GenericBuilder;
import model.company.Company;
import model.computer.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import service.CompanyService;
import service.ComputerService;
import service.validator.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class UpdateComputerServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(UpdateComputerServlet.class);
    public static final String MESSAGE_ERROR = "messageError";
    private static final String ID = "id";
    private static final String NAME = "computerName";
    private static final String INTRO = "introduced";
    private static final String DISCO = "discontinued";
    private static final String COMPANY_ID = "companyId";
    @Autowired
    private ComputerService serviceComputer;
    @Autowired
    private CompanyService serviceCompany;


    /**
     * Init beans.
     * @param config the servlet config for spring
     * @throws ServletException if bug
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        this.serviceComputer = (ComputerService) ac.getBean("computerService");
        this.serviceCompany = (CompanyService) ac.getBean("companyService");
    }

    /**
     * Get the computer set by the user.
     * @param request of the jsp
     * @return the computer wanted
     */
    protected Computer getComputerNoStrict(HttpServletRequest request) {
        return getComputer(request, false);
    }

    /**
     * Get the computer set by the user without null.
     * @param request of the jsp
     * @return the computer wanted
     */
    protected Computer getComputer(HttpServletRequest request) {
        return getComputer(request, true);
    }

    /**
     * Get the computer set by the user.
     * @param request of the jsp
     * @param strict if strict of not
     * @return the computer wanted
     */
    private Computer getComputer(HttpServletRequest request, boolean strict) {
        int id = CompanyService.ECHEC_FLAG;
        ArrayList<String> messageError = new ArrayList<>();
        //Test of the name
        String name = request.getParameter(NAME);
        String intro = request.getParameter(INTRO);
        String disco = request.getParameter(DISCO);
        String compId = request.getParameter(COMPANY_ID);
        LocalDateTime introduced = Validator.parseString(intro);
        LocalDateTime discontinued = Validator.parseString(disco);
        if (strict) {
            String resultError;
            //Test of name
            //resultError = Validator.nameValidator(name);
            //setError(messageError, resultError);

            //Test of introduced
            resultError = Validator.dateValidate(intro);
            setError(messageError, resultError);

            //Test of discontinued
            resultError = Validator.dateValidate(disco);
            setError(messageError, resultError);

            //Test of Company id
            resultError = Validator.companyidValidate(compId);
            setError(messageError, resultError);

            //test date
            resultError = Validator.testDate(introduced, discontinued);
            setError(messageError, resultError);

            //set message error
            if (messageError.size() != 0) {
                request.setAttribute(MESSAGE_ERROR, messageError);
                return null;
            }
        }
        int companyId = Integer.parseInt(compId);
        return GenericBuilder.of(Computer::new)
                .with(Computer::setId, id)
                .with(Computer::setName, name)
                .with(Computer::setIntroduced, introduced)
                .with(Computer::setDiscontinued, discontinued)
                .with(Computer::setCompany, serviceCompany.get(companyId))
                .build();
    }

    /**
     * If error != null, add it to massageError.
     * @param messageError list of error
     * @param error string
     */
    private void setError(List messageError, String error) {
        if (error != null) {
            logger.warn(error);
            messageError.add(error);
        }
    }

    /**
     * Get the computer set by the user, if user don't set value, keep older.
     * @param request of the jsp
     * @return the computer wanted
     */
    protected Computer getComputerModified(HttpServletRequest request) {
        Computer userInsert = getComputer(request);
        int id = Integer.parseInt(request.getParameter(ID));
        Computer old = serviceComputer.get(id);
        boolean notModified = true;
        String name = userInsert.getName();
        LocalDateTime introduced = userInsert.getIntroduced();
        LocalDateTime discontinued = userInsert.getDiscontinued();
        Company company = userInsert.getCompany();
        if (introduced == null) {
            introduced = old.getIntroduced();
            notModified = false;
        }
        if (discontinued == null) {
            discontinued = old.getDiscontinued();
            notModified = false;
        }
        if (notModified) {
            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, id)
                    .with(Computer::setName, userInsert.getName())
                    .with(Computer::setIntroduced, userInsert.getIntroduced())
                    .with(Computer::setDiscontinued, userInsert.getDiscontinued())
                    .with(Computer::setCompany, userInsert.getCompany())
                    .build();
        } else {
            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, id)
                    .with(Computer::setName, name)
                    .with(Computer::setIntroduced, introduced)
                    .with(Computer::setDiscontinued, discontinued)
                    .with(Computer::setCompany, company)
                    .build();
        }
    }
}
