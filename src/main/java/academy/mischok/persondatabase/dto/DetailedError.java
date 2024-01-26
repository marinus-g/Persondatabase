package academy.mischok.persondatabase.dto;

/**
 * The DetailedError enum represents the types of detailed errors that can occur when creating or editing a person.
 * It includes values for errors related to the email, first name, last name, and date.
 */
public enum DetailedError {
    /**
     * Represents an error related to the email.
     */
    EMAIL,

    /**
     * Represents an error related to the first name.
     */
    FIRST_NAME,

    /**
     * Represents an error related to the last name.
     */
    LAST_NAME,

    /**
     * Represents an error related to the date.
     */
    DATE
}