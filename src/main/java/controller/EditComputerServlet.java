package controller;

import model.GenericBuilder;
import model.computer.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import service.CompanyService;
import service.ComputerService;
import service.mappy.CompanyMapper;
import service.mappy.computer.ComputerDTO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditComputerServlet", urlPatterns = "/editComputer")
public class EditComputerServlet extends UpdateComputerServlet {
    private static final String ID = "id";
    private static final String COMPUTER = "computer";
    private static final String LIST = "listCompany";
    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;
    private CompanyMapper companyMap = new CompanyMapper();

    /**
     * Init beans.
     * @param config the servlet config for spring
     * @throws ServletException if bug
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        this.computerService = (ComputerService) ac.getBean("computerService");
        this.companyService = (CompanyService) ac.getBean("companyService");
    }

    /**
     * Get the id of the computer wanted et set the computer to the jsp.
     * @param request  contains the id
     * @param response contains the computer
     * @throws ServletException if bug
     * @throws IOException      if bug
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(ID));
        request.setAttribute(ID, id);
        Computer c = computerService.get(id);
        request.setAttribute(COMPUTER, GenericBuilder.of(ComputerDTO::new)
                .with(ComputerDTO::setId, id)
                .with(ComputerDTO::setName, c.getName())
                .with(ComputerDTO::setIntroduced, c.getIntroduced())
                .with(ComputerDTO::setDiscontinued, c.getDiscontinued())
                .with(ComputerDTO::setCompany, c.getCompany())
                .build());
        request.setAttribute(LIST, companyMap.toList(companyService.listAll()));
        request.getRequestDispatcher(DashboardServlet.EDIT_COMPUTER_JSP).forward(request, response);
    }

    /**
     * Update a computer.
     * @param request  contains data
     * @param response return
     * @throws ServletException if bug
     * @throws IOException      if bug
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean updateSucces = computerService.update(getComputerModified(request));
        if (!updateSucces) {
            request.getRequestDispatcher(DashboardServlet.ERROR_500_JSP).forward(request, response);
        }
        response.sendRedirect(request.getContextPath() + DashboardServlet.DASHBOARD);
    }
}
