package academy.mischok.persondatabase.command.exception;

public class CommandConstructorNotFoundException extends Exception {

    public CommandConstructorNotFoundException(String message) {
        super(message);
    }

}
