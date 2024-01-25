package academy.mischok.persondatabase.validator;

/**
 * The StringValidator interface provides a contract for validating strings.
 * Classes implementing this interface should provide their own implementation of the isValidString method.
 */
public interface StringValidator {

    /**
     * Checks if the provided string is valid according to some criteria.
     *
     * @param string the string to validate
     * @return true if the string is valid, false otherwise
     */
    boolean isValidString(final String string);

}