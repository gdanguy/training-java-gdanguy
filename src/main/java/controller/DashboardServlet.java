package controller;

import model.Page;
import model.computer.Computer;
import org.slf4j.LoggerFactory;
import service.ComputerService;
import service.mappy.ComputerMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        String cp = request.getParameter("currentPage");
        int currentPage = 0;
        if (!(cp == null || cp.equals(""))) {
            currentPage = Integer.parseInt(cp);
        }
        request.setAttribute("currentPage", currentPage);
        String size = request.getParameter("sizePages");
        int sizePages = Page.PAGE_SIZE;
        if (!(size == null || size.equals(""))) {
            sizePages = Integer.parseInt(size);
        }
        int debut = 0;
        if (currentPage > NB_PAGINATION) {
            debut = currentPage - NB_PAGINATION;
        }
        ComputerService serviceComputer = ComputerService.getInstance();
        int nbComputer = serviceComputer.count();
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
        ComputerMapper computerMap = ComputerMapper.getInstance();
        request.setAttribute("listComputers", computerMap.toDTO(serviceComputer.list(currentPage, sizePages)).getListPage());

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
        String search = request.getParameter("search");
        if (search == null || search.equals("")) {
            doGet(request, response);
        }
        request.setAttribute("currentPage", 0);
        ComputerService serviceDAO = ComputerService.getInstance();
        ArrayList<Computer> listComputer = serviceDAO.list(search).getListPage();
        request.setAttribute("debut", 0);
        request.setAttribute("fin", 0);
        request.setAttribute("countComputer", listComputer.size());
        request.setAttribute("sizePages", listComputer.size());
        ComputerMapper computerMap = ComputerMapper.getInstance();
        request.setAttribute("listComputers", computerMap.toDTO(listComputer));
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }
}
