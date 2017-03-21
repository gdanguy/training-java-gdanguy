package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteComputerServlet", urlPatterns = "/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
    /**
     * r.
     * @param request r
     * @param response r
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * r.
     * @param request r
     * @param response r
     * @throws ServletException if bug
     * @throws IOException if bug
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/").forward(request, response);
    }
}
