package service.validator;

public abstract class Validator {
    public static final String REGEX_DATE = "^[0-3][0-9][-][0-1][0-9][-](([1][9][9][0-9])|([2-9][0-9]{3}))$";
    public static final String REGEX_NAME = "^[A-Za-z0-9 ]{0,39}[A-Za-z0-9]$";
    public static final String REGEX_INT = "^-?\\\\d+$";

    /**
     * Validator for Date.
     * @param date the date to validate
     * @throws ErrorValidateur if synthaxe problem
     */
    public static void dateValidate(String date) throws ErrorValidateur {
        if (date == null || date.equals("") || !(date.matches(REGEX_DATE))) {
            throw new ErrorValidateur("Error Date format, format expected : " + REGEX_DATE + " but actually : " + date);
        }
    }

    /**
     * Validator for Name.
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
     * Validator for int.
     * @param intToValidate the int to validate
     * @throws ErrorValidateur if synthaxe problem
     */
    public static void intValidator(String intToValidate) throws ErrorValidateur {
        if (intToValidate != null && !(intToValidate.matches(REGEX_INT))) {
            throw new ErrorValidateur("Error int format, format expected : " + REGEX_INT + " but actually : " + intToValidate);
        }
    }

    /**
     * Validator for int with null is a error.
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
}
