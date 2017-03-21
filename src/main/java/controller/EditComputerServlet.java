package controller;

import model.computer.Computer;
import org.slf4j.LoggerFactory;
import service.DAOService;
import service.DAOServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet(name = "EditComputerServlet", urlPatterns = "/editComputer")
public class EditComputerServlet extends HttpServlet {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);

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
            request.setAttribute("computer", service.getComputer(id));
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
            Computer old = null;
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("computerName");
            if (name.equals("")) {
                if (old == null) {
                    old = service.getComputer(id);
                }
                name = old.getName();
            }
            String intro = request.getParameter("introduced");
            //TODO faire la gestion du LocalDateTime
            LocalDateTime introduced = null;
            if (intro.equals("")) {
                if (old == null) {
                    old = service.getComputer(id);
                }
                introduced = old.getIntroduced();
            }
            String disco = request.getParameter("discontinued");
            //TODO faire la gestion du LocalDateTime
            LocalDateTime discontinued = null;
            if (disco.equals("")) {
                if (old == null) {
                    old = service.getComputer(id);
                }
                discontinued = old.getIntroduced();
            }
            int companyId = Integer.parseInt(request.getParameter("companyId"));


            service.updateComputer(
                    new Computer(id, name, introduced, discontinued, service.getCompany(companyId)));
        } catch (SQLException e) {
            logger.error("" + e);
        }
        request.getRequestDispatcher("/").forward(request, response);
    }
}
