package controller;

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

@WebServlet(name = "DashboardServlet", urlPatterns = "/")
public class DashboardServlet extends HttpServlet {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    /**
     * Set data for the Dashboard.
     * @param request jsp request
     * @param response jsp response
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DAOService service = new DAOServiceImpl();
            request.setAttribute("countComputer", service.countComputers());
            request.setAttribute("listComputers", service.listComputers(0).getListPage());
        } catch (SQLException e) {
            logger.error("" + e);
        }
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

    }

    /**
     * Call doGet.
     * @param request jsp request
     * @param response jsp response
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
