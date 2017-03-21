package controller;

import model.Pages;
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

@WebServlet(name = "DashboardServlet", urlPatterns = "/dashboard")
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
            String cp = request.getParameter("currentPage");
            int currentPage = 0;
            if (!(cp == null || cp.equals(""))) {
                currentPage = Integer.parseInt(cp);
            }
            request.setAttribute("currentPage", currentPage);
            String size = request.getParameter("sizePages");
            int sizePages = Pages.PAGE_SIZE;
            if (!(size == null || size.equals(""))) {
                sizePages = Integer.parseInt(size);
            }
            int debut = 0;
            if (currentPage > 2) {
                debut = currentPage - 2;
            }
            DAOService service = new DAOServiceImpl();
            int nbComputer = service.countComputers();
            int fin = nbComputer / sizePages;
            if (currentPage + 2 < fin) {
                fin = currentPage + 2;
            } else if (currentPage > fin) {
                currentPage = fin;
            }
            request.setAttribute("debut", debut);
            request.setAttribute("fin", fin);
            request.setAttribute("countComputer", nbComputer);
            request.setAttribute("sizePages", sizePages);
            request.setAttribute("listComputers", service.listComputers(currentPage, sizePages).getListPage());
        } catch (SQLException e) {
            logger.error("" + e);
            request.getRequestDispatcher("/views/500.html").forward(request, response);
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
