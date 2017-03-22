package controller;

import model.dao.DAOException;
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

@WebServlet(name = "DeleteComputerServlet", urlPatterns = "/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);
    /**
     * Delete selected computer.
     * @param request r
     * @param response r
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOService service = new DAOServiceImpl();
        String[] computerToDelete = request.getParameter("selection").split(",");
        try {
            for (int i = 0; i < computerToDelete.length; i++) {
                service.deleteComputer(Integer.parseInt(computerToDelete[i]));
            }
            request.getRequestDispatcher("/dashboard").forward(request, response);
        } catch (DAOException e) {
            logger.error(e.toString());
            request.getRequestDispatcher("/views/500.html").forward(request, response);
        }
    }

}
