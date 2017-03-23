package controller;

import model.computer.Computer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import service.dao.DAOService;
import service.dao.DAOServiceImpl;
import service.dto.DTOService;
import service.dto.DTOServiceImpl;
import service.validator.ValidatorFront;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

    /**
     * Forward the addComputer jsp.
     * @param request no change
     * @param response no change
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOService serviceDAO = new DAOServiceImpl();
        DTOService serviceDTO = new DTOServiceImpl();
        request.setAttribute("listCompany", serviceDTO.convertCompanyToCompanyDTO(serviceDAO.listAllCompanies()));
        request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
    }

    /**
     * Create a computer in DataBase.
     * @param request get computer date
     * @param response to dashboard.jsp
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOService service = new DAOServiceImpl();
        int id = DAOService.ECHEC_FLAG;
        String name = ValidatorFront.getValidName(request.getParameter("computerName"));
        LocalDateTime introduced = ValidatorFront.convertStringToLocalDateTime(request.getParameter("introduced"));
        LocalDateTime discontinued = ValidatorFront.convertStringToLocalDateTime(request.getParameter("discontinued"));
        int companyId = Integer.parseInt(request.getParameter("companyId"));

        int updateSucces =  service.createComputer(
                    new Computer(id, name, introduced, discontinued, service.getCompany(companyId)));
        if (updateSucces < 0) {
            request.getRequestDispatcher("/views/500.html").forward(request, response);
        }
        request.getRequestDispatcher("/dashboard").forward(request, response);
    }
}
