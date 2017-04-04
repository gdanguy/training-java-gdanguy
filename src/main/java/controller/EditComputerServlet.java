package controller;

import model.GenericBuilder;
import model.computer.Computer;
import service.CompanyService;
import service.ComputerService;
import service.mappy.CompanyMapper;
import service.mappy.computer.ComputerDTO;

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
        ComputerService computerService = ComputerService.getInstance();
        CompanyService companyService = CompanyService.getInstance();
        CompanyMapper companyMap = CompanyMapper.getInstance();
        Computer c = computerService.get(id);
        request.setAttribute(COMPUTER, GenericBuilder.of(ComputerDTO::new)
                .with(ComputerDTO::setId, id)
                .with(ComputerDTO::setName, c.getName())
                .with(ComputerDTO::setIntroduced, c.getIntroduced())
                .with(ComputerDTO::setDiscontinued, c.getDiscontinued())
                .with(ComputerDTO::setCompany, c.getCompany())
                .build());
        request.setAttribute(LIST, companyMap.toDTO(companyService.listAll()));
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
        ComputerService service = ComputerService.getInstance();
        boolean updateSucces = service.update(getComputerModified(request));
        if (!updateSucces) {
            request.getRequestDispatcher(DashboardServlet.ERROR_500_JSP).forward(request, response);
        }
        response.sendRedirect(DashboardServlet.DASHBOARD);
    }
}
