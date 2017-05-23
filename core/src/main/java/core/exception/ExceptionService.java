package core.exception;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 15/05/17.
 */
public abstract class ExceptionService {
    public static final String ERROR_PARAMETER = "error";
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
            case COMPANY_NOT_FOUND:
                return "error.computer.company.id.invalid";
            case COMPUTER_DELETE_LIST_EMPTY:
                return "error.computer.delete.list.empty";
            case COMPUTER_DELETE_NULL_ID:
                return "error.computer.delete.ID.null";
            case COMPUTER_ID_INVALID:
                return "error.computer.id.invalid";
            case INVALID_PAGE:
                return "error.invalid.page";
            case INVALID_DATE:
                return "error.invalid.date";
            default:
                return "error.default";
        }
    }
    /**
     * Get the message corresponding to the errorCode.
     * @param e CDBException
     * @return message
     */
    public static String get(CDBException e) {
        return get(e.getError());
    }

    /**
     * Convert a List of code Errors on List of String.
     * @param codeErrors List<CodeError>
     * @return List<String>
     */
    public static List<String> get(List<CodeError> codeErrors) {
        ArrayList<String> result = new ArrayList<>();
        for (CodeError codeError : codeErrors) {
            result.add(get(codeError));
        }
        return result;
    }

    /**
     * Return redirection to the Errors Controller.
     * @param e .
     * @param htlmCode .
     * @param redirectAttributes .
     * @return .
     */
    public static ModelAndView redirect(CDBException e, String htlmCode, RedirectAttributes redirectAttributes) {
        List<CodeError> codeErrors = new ArrayList<>();
        codeErrors.add(e.getError());
        redirectAttributes.addFlashAttribute(ERROR_PARAMETER, new ErrorParameter(htlmCode, codeErrors));
        return new ModelAndView(new RedirectView("errors"));
    }
}
