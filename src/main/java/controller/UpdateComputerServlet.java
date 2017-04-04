package controller;

import model.GenericBuilder;
import model.company.Company;
import model.computer.Computer;
import service.CompanyService;
import service.ComputerService;
import service.validator.Validator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by ebiz on 23/03/17.
 */
public abstract class UpdateComputerServlet extends HttpServlet {
    public static final String MESSAGE_ERROR = "messageError";
    private static final String ID = "id";
    private static final String NAME = "computerName";
    private static final String INTRO = "introduced";
    private static final String DISCO = "discontinued";
    private static final String COMPANY_ID = "companyId";

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
        CompanyService service = CompanyService.getInstance();
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
            resultError = Validator.nameValidator(name);
            if (resultError != null) {
                messageError.add(resultError);
            }
            //Test of introduced
            resultError = Validator.dateValidate(intro);
            if (resultError != null) {
                messageError.add(resultError);
            }
            //Test of discontinued
            resultError = Validator.dateValidate(disco);
            if (resultError != null) {
                messageError.add(resultError);
            }
            //Test of Company id
            resultError = Validator.intValidator(compId);
            if (resultError != null) {
                messageError.add(resultError);
            }
            //test date
            resultError = Validator.testDate(introduced, discontinued);
            if (resultError != null) {
                messageError.add(resultError);
            }
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
                .with(Computer::setCompany, service.get(companyId))
                .build();
    }

    /**
     * Get the computer set by the user, if user don't set value, keep older.
     * @param request of the jsp
     * @return the computer wanted
     */
    protected Computer getComputerModified(HttpServletRequest request) {
        Computer userInsert = getComputerNoStrict(request);
        ComputerService service = ComputerService.getInstance();
        int id = Integer.parseInt(request.getParameter(ID));
        Computer old = service.get(id);
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
