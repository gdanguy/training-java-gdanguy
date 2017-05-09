package controller;

import model.Page;
import model.computer.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.ComputerService;
import service.mappy.ComputerMapper;
import service.mappy.computer.ComputerDTO;

import java.util.ArrayList;

@Controller
public class DashboardController {
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
     * Set data for the Dashboard.
     * @param cp .
     * @param order .
     * @param size .
     * @param model ModelMap
     * @return String
     */
    @GetMapping(DASHBOARD)
    protected String doGet(@RequestParam(value = CURRENT_PAGE, required = false) String cp,
                           @RequestParam(value = SIZE_PAGE, required = false) String size,
                           @RequestParam(value = ORDER, required = false) String order,
                            ModelMap model) {
        int currentPage = 0;
        if (cp != null && !cp.equals("")) {
            currentPage = Integer.parseInt(cp);
        }
        model.addAttribute(CURRENT_PAGE, currentPage);
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
        if (order == null) {
            order = ORDER_NULL;
            model.addAttribute(ORDER, ORDER_NULL);
        } else {
            model.addAttribute(ORDER, order);
        }
        ArrayList<ComputerDTO> list = new ArrayList<>(computerMap.toList(serviceComputer.list(currentPage, sizePages, order).getListPage()));

        model.addAttribute(START, debut);
        model.addAttribute(END, fin);
        model.addAttribute(COUNT, nbComputer);
        model.addAttribute(SIZE_PAGE, sizePages);
        model.addAttribute(LIST, list);
        return "dashboard";
    }

    /**
     * Dashboard Search.
     * @param model ModelMap
     * @param order the order of the list
     * @param search the word research
     * @return String
     */
    @PostMapping(DASHBOARD)
    protected String doPost(@RequestParam(value = ORDER, required = false) String order,
                            @RequestParam(value = SEARCH) String search,
                            ModelMap model) {
        model.addAttribute(CURRENT_PAGE, 0);
        ArrayList<Computer> listComputer = serviceComputer.list(search).getListPage();
        model.addAttribute(START, 0);
        model.addAttribute(END, 0);
        model.addAttribute(COUNT, listComputer.size());
        model.addAttribute(SIZE_PAGE, listComputer.size());
        model.addAttribute(LIST, computerMap.toList(listComputer));
        model.addAttribute(ORDER, order);
        return "dashboard";
    }
}
