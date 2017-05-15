package exception;


public class DAOException extends RuntimeException {
    private CodeError error;
    /**
     * Constructor.
     * @param error the code of the Exception
     */
    public DAOException(CodeError error) {
        this.error = error;
    }

    public CodeError getError() {
        return error;
    }
}
