package controller;

import core.exception.CDBException;
import core.exception.ExceptionService;
import core.model.Computer;
import core.utils.Constant;
import core.utils.Page;
import map.ComputerMapperDTO;
import map.computer.ComputerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.computer.ComputerService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private ComputerMapperDTO computerMap = new ComputerMapperDTO();

    @Autowired
    private ComputerService serviceComputer;

    /**
     * Set data for the Dashboard.
     * @param cp .
     * @param order .
     * @param size .
     * @param model ModelMap
     * @param errors ArrayList<CodeError>
     * @return String
     */
    @GetMapping
    protected String doGet(@RequestParam(value = Constant.CURRENT_PAGE, required = false) String cp,
                           @RequestParam(value = Constant.SIZE_PAGE, required = false) String size,
                           @RequestParam(value = Constant.ORDER, required = false) String order,
                           @RequestParam(value = Constant.MESSAGE_ERROR, required = false) ArrayList<String> errors,
                            ModelMap model) {
        int currentPage = 0;
        if (cp != null && !cp.isEmpty()) {
            currentPage = Integer.parseInt(cp);
        }
        model.addAttribute(Constant.CURRENT_PAGE, currentPage);
        int sizePages = Page.PAGE_SIZE;
        if (size != null && !size.isEmpty()) {
            sizePages = Integer.parseInt(size);
        }
        int debut = 0;
        if (currentPage > Constant.NB_PAGINATION) {
            debut = currentPage - Constant.NB_PAGINATION;
        }
        int nbComputer = serviceComputer.count();
        int fin = nbComputer / sizePages;
        if (currentPage + Constant.NB_PAGINATION < fin) {
            fin = currentPage + Constant.NB_PAGINATION;
        } else if (currentPage > fin) {
            currentPage = fin;
        }
        if (order == null) {
            order = Constant.ORDER_NULL;
            model.addAttribute(Constant.ORDER, Constant.ORDER_NULL);
        } else {
            model.addAttribute(Constant.ORDER, order);
        }
        ArrayList<ComputerDTO> list = null;
        try {
            list = new ArrayList<>(computerMap.toList(serviceComputer.list(currentPage, sizePages, order).getListPage()));
        } catch (CDBException e) {
            errors.add(ExceptionService.get(e));
        }

        model.addAttribute(Constant.MESSAGE_ERROR, errors);
        model.addAttribute(Constant.START, debut);
        model.addAttribute(Constant.END, fin);
        model.addAttribute(Constant.COUNT, nbComputer);
        model.addAttribute(Constant.SIZE_PAGE, sizePages);
        model.addAttribute(Constant.LIST_COMPUTERS, list);
        return "dashboard";
    }

    /**
     * Dashboard Search.
     * @param model ModelMap
     * @param order the order of the list
     * @param search the word research
     * @return String
     */
    @PostMapping
    protected String doPost(@RequestParam(value = Constant.ORDER, required = false) String order,
                            @RequestParam(value = Constant.SEARCH) String search,
                            ModelMap model) {
        model.addAttribute(Constant.CURRENT_PAGE, 0);
        List<Computer> list = null;
        try {
            list = serviceComputer.list(search).getListPage();
        } catch (CDBException e) {
            ArrayList<String> messageError = new ArrayList<>();
            messageError.add(ExceptionService.get(e));
            model.addAttribute(Constant.MESSAGE_ERROR, messageError);
        }
        model.addAttribute(Constant.START, 0);
        model.addAttribute(Constant.END, 0);
        model.addAttribute(Constant.COUNT, list.size());
        model.addAttribute(Constant.SIZE_PAGE, list.size());
        model.addAttribute(Constant.LIST_COMPUTERS, computerMap.toList(list));
        model.addAttribute(Constant.ORDER, order);
        return "dashboard";
    }
}
