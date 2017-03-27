package service.validator;

import model.company.Company;
import model.computer.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public abstract class Validator {
    private static Logger logger = LoggerFactory.getLogger(Validator.class);
    public static final String REGEX_DATE = "^(([1][9][9][0-9])|([2-9][0-9]{3}))[-][0-1][0-9][-][0-3][0-9]$";
    public static final String REGEX_NAME = "^[A-Za-z0-9 -]{0,39}[A-Za-z0-9]$";
    public static final String REGEX_INT = "^-?\\\\d+$";
    public static final String NAME_DEFAULT = "no name";

    /**
     * Validator for Date.
     * @param date the date to validate
     * @return true if valid, false else
     */
    public static boolean dateValidate(String date) {
        if (date == null || date.equals("") || !(date.matches(REGEX_DATE))) {
            return false;
        }
        return true;
    }

    /**
     * Validator for Name.
     * @param name the name to validate
     * @return true if valid, false else
     */
    public static boolean nameValidator(String name) {
        if (name != null) {
            if (!(name.matches(REGEX_NAME))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validator for int.
     * @param intToValidate the int to validate
     * @return true if valid, false else
     */
    public static boolean intValidator(String intToValidate) {
        if (intToValidate != null && !(intToValidate.matches(REGEX_INT))) {
            return false;
        }
        return true;
    }

    /**
     * Validator for int with null is a error.
     * @param intToValidate the int to validate
     * @return true if valid, false else
     */
    public static boolean intValidatorStrict(String intToValidate) {
        if (intToValidate == null || intToValidate.equals("")) {
            return false;
        }
        if (!(intToValidate.matches(REGEX_INT))) {
            return false;
        }
        return true;
    }

    /**
     * Validator for int with null and negative is a error.
     * @param intToValidate the int to validate
     * @return true if valid, false else
     */
    public static boolean intValidatorStrict(Integer intToValidate) {
        if (intToValidate == null || intToValidate < 0) {
            return false;
        }
        return true;
    }

    /**
     * If name is not valid return 'no name' else return name.
     * @param name String to validate
     * @return 'no name' or name
     */
    public static String getValidName(String name) {
        if (nameValidator(name)) {
            return name;
        }
        return NAME_DEFAULT;
    }

    /**
     * Convert user's input into LocalDateTime.
     * @param date user's input
     * @return LocalDateTime
     */
    public static LocalDateTime parseString(String date) {
        if (dateValidate(date)) {
            return Computer.convertStringToLocalDateTime(date);
        }
        return null;
    }

    /**
     * Validator for Company syntax.
     * @param c the Computer to validate
     * @return true if valid, false else
     */
    public static boolean validCompany(Company c) {
        if (!(nameValidator(c.getName()) && intValidatorStrict(c.getId()))) {
            return false;
        }
        return true;
    }

    /**
     * Validator for Computer syntax.
     * @param c the Computer to validate
     * @return true if valid, false else
     */
    public static boolean validComputer(Computer c) {
        if (!(nameValidator(c.getName()) && intValidatorStrict(c.getId()) && validCompany(c.getCompany()))) {
            return false;
        }
        return true;
    }
}
