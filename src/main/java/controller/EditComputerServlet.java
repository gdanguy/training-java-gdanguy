package controller;

import model.computer.Computer;
import model.dto.computer.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import service.dao.DAOService;
import service.dao.DAOServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("id", id);
            DAOService service = new DAOServiceImpl();
            request.setAttribute("computer", new ComputerDTO(service.getComputer(id)));
            request.setAttribute("listCompany", service.listAllCompanies());
        } catch (SQLException e) {
            logger.error("" + e);
        }
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
        try {
            DAOService service = new DAOServiceImpl();
            int id = Integer.parseInt(request.getParameter("id"));
            Computer old = service.getComputer(id);
            String name = request.getParameter("computerName");
            if (name.equals("")) {
                name = old.getName();
            }
            LocalDateTime introduced = convertStringToLocalDateTime(request.getParameter("introduced"));
            if (introduced == null) {
                introduced = old.getIntroduced();
            }
            LocalDateTime discontinued = convertStringToLocalDateTime(request.getParameter("discontinued"));
            if (discontinued == null) {
                discontinued = old.getDiscontinued();
            }
            int companyId = Integer.parseInt(request.getParameter("companyId"));

            service.updateComputer(
                    new Computer(id, name, introduced, discontinued, service.getCompany(companyId)));
        } catch (SQLException e) {
            logger.error("" + e);
            request.getRequestDispatcher("/views/500.html").forward(request, response);
        }
        request.getRequestDispatcher("/dashboard").forward(request, response);
    }
}
