package controller;

import service.ComputerService;
import service.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "DeleteComputerServlet", urlPatterns = "/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
    private static final String SELECT = "selection";

    /**
     * Delete selected computer.
     * @param request  r
     * @param response r
     * @throws ServletException if bug
     * @throws IOException      if bug
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerService service = ComputerService.getInstance();
        String[] computerToDelete = request.getParameter(SELECT).split(",");
        ArrayList<Integer> listId = new ArrayList<>();
        for (int i = 0; i < computerToDelete.length; i++) {
            if (Validator.intValidatorStrict(computerToDelete[i]).size() > 0) {
                request.setAttribute(UpdateComputerServlet.MESSAGE_ERROR, "Invalid selection");
                request.getRequestDispatcher(DashboardServlet.DASHBOARD).forward(request, response);
            }
            listId.add(Integer.parseInt(computerToDelete[i]));
        }
        service.delete(listId);
        response.sendRedirect(DashboardServlet.DASHBOARD);
    }

}
