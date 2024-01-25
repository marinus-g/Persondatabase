package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;

/**
 * The ExitCommand class represents a command that ends the program.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the exit command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "exit", description = "Ends the program", aliases = "end")
public class ExitCommand extends AbstractCommand {

    /**
     * The PersonDatabase to use for database-related operations.
     */
    private final PersonDatabase personDatabase;

    /**
     * Constructs an ExitCommand object with the given PersonDatabase.
     *
     * @param personDatabase the PersonDatabase to use for database-related operations
     */
    public ExitCommand(PersonDatabase personDatabase) {
        this.personDatabase = personDatabase;
    }

    /**
     * Handles the exit command with the given command and arguments.
     * The method sets the running state of the PersonDatabase to false, effectively ending the program.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     */
    @Override
    public void handleCommand(String command, String[] args) {
        this.personDatabase.setRunning(false);
        System.out.println("Ended the program!");
    }
}