package controller;

import model.computer.Computer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import service.CompanyService;
import service.ComputerService;
import service.mappy.CompanyMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends UpdateComputerServlet {
    public static final String LIST_COMPANIES_ATTRIBUTE = "listCompany";
    private CompanyService serviceCompany;
    private CompanyMapper companyMap;
    private ComputerService serviceComputer;

    public void setServiceCompany(CompanyService serviceCompany) {
        this.serviceCompany = serviceCompany;
    }

    public void setCompanyMap(CompanyMapper companyMap) {
        this.companyMap = companyMap;
    }

    public void setServiceComputer(ComputerService serviceComputer) {
        this.serviceComputer = serviceComputer;
    }

    /**
     * Init beans.
     * @param config the servlet config for spring
     * @throws ServletException if bug
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        this.companyMap = (CompanyMapper) ac.getBean("companyMapper");
        this.serviceComputer = (ComputerService) ac.getBean("computerService");
        this.serviceCompany = (CompanyService) ac.getBean("companyService");
    }

    /**
     * Forward the addComputer jsp.
     * @param request  no change
     * @param response no change
     * @throws ServletException if bug
     * @throws IOException      if bug
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(LIST_COMPANIES_ATTRIBUTE, companyMap.toDTO(serviceCompany.listAll()));
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
        int updateSucces = serviceComputer.create(input);
        if (updateSucces < 0) {
            request.getRequestDispatcher(DashboardServlet.ERROR_500_JSP).forward(request, response);
        }
        response.sendRedirect(request.getContextPath() + DashboardServlet.DASHBOARD);
    }
}
