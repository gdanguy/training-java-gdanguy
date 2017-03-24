package controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import service.ComputerService;
import service.ComputerServiceImpl;
import service.CompanyService;
import service.CompanyServiceImpl;
import service.dto.DTOService;
import service.dto.DTOServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends UpdateComputerServlet {
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
        CompanyService serviceDAO = CompanyService.getInstance();
        DTOService serviceDTO = new DTOServiceImpl();
        request.setAttribute("listCompany", serviceDTO.convertCompanyToCompanyDTO(serviceDAO.listAll()));
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
        ComputerService service = ComputerService.getInstance();

        int updateSucces =  service.create(getComputer(request));
        if (updateSucces < 0) {
            request.getRequestDispatcher("/views/500.html").forward(request, response);
        }
        request.getRequestDispatcher("/dashboard").forward(request, response);
    }
}
