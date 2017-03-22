package controller;

import model.computer.Computer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import service.dao.DAOService;
import service.dao.DAOServiceImpl;

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
            LocalDateTime introduced;
            if (intro == null || intro.equals("")) {
                introduced = null;
            } else {
                introduced = Computer.convertStringToLocalDateTime(intro);
            }
            String disco = request.getParameter("discontinued");
            LocalDateTime discontinued;
            if (disco == null || disco.equals("")) {
                discontinued = null;
            } else {
                discontinued = Computer.convertStringToLocalDateTime(disco);
            }
            int companyId = Integer.parseInt(request.getParameter("companyId"));
            service.createComputer(
                    new Computer(id, name, introduced, discontinued, service.getCompany(companyId)));
        } catch (SQLException e) {
            logger.error("" + e);
            request.getRequestDispatcher("/views/500.html").forward(request, response);
        }
        request.getRequestDispatcher("/dashboard").forward(request, response);
    }
}
