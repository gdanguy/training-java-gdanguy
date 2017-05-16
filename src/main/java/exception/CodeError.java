package exception;

/**
 * Created by ebiz on 15/05/17.
 */
public enum CodeError {
    //Exception ComputerDAO
    COMPUTER_NOT_FOUND,
    COMPUTER_CREATE_BAD_PARAMETERS,
    COMPUTER_IS_NULL,
    COMPUTER_EDIT,
    COMPUTER_DELETE,
    COMPUTER_DELETE_LIST_EMPTY,
    COMPUTER_DELETE_NULL_ID,
    COMPUTER_COMPANY_ID_INVALID,

    //Exception CompanyDAO
    COMPANY_CREATE,
    COMPANY_DELETE
}
