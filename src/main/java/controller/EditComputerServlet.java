package controller;

import model.GenericBuilder;
import model.computer.Computer;
import service.mappy.CompanyMapper;
import service.mappy.computer.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import service.CompanyService;
import service.ComputerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditComputerServlet", urlPatterns = "/editComputer")
public class EditComputerServlet extends UpdateComputerServlet {
    private Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);

    /**
     * Get the id of the computer wanted et set the computer to the jsp.
     * @param request contains the id
     * @param response contains the computer
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("id", id);
        ComputerService computerService = ComputerService.getInstance();
        CompanyService companyService = CompanyService.getInstance();
        CompanyMapper companyMap = CompanyMapper.getInstance();
        Computer c = computerService.get(id);
        request.setAttribute("computer", GenericBuilder.of(ComputerDTO::new)
                .with(ComputerDTO::setId, id)
                .with(ComputerDTO::setName, c.getName())
                .with(ComputerDTO::setIntroduced, c.getIntroduced())
                .with(ComputerDTO::setDiscontinued, c.getDiscontinued())
                .with(ComputerDTO::setCompany, c.getCompany())
                .build());
        request.setAttribute("listCompany", companyMap.toDTO(companyService.listAll()));
        request.getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
    }

    /**
     * Update a computer.
     * @param request contains data
     * @param response return
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerService service = ComputerService.getInstance();
        boolean updateSucces = service.update(getComputerModified(request));
        if (!updateSucces) {
            request.getRequestDispatcher("/views/500.html").forward(request, response);
        }
        request.getRequestDispatcher("/dashboard").forward(request, response);
    }
}
