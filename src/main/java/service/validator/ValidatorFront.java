package service.validator;

import model.computer.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public abstract class ValidatorFront {
    private static Logger logger = LoggerFactory.getLogger(ValidatorFront.class);
    public static final String REGEX_DATE = "^[0-3][0-9][-][0-1][0-9][-](([1][9][9][0-9])|([2-9][0-9]{3}))$";
    public static final String REGEX_NAME = "^[A-Za-z0-9 -]{0,39}[A-Za-z0-9]$";
    public static final String REGEX_INT = "^-?\\\\d+$";
    public static final String NAME_DEFAULT = "no name";

    /**
     * ValidatorFront for Date.
     * @param date the date to validate
     * @throws ErrorValidateur if synthaxe problem
     */
    public static void dateValidate(String date) throws ErrorValidateur {
        if (date == null || date.equals("") || !(date.matches(REGEX_DATE))) {
            throw new ErrorValidateur("Error Date format, format expected : " + REGEX_DATE + " but actually : " + date);
        }
    }

    /**
     * ValidatorFront for Name.
     * @param name the name to validate
     * @throws ErrorValidateur if synthaxe problem
     */
    public static void nameValidator(String name) throws ErrorValidateur {
        if (name != null) {
            if (!(name.matches(REGEX_NAME))) {
                throw new ErrorValidateur("Error Name format, format expected : " + REGEX_NAME + " but actually : " + name);
            }
        }
    }

    /**
     * ValidatorFront for int.
     * @param intToValidate the int to validate
     * @throws ErrorValidateur if synthaxe problem
     */
    public static void intValidator(String intToValidate) throws ErrorValidateur {
        if (intToValidate != null && !(intToValidate.matches(REGEX_INT))) {
            throw new ErrorValidateur("Error int format, format expected : " + REGEX_INT + " but actually : " + intToValidate);
        }
    }

    /**
     * ValidatorFront for int with null is a error.
     * @param intToValidate the int to validate
     * @throws ErrorValidateur if synthaxe problem
     */
    public static void intValidatorStrict(String intToValidate) throws ErrorValidateur {
        if (intToValidate == null || intToValidate.equals("")) {
            throw new ErrorValidateur("Error int format, null is not accepted");
        }
        if (!(intToValidate.matches(REGEX_INT))) {
            throw new ErrorValidateur("Error int format, format expected : " + REGEX_INT + " but actually : " + intToValidate);
        }
    }

    /**
     * If name is not valid return 'no name' else return name.
     * @param name String to validate
     * @return 'no name' or name
     */
    public static String getValidName(String name) {
        try {
            nameValidator(name);
            return name;
        } catch (ErrorValidateur e) {
            logger.warn(e.toString());
            return NAME_DEFAULT;
        }
    }

    /**
     * Convert user's input into LocalDateTime.
     * @param date user's input
     * @return LocalDateTime
     */
    public static LocalDateTime convertStringToLocalDateTime(String date) {
        try {
            dateValidate(date);
            return Computer.convertStringToLocalDateTime(date);
        } catch (ErrorValidateur e) {
            logger.warn(e.toString());
            return null;
        }
    }
}
