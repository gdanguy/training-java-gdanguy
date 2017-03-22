package controller;

import model.Pages;
import model.computer.Computer;
import org.slf4j.LoggerFactory;
import service.dao.DAOService;
import service.dao.DAOServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "DashboardServlet", urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    private static final int NB_PAGINATION = 10;

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
                System.out.println("modifier sizePage = " + sizePages);
            }
            int debut = 0;
            if (currentPage > NB_PAGINATION) {
                debut = currentPage - NB_PAGINATION;
            }
            DAOService service = new DAOServiceImpl();
            int nbComputer = service.countComputers();
            int fin = nbComputer / sizePages;
            if (currentPage + NB_PAGINATION < fin) {
                fin = currentPage + NB_PAGINATION;
            } else if (currentPage > fin) {
                currentPage = fin;
            }
            request.setAttribute("debut", debut);
            request.setAttribute("fin", fin);
            request.setAttribute("countComputer", nbComputer);
            request.setAttribute("sizePages", sizePages);
            request.setAttribute("listComputers", service.convertComputerToComputerDTO(service.listComputers(currentPage, sizePages)).getListPage());
        } catch (SQLException e) {
            logger.error("" + e);
        }
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

    }

    /**
     * Search.
     * @param request jsp request
     * @param response jsp response
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String search = request.getParameter("search");
            if (search == null || search.equals("")) {
                doGet(request, response);
            }
            request.setAttribute("currentPage", 0);
            DAOService service = new DAOServiceImpl();
            ArrayList<Computer> listComputer = service.listComputers(search).getListPage();
            request.setAttribute("debut", 0);
            request.setAttribute("fin", 0);
            request.setAttribute("countComputer", listComputer.size());
            request.setAttribute("sizePages", listComputer.size());
            request.setAttribute("listComputers", service.convertComputerToComputerDTO(listComputer));
        } catch (SQLException e) {
            logger.error("" + e);
        }
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }
}
