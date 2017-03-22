package service.validator;

public class ErrorValidateur extends java.lang.Exception {
    /**
     * Create Error.
     * @param message of the error
     */
    public ErrorValidateur(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param e Exception
     */
    public ErrorValidateur(Exception e) {
        super(e.getMessage(), e.getCause());
    }

}
