package academy.mischok.persondatabase.command.exception;

/**
 * The CommandHandlerNotFoundException class represents an exception that is thrown when a command handler is not found.
 * It extends the Exception class to provide the functionality of an exception.
 */
public class CommandHandlerNotFoundException extends Exception {

    /**
     * Constructs a CommandHandlerNotFoundException object with the given message.
     *
     * @param message the message of the exception
     */
    public CommandHandlerNotFoundException(String message) {
        super(message);
    }
}