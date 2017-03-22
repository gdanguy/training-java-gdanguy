package controller;


import model.computer.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.validator.ErrorValidateur;
import service.validator.Validator;

import javax.servlet.http.HttpServlet;
import java.time.LocalDateTime;

public abstract class UpdateComputerServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(UpdateComputerServlet.class);
    /**
     * Convert user's input into LocalDateTime.
     * @param date user's input
     * @return LocalDateTime
     */
    protected LocalDateTime convertStringToLocalDateTime(String date) {
        try {
            Validator.dateValidate(date);
            return Computer.convertStringToLocalDateTime(date);
        } catch (ErrorValidateur e) {
            logger.warn(e.toString());
            return null;
        }
    }
}
