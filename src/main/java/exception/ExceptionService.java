package exception;

/**
 * Created by ebiz on 15/05/17.
 */
public abstract class ExceptionService {
    /**
     * Get the message corresponding to the errorCode.
     * @param codeError CodeError
     * @return message
     */
    public static String get(CodeError codeError) {
        switch (codeError) {
            case COMPANY_CREATE:
                return "error.company.create";
            case COMPANY_DELETE:
                return "error.company.delete";
            case COMPUTER_COMPANY_ID_INVALID:
                return "error.computer.company.id.invalid";
            case COMPUTER_CREATE_BAD_PARAMETERS:
                return "error.computer.create.bad.parameters";
            case COMPUTER_IS_NULL:
                return "error.computer.is.null";
            case COMPUTER_EDIT:
                return "error.computer.edit";
            case COMPUTER_DELETE:
                return "error.computer.delete";
            case COMPUTER_NOT_FOUND:
                return "error.computer.not.found";
            default:
                return "error.default";
        }
    }
    /**
     * Get the message corresponding to the errorCode.
     * @param e DAOException
     * @return message
     */
    public static String get(DAOException e) {
        return get(e.getError());
    }
}
