package exception;

import java.util.List;

/**
 * Created by ebiz on 16/05/17.
 */
public class ErrorParameter {
    private String codeErrorHtml;
    private List<CodeError> codeErrors;

    /**
     * Create a ErrorParamater use in ModelAndView.redirect.
     * @param codeErrorHtml the HTML code of the errors (500, 400, ...)
     * @param codeErrors List<CodeError>
     */
    public ErrorParameter(String codeErrorHtml, List<CodeError> codeErrors) {
        this.codeErrorHtml = codeErrorHtml;
        this.codeErrors = codeErrors;
    }

    public String getCodeErrorHtml() {
        return codeErrorHtml;
    }

    public List<CodeError> getCodeErrors() {
        return codeErrors;
    }
}
