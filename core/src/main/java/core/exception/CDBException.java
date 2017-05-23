package core.exception;


public class CDBException extends RuntimeException {
    private CodeError error;
    /**
     * Constructor.
     * @param error the code of the Exception
     */
    public CDBException(CodeError error) {
        this.error = error;
    }

    public CodeError getError() {
        return error;
    }
}
