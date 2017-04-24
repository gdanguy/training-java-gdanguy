package controller;

import model.Page;
import model.computer.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import service.ComputerService;
import service.mappy.ComputerMapper;
import service.mappy.computer.ComputerDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "DashboardServlet", urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {
    public static final String DASHBOARD = "/dashboard";
    public static final String DASHBOARD_JSP = "/views/dashboard.jsp";
    public static final String ERROR_500_JSP = "/views/500.html";
    public static final String ADD_COMPUTER_JSP = "/views/addComputer.jsp";
    public static final String EDIT_COMPUTER_JSP = "/views/editComputer.jsp";
    public static final int NB_PAGINATION = 10;

    private static final String CURRENT_PAGE = "currentPage";
    private static final String SIZE_PAGE = "sizePages";
    private static final String START = "debut";
    private static final String END = "fin";
    private static final String COUNT = "countComputer";
    private static final String LIST = "listComputers";
    private static final String SEARCH = "search";
    public static final String ORDER = "order";
    public static final String ORDER_NULL = "null";
    public static final String ORDER_NAME_ASC = "name_a";
    public static final String ORDER_NAME_DESC = "name_d";
    public static final String ORDER_INTRO_ASC = "intro_a";
    public static final String ORDER_INTRO_DESC = "intro_b";
    public static final String ORDER_DISCO_ASC = "disco_a";
    public static final String ORDER_DISCO_DESC = "disco_b";
    public static final String ORDER_COMPANY_ASC = "company_a";
    public static final String ORDER_COMPANY_DESC = "company_b";

    private ComputerMapper computerMap = new ComputerMapper();
    @Autowired
    private ComputerService serviceComputer;

    /**
     * Init beans.
     * @param config the servlet config for spring
     * @throws ServletException if bug
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        this.serviceComputer = (ComputerService) ac.getBean("computerService");
    }


    /**
     * Set data for the Dashboard.
     * @param request  jsp request
     * @param response jsp response
     * @throws ServletException if bug
     * @throws IOException      if bug
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setDashboard(request);
        request.getRequestDispatcher(DASHBOARD_JSP).forward(request, response);
    }

    /**
     * Dashboard Search.
     * @param request  jsp request
     * @param response jsp response
     * @throws ServletException if bug
     * @throws IOException      if bug
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter(SEARCH);
        request.setAttribute(CURRENT_PAGE, 0);
        ArrayList<Computer> listComputer = serviceComputer.list(search).getListPage();
        request.setAttribute(START, 0);
        request.setAttribute(END, 0);
        request.setAttribute(COUNT, listComputer.size());
        request.setAttribute(SIZE_PAGE, listComputer.size());
        request.setAttribute(LIST, computerMap.toList(listComputer));
        request.setAttribute(ORDER, request.getParameter(ORDER));
        request.getRequestDispatcher(DASHBOARD_JSP).forward(request, response);
    }

    /**
     * Set all variable whose dashboard need.
     * @param request http request
     */
    private void setDashboard(HttpServletRequest request) {
        String cp = request.getParameter(CURRENT_PAGE);
        int currentPage = 0;
        if (cp != null && !cp.equals("")) {
            currentPage = Integer.parseInt(cp);
        }
        request.setAttribute(CURRENT_PAGE, currentPage);
        String size = request.getParameter(SIZE_PAGE);
        int sizePages = Page.PAGE_SIZE;
        if (size != null && !size.equals("")) {
            sizePages = Integer.parseInt(size);
        }
        int debut = 0;
        if (currentPage > NB_PAGINATION) {
            debut = currentPage - NB_PAGINATION;
        }
        int nbComputer = serviceComputer.count();
        int fin = nbComputer / sizePages;
        if (currentPage + NB_PAGINATION < fin) {
            fin = currentPage + NB_PAGINATION;
        } else if (currentPage > fin) {
            currentPage = fin;
        }
        String order = request.getParameter(ORDER);
        if (order == null) {
            order = ORDER_NULL;
            request.setAttribute(ORDER, ORDER_NULL);
        } else {
            request.setAttribute(ORDER, order);
        }
        ArrayList<ComputerDTO> list = new ArrayList<>(computerMap.toList(serviceComputer.list(currentPage, sizePages, order).getListPage()));

        request.setAttribute(START, debut);
        request.setAttribute(END, fin);
        request.setAttribute(COUNT, nbComputer);
        request.setAttribute(SIZE_PAGE, sizePages);
        request.setAttribute(LIST, list);
    }
}
