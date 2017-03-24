package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import model.company.Company;
import model.computer.Computer;
import service.CompanyService;
import service.CompanyServiceImpl;
import service.ComputerService;
import service.ComputerServiceImpl;
import service.validator.ValidatorFront;

import java.time.LocalDateTime;

/**
 * Created by ebiz on 23/03/17.
 */
public abstract class UpdateComputerServlet extends HttpServlet {

    /**
     * Get the computer set by the user.
     * @param request of the jsp
     * @return the computer wanted
     */
    protected Computer getComputer(HttpServletRequest request) {
        CompanyService service = CompanyService.getInstance();
        int id = CompanyService.ECHEC_FLAG;
        String name = ValidatorFront.getValidName(request.getParameter("computerName"));
        LocalDateTime introduced = ValidatorFront.convertStringToLocalDateTime(request.getParameter("introduced"));
        LocalDateTime discontinued = ValidatorFront.convertStringToLocalDateTime(request.getParameter("discontinued"));
        int companyId = Integer.parseInt(request.getParameter("companyId"));
        return new Computer(id, name, introduced, discontinued, service.get(companyId));
    }

    /**
     * Get the computer set by the user, if user don't set value, keep older.
     * @param request of the jsp
     * @return the computer wanted
     */
    protected Computer getComputerModified(HttpServletRequest request) {
        Computer userInsert = getComputer(request);
        ComputerService service = ComputerService.getInstance();
        int id = Integer.parseInt(request.getParameter("id"));
        Computer old = service.get(id);
        boolean notModified = true;
        String name = userInsert.getName();
        LocalDateTime introduced = userInsert.getIntroduced();
        LocalDateTime discontinued = userInsert.getDiscontinued();
        Company company = userInsert.getCompany();
        if (name.equals(ValidatorFront.NAME_DEFAULT)) {
            name = old.getName();
            notModified = false;
        }
        if (introduced == null) {
            introduced = old.getIntroduced();
            notModified = false;
        }
        if (discontinued == null) {
            discontinued = old.getDiscontinued();
            notModified = false;
        }
        if (notModified) {
            return userInsert;
        } else {
            return new Computer(id, name, introduced, discontinued, company);
        }
    }
}
