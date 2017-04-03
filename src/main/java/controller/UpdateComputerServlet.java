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
    protected Computer getComputer(HttpServletRequest request) {
        CompanyService service = CompanyService.getInstance();
        int id = CompanyService.ECHEC_FLAG;
        ArrayList<String> messageError = new ArrayList<>();
        //Test of the name
        String name = request.getParameter(NAME);
        String resultError;
        resultError = Validator.nameValidator(name);
        if (resultError != null) {
            messageError.add(resultError);
        }
        //Test of introduced
        String intro = request.getParameter(INTRO);
        resultError = Validator.dateValidate(intro);
        if (resultError != null) {
            messageError.add(resultError);
        }
        //Test of discontinued
        String disco = request.getParameter(DISCO);
        resultError = Validator.dateValidate(disco);
        if (resultError != null) {
            messageError.add(resultError);
        }
        //Test of Company id
        String compId = request.getParameter(COMPANY_ID);
        resultError = Validator.intValidator(compId);
        if (resultError != null) {
            messageError.add(resultError);
        }
        LocalDateTime introduced = Validator.parseString(intro);
        LocalDateTime discontinued = Validator.parseString(disco);
        resultError = Validator.testDate(introduced, discontinued);
        if (resultError != null) {
            messageError.add(resultError);
        }
        //set message error
        if (messageError.size() != 0) {
            request.setAttribute(MESSAGE_ERROR, messageError);
            return null;
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
        Computer userInsert = getComputer(request);
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
