package controller;

import exception.CDBException;
import exception.ExceptionService;
import model.ComputerValidator;
import model.GenericBuilder;
import model.company.Company;
import model.computer.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import service.CompanyService;
import service.ComputerService;
import service.mappy.CompanyMapper;
import service.mappy.ComputerMapper;
import service.mappy.computer.ComputerDTO;
import service.validator.Validateur;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ebiz on 04/05/17.
 */
@Controller
public class ComputerController {
    private static final String LIST_COMPANIES_ATTRIBUTE = "listCompany";
    private static Logger logger = LoggerFactory.getLogger(ComputerController.class);
    private static final String SELECT = "selection";
    private static final String MESSAGE_ERROR = "messageError";
    private static final String ID = "id";
    private static final String COMPUTER = "computer";
    private static final String LIST = "listCompany";
    private static final String NAME = "computerName";
    private static final String INTRO = "introduced";
    private static final String DISCO = "discontinued";
    private static final String COMPANY_ID = "companyId";
    private CompanyMapper companyMap = new CompanyMapper();
    private ComputerMapper computerMap = new ComputerMapper();
    @Autowired
    private CompanyService serviceCompany;
    @Autowired
    private ComputerService serviceComputer;
    @Autowired
    private ComputerValidator computerValidator;

    /**
     * Forward the addComputer jsp.
     * @param model ModelMap
     * @param redirectAttributes RedirectAttributes
     * @return String
     */
    @GetMapping("/addComputer")
    protected ModelAndView addComputerGet(ModelMap model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute(LIST_COMPANIES_ATTRIBUTE, companyMap.toList(serviceCompany.listAll()));
        } catch (CDBException e) {
            return ExceptionService.redirect(e, "404", redirectAttributes);
        }
        ModelAndView redirect = new ModelAndView();
        redirect.setViewName("addComputer");
        return redirect;
    }

    /**
     * Create a computer in DataBase.
     * @param model ModelMap
     * @param name String
     * @param intro String
     * @param disco String
     * @param compId String
     * @param redirectAttributes RedirectAttributes
     * @return String
     */
    @PostMapping("/addComputer")
    protected ModelAndView addComputerPost(@RequestParam(value = NAME) String name,
                                         @RequestParam(value = INTRO) String intro,
                                         @RequestParam(value = DISCO) String disco,
                                         @RequestParam(value = COMPANY_ID) String compId,
                                           RedirectAttributes redirectAttributes,
                                         ModelMap model) {
        ArrayList<String> errors = null;
        Computer input;
        try {
            input = getComputerNoStrict(name, intro, disco, compId, errors);
        } catch (CDBException e) {
            return ExceptionService.redirect(e, "500", redirectAttributes);
        }
        if (errors != null && errors.size() != 0) {
            model.addAttribute(MESSAGE_ERROR, errors);
        }
        if (input == null) {
            ModelAndView redirect = new ModelAndView();
            redirect.setViewName("addComputer");
            return redirect;
        }
        try {
            serviceComputer.create(input);
        } catch (CDBException e) {
            return ExceptionService.redirect(e, "500", redirectAttributes);
        }
        return new ModelAndView(new RedirectView("dashboard"));
    }

    /**
     * Delete selected computer.
     * @param model  ModelMap
     * @param toDelete  String
     * @param redirectAttributes RedirectAttributes
     * @return String
     */
    @PostMapping("/deleteComputer")
    protected ModelAndView deleteComputer(@RequestParam(value = SELECT) String toDelete,
                                    RedirectAttributes redirectAttributes,
                                    ModelMap model) {
        String[] computerToDelete = toDelete.split(",");
        ArrayList<Integer> listId = new ArrayList<>();
        for (int i = 0; i < computerToDelete.length; i++) {
            if (Validateur.intValidatorStrict(computerToDelete[i]).size() > 0) {
                model.addAttribute(MESSAGE_ERROR, "Invalid selection");
                return new ModelAndView(new RedirectView("dashboard"));
            }
            listId.add(Integer.parseInt(computerToDelete[i]));
        }
        try {
            serviceComputer.delete(listId);
        } catch (CDBException e) {
            return ExceptionService.redirect(e, "403", redirectAttributes);
        }
        return new ModelAndView(new RedirectView("dashboard"));
    }

    /**
     * Get the id of the computer wanted et set the computer to the jsp.
     * @param model ModelMap
     * @param idComputer int
     * @return String
     */
    @GetMapping("/editComputer")
    protected String editCompuer(@RequestParam(value = ID) String idComputer,
            ModelMap model) {
        int id = Integer.parseInt(idComputer);
        model.addAttribute(ID, id);
        Computer c = serviceComputer.get(id);
        model.addAttribute("form", new ComputerDTO());
        model.addAttribute(COMPUTER, GenericBuilder.of(ComputerDTO::new)
                .with(ComputerDTO::setId, id)
                .with(ComputerDTO::setName, c.getName())
                .with(ComputerDTO::setIntroduced, c.getIntroduced())
                .with(ComputerDTO::setDiscontinued, c.getDiscontinued())
                .with(ComputerDTO::setCompany, c.getCompany())
                .build());
        model.addAttribute(LIST, companyMap.toList(serviceCompany.listAll()));
        return "editComputer";
    }


    /**
     * Update a computer.
     * @param model ModelMap
     * @param computerDTO computerDto
     * @param bindingResult BindingResult
     * @param redirectAttributes RedirectAttributes
     * @return String
     */
    @PostMapping("/editComputer")
    protected ModelAndView editComputerPost(ComputerDTO computerDTO,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            ModelMap model) {
        computerValidator.validate(computerMap.from(computerDTO), bindingResult);
        if (bindingResult.hasErrors()) {
            String errorString = "";
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorString += error.getObjectName() + " - " + error.getDefaultMessage() + "<br/>";
            }
            model.addAttribute("messageError", "Error creating computer, invalid param : " + errorString);
            return new ModelAndView(new RedirectView("dashboard"));
        }
        try {
            serviceComputer.update(getComputerModified(computerDTO.getId(), computerDTO.getName(), computerDTO.getIntroduced(), computerDTO.getDiscontinued(), ("" + computerDTO.getCompanyId())));
        } catch (CDBException e) {
            return ExceptionService.redirect(e, "500", redirectAttributes);
        }
        return new ModelAndView(new RedirectView("dashboard"));
    }

    /**
     * Get the computer set by the user.
     * @param name String
     * @param intro String
     * @param disco String
     * @param compId String
     * @param errors modified with a new ArrayList of errors
     * @return the computer wanted
     */
    private Computer getComputerNoStrict(String name, String intro, String disco, String compId, ArrayList<String> errors) {
        return getComputer(name, intro, disco, compId, false, errors);
    }

    /**
     * Get the computer set by the user without null.
     * @param name String
     * @param intro String
     * @param disco String
     * @param compId String
     * @param errors modified with a new ArrayList of errors
     * @return the computer wanted
     */
    private Computer getComputer(String name, String intro, String disco, String compId, ArrayList<String> errors) {
        return getComputer(name, intro, disco, compId, true, errors);
    }

    /**
     * Get the computer set by the user.
     * @param strict if strict of not
     * @param name String
     * @param intro String
     * @param disco String
     * @param compId String
     * @param errors modified with a new ArrayList of errors
     * @return the computer wanted
     */
    private Computer getComputer(String name, String intro, String disco, String compId, boolean strict, ArrayList<String> errors) {
        int id = CompanyService.ECHEC_FLAG;
        ArrayList<String> messageError = new ArrayList<>();
        LocalDateTime introduced = Validateur.parseString(intro);
        LocalDateTime discontinued = Validateur.parseString(disco);
        if (strict) {
            String resultError;

            //Test of introduced
            resultError = Validateur.dateValidate(intro);
            setError(messageError, resultError);

            //Test of discontinued
            resultError = Validateur.dateValidate(disco);
            setError(messageError, resultError);

            //Test of Company id
            resultError = Validateur.companyidValidate(compId);
            setError(messageError, resultError);

            //test date
            resultError = Validateur.testDate(introduced, discontinued);
            setError(messageError, resultError);

            //set message error
            if (messageError.size() != 0) {
                errors = messageError;
                //request.setAttribute(MESSAGE_ERROR, messageError);
                return null;
            }
        }
        int companyId = Integer.parseInt(compId);
        return GenericBuilder.of(Computer::new)
                .with(Computer::setId, id)
                .with(Computer::setName, name)
                .with(Computer::setIntroduced, introduced)
                .with(Computer::setDiscontinued, discontinued)
                .with(Computer::setCompany, serviceCompany.get(companyId))
                .build();
    }

    /**
     * If error != null, add it to massageError.
     * @param messageError list of error
     * @param error string
     */
    private void setError(List messageError, String error) {
        if (error != null) {
            logger.warn(error);
            messageError.add(error);
        }
    }

    /**
     * Get the computer set by the user, if user don't set value, keep older.
     * @param id int
     * @param nameModified String
     * @param introModified String
     * @param discoModified String
     * @param compIdModified String
     * @return the computer wanted
     */
    protected Computer getComputerModified(int id, String nameModified, String introModified, String discoModified, String compIdModified) {
        Computer userInsert = getComputer(nameModified, introModified, discoModified, compIdModified, null);
        Computer old = serviceComputer.get(id);
        boolean notModified = true;
        String name = userInsert.getName();
        LocalDateTime introduced = userInsert.getIntroduced();
        LocalDateTime discontinued = userInsert.getDiscontinued();
        Company company = userInsert.getCompany();
        if (introduced == null) {
            introduced = old.getIntroduced();
            notModified = false;
        }
        if (discontinued == null) {
            discontinued = old.getDiscontinued();
            notModified = false;
        }
        if (notModified) {
            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, id)
                    .with(Computer::setName, userInsert.getName())
                    .with(Computer::setIntroduced, userInsert.getIntroduced())
                    .with(Computer::setDiscontinued, userInsert.getDiscontinued())
                    .with(Computer::setCompany, userInsert.getCompany())
                    .build();
        } else {
            return GenericBuilder.of(Computer::new)
                    .with(Computer::setId, id)
                    .with(Computer::setName, name)
                    .with(Computer::setIntroduced, introduced)
                    .with(Computer::setDiscontinued, discontinued)
                    .with(Computer::setCompany, company)
                    .build();
        }
    }
}
