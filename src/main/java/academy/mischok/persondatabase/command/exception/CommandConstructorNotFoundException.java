package academy.mischok.persondatabase.command.exception;

/**
 * The CommandConstructorNotFoundException class represents an exception that is thrown when a command constructor is not found.
 * It extends the Exception class to provide the functionality of an exception.
 */
public class CommandConstructorNotFoundException extends Exception {

    /**
     * Constructs a CommandConstructorNotFoundException object with the given message.
     *
     * @param message the message of the exception
     */
    public CommandConstructorNotFoundException(String message) {
        super(message);
    }
}