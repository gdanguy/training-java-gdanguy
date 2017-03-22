package service.validator;

public class ErrorValidateur extends java.lang.Exception {
    private String message;
    /**
     * Create Error.
     * @param message of the error
     */
    public ErrorValidateur(String message) {
        this.message = message;
    }

    /**
     * To String.
     * @return String
     */
    @Override
    public String toString() {
        return message;
    }
}
