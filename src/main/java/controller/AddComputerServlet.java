package controller;

import model.computer.Computer;
import service.CompanyService;
import service.ComputerService;
import service.mappy.CompanyMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends UpdateComputerServlet {
    public static final String LIST_COMPANIES_ATTRIBUTE = "listCompany";

    /**
     * Forward the addComputer jsp.
     * @param request  no change
     * @param response no change
     * @throws ServletException if bug
     * @throws IOException      if bug
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CompanyService serviceDAO = CompanyService.getInstance();
        CompanyMapper computerMap = CompanyMapper.getInstance();
        request.setAttribute(LIST_COMPANIES_ATTRIBUTE, computerMap.toDTO(serviceDAO.listAll()));
        request.getRequestDispatcher(DashboardServlet.ADD_COMPUTER_JSP).forward(request, response);
    }

    /**
     * Create a computer in DataBase.
     * @param request  get computer date
     * @param response to dashboard.jsp
     * @throws ServletException if bug
     * @throws IOException      if bug
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Computer input = getComputerNoStrict(request);
        if (input == null) {
            doGet(request, response);
        }
        ComputerService service = ComputerService.getInstance();
        int updateSucces = service.create(input);
        if (updateSucces < 0) {
            request.getRequestDispatcher(DashboardServlet.ERROR_500_JSP).forward(request, response);
        }
        response.sendRedirect(request.getContextPath()+DashboardServlet.DASHBOARD);
    }
}
