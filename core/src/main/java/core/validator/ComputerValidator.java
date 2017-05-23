package core.validator;

import core.model.Computer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by ebiz on 09/05/17.
 */
@Component
public class ComputerValidator implements Validator {
    /**
     * .
     * @param aClass .
     * @return .
     */
    @Override
    public boolean supports(Class aClass) {
        return Computer.class.equals(aClass);
    }

    /**
     * .
     * @param o .
     * @param errors .
     */
    @Override
    public void validate(Object o, Errors errors) {
        Computer computer = (Computer) o;
        if (computer.getName() == null) {
            errors.rejectValue("name", "name_null");
        }
        if (Validateur.validComputer(computer).length != 0) {
            errors.reject("invalid_computer");
        }
    }
}
