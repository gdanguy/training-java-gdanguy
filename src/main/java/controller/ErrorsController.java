package controller;

import exception.ErrorParameter;
import exception.ExceptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/errors")
public class ErrorsController {
    public static final String ERROR_PARAMETER = "error";
    /**
     * Error controller.
     * @param model Model
     * @return errorPage
     */
    @GetMapping
    public String renderErrorPage(Model model) {
        ErrorParameter errorParameter = (ErrorParameter) model.asMap().get(ERROR_PARAMETER);
        List<String> errors = ExceptionService.get(errorParameter.getCodeErrors());
        model.addAttribute("messageError", errors);
        return errorParameter.getCodeErrorHtml();
    }
}