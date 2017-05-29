package rest.Exception;

import core.exception.CDBException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

/**
 * Created by ebiz on 29/05/17.
 */
@RestControllerAdvice
public class ExceptionRestController {

    /**
     * Handle InvalidRestParameterException.
     *
     * @param e e
     * @return e
     */
    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMsg handleInvalidParameterException(InvalidParameterException e) {
        return new ResponseMsg.Builder().type("Invalid parameter").message(e.getMessage()).build();
    }

    /**
     * Handle NoSuchElementException.
     *
     * @param e e
     * @return e
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMsg handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseMsg.Builder().type("No such element").message(e.getMessage()).build();
    }

    /**
     * Default Exception Handler.
     * @param e .
     * @return .
     */
    @ExceptionHandler(CDBException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMsg defaultRestException(CDBException e) {
        return new ResponseMsg.Builder().type("Persistence error").message(e.getMessage()).build();
    }
}
