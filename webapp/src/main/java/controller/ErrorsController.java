package controller;

import core.exception.CodeError;
import core.exception.ErrorParameter;
import core.exception.ExceptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/errors")
public class ErrorsController {

    /**
     * Error controller.
     * @param model Model
     * @return errorPage
     */
    @GetMapping
    public String renderErrorPage(Model model) {
        ErrorParameter errorParameter = (ErrorParameter) model.asMap().get(ExceptionService.ERROR_PARAMETER);
        List<String> errors;
        try {
            errors = ExceptionService.get(errorParameter.getCodeErrors());
        } catch (NullPointerException e) {
            errors = new ArrayList<>();
            errors.add(ExceptionService.get(CodeError.DEFAULT));
        }
        model.addAttribute("messageError", errors);
        if (errorParameter != null) {
            return errorParameter.getCodeErrorHtml();
        }
        return "404";
    }
}