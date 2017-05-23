package core.validator;

import core.model.Company;
import core.model.Computer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Validateur {
    public static final String REGEX_DATE = "^(([1][9][9][0-9])|([2-9][0-9]{3}))[-](([0][1-9])|([1][0-2]))[-](([0][1-9])|([1-2][0-9])|([3][0-1]))$";
    public static final String REGEX_DATE_2 = "^(([0][1-9])|([1-2][0-9])|([3][0-1]))[-](([0][1-9])|([1][0-2]))[-](([1][9][9][0-9])|([2-9][0-9]{3}))$";
    public static final String REGEX_NAME = "^[A-Za-z0-9 -]{0,38}[A-Za-z0-9]$";
    public static final String REGEX_INT = "^-?[0-9]*$";
    public static final String NAME_DEFAULT = "no name";
    public static final int ECHEC_FLAG = -1;

    /**
     * Validateur for Date.
     * @param date the date to validate
     * @return true if valid, false else
     */
    public static String dateValidate(String date) {
        if (date != null && !date.equals("") && (!date.matches(REGEX_DATE) && !date.matches(REGEX_DATE_2))) {
            return "Date Invalid";
        }
        return null;
    }

    /**
     * Validateur for Name.
     * @param name the name to validate
     * @return true if valid, false else
     */
    public static String nameValidator(String name) {
        if (name == null || name.isEmpty()) {
            return "Name Invalid";
        }
        return null;
    }

    /**
     * Validateur for int.
     * @param intToValidate the int to validate
     * @return true if valid, false else
     */
    public static String intValidator(String intToValidate) {
        if (intToValidate != null && !intToValidate.isEmpty() && !(intToValidate.matches(REGEX_INT))) {
            return "Number invalid";
        }
        return null;
    }

    /**
     * Validateur for int with null is a error.
     * @param intToValidate the int to validate
     * @return true if valid, false else
     */
    public static List<String> intValidatorStrict(String intToValidate) {
        ArrayList<String> result = new ArrayList<>();
        if (intToValidate == null || intToValidate.equals("")) {
            result.add("Number empty");
        }
        if (!(intToValidate.matches(REGEX_INT))) {
            result.add("Invalid Number");
        }
        return result;
    }

    /**
     * Validateur for int with null and negative is a error.
     * @param intToValidate the int to validate
     * @return true if valid, false else
     */
    public static String intValidatorStrict(Integer intToValidate) {
        if (intToValidate == null || intToValidate < 0) {
            return "Invalid Number";
        }
        return null;
    }

    /**
     * If name is not valid return 'no name' else return name.
     * @param name String to validate
     * @return 'no name' or name
     */
    public static String getValidName(String name) {
        if (nameValidator(name) == null) {
            return name;
        }
        return NAME_DEFAULT;
    }

    /**
     * Convert user's input into LocalDateTime.
     * @param date user's input
     * @return LocalDateTime .
     */
    public static LocalDateTime parseString(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        if (dateValidate(date) == null) {
            if (date.matches(REGEX_DATE)) {
                return Computer.toLocalDateTime(date);
            } else {
                return Computer.toLocalDateTime2(date);
            }
        }
        return null;
    }

    /**
     * Validateur for model.Company syntax.
     * @param c the model.Computer to validate
     * @return null if valid, String[2] else
     */
    public static String validCompany(Company c) {
        if (c == null) {
            return null;
        }
        String messageError = nameValidator(c.getName());
        if (messageError == null) {
            return null;
        }
        return messageError;
    }

    /**
     * Validateur Strict for model.Company syntax.
     * @param c the model.Computer to validate
     * @return null if valid, String[2] else
     */
    public static String validCompanyStrict(Company c) {
        if (c == null) {
            return "model.Company null";
        }
        String messageError = nameValidator(c.getName());
        if (messageError == null) {
            return null;
        }
        return messageError;
    }

    /**
     * Validateur for model.Computer syntax.
     * @param c the model.Computer to validate
     * @return null if valid, String[] else
     */
    public static String[] validComputer(Computer c) {
        if (c == null) {
            return null;
        }
        String messageErrorCompany = validCompany(c.getCompany());
        String[] messageError = new String[2];
        messageError[0] = nameValidator(c.getName());
        if (c.getId() != ECHEC_FLAG) {
            messageError[1] = intValidatorStrict(c.getId());
        }
        if (messageError[0] == null && messageError[1] == null && (messageErrorCompany == null || messageErrorCompany.isEmpty())) {
            return null;
        }
        if (messageErrorCompany != null) {
            String[] messageError2 = new String[3];
            messageError2[0] = messageError[0];
            messageError2[1] = messageError[1];
            messageError2[2] = messageErrorCompany;
            return messageError2;
        }
        return messageError;
    }

    /**
     * Test if introduced <= discontinued.
     * @param introduced   of the model.Computer in DataBase
     * @param discontinued of the model.Computer in DataBase
     * @return null if valid, String else
     */
    public static String testDate(LocalDateTime introduced, LocalDateTime discontinued) {
        if (!(introduced == null && discontinued == null)) {
            if ((introduced == null && discontinued != null) || (introduced != null && discontinued != null && (Timestamp.valueOf(introduced).after(Timestamp.valueOf(discontinued))))) {
                return "Introduced is after Discontinued but it must be before";
            }
        }
        return null;
    }

    /**
     * Return null if companyId if valid or null or equals(""), else return error message.
     * @param compId Id to validate
     * @return Return null if companyId if valid or null or equals(""), else return error message
     */
    public static String companyidValidate(String compId) {
        if (compId != null && !compId.equals("") && !compId.equals("" + ECHEC_FLAG)) {
            return intValidator(compId);
        }
        return null;
    }
}
