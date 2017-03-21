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

@WebServlet(name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends HttpServlet {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

    /**
     * Forward the addComputer jsp.
     * @param request no change
     * @param response no change
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DAOService service = new DAOServiceImpl();
            request.setAttribute("listCompany", service.listAllCompanies());
        } catch (SQLException e) {
            logger.error("" + e);
        }
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
        try {
            DAOService service = new DAOServiceImpl();
            int id = -1;
            String name = request.getParameter("computerName");
            if (name.equals("")) {
                name = "no name";
            }
            String intro = request.getParameter("introduced");
            LocalDateTime introduced = null;
            if (intro == null || intro.equals("")) {
                intro = null;
            }
            String disco = request.getParameter("discontinued");
            LocalDateTime discontinued = null;
            if (disco == null || disco.equals("")) {
                discontinued = null;
            }
            int companyId = Integer.parseInt(request.getParameter("companyId"));
            System.out.println(id);
            System.out.println(name);
            System.out.println(intro + " -> " + introduced);
            System.out.println(disco + " -> " + discontinued);
            System.out.println(companyId);
            service.createComputer(
                    new Computer(id, name, introduced, discontinued, service.getCompany(companyId)));
        } catch (SQLException e) {
            logger.error("" + e);
        }
        request.getRequestDispatcher("/").forward(request, response);
    }
}
