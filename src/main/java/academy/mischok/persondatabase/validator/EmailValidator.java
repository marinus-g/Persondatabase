package academy.mischok.persondatabase.validator;

/**
 * The EmailValidator class implements the StringValidator interface.
 * It provides a specific implementation of the isValidString method for validating email addresses.
 */
public class EmailValidator implements StringValidator {

    /**
     * Checks if the provided string is a valid email address.
     * A valid email address matches the pattern "^[a-zA-Z0-9._%+-]+@[a-zA-Z.-]+\\.[a-zA-Z]{2,6}$".
     *
     * @param string the email address to validate
     * @return true if the email address is valid, false otherwise
     */
    @Override
    public boolean isValidString(String string) {
        return string.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z.-]+\\.[a-zA-Z]{2,6}$");
    }
}