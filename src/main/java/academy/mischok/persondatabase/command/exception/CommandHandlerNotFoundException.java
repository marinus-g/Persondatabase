package academy.mischok.persondatabase.command.exception;

public class CommandHandlerNotFoundException extends Exception {

    public CommandHandlerNotFoundException(String message) {
        super(message);
    }
}
