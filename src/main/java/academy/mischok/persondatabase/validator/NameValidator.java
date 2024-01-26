package academy.mischok.persondatabase.validator;

/**
 * The NameValidator class implements the StringValidator interface.
 * It provides a specific implementation of the isValidString method for validating names.
 */
public class NameValidator implements StringValidator {

    /**
     * Checks if the provided string is a valid name.
     * A valid name starts with an uppercase letter followed by 1 to 99 lowercase letters.
     *
     * @param string the name to validate
     * @return true if the name is valid, false otherwise
     */
    @Override
    public boolean isValidString(String string) {
        return string.matches("^([A-ZÄÖÜ])([a-zßäöü]{1,99})$");
    }
}