package academy.mischok.persondatabase.command.handler;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.CommandRegistry;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The CommandHandler class is responsible for handling user input and executing the corresponding commands.
 * It uses a CommandRegistry to find and execute commands, and a Scanner to read user input.
 */
public class CommandHandler {

    /**
     * The Scanner to use for reading user input.
     */
    private final Scanner scanner;

    /**
     * The CommandRegistry to use for finding and executing commands.
     */
    private final CommandRegistry registry;

    /**
     * The PersonDatabase to use for database-related operations.
     */
    private final PersonDatabase personDatabase;


    /**
     * Constructs a CommandHandler object with the given PersonService, PersonDatabase, NameValidator, EmailValidator, and DateValidator.
     *
     * @param personService the PersonService to use for person-related operations
     * @param personDatabase the PersonDatabase to use for database-related operations
     * @param nameValidator the NameValidator to use for name validation
     * @param emailValidator the EmailValidator to use for email validation
     * @param dateValidator the DateValidator to use for date validation
     */
    public CommandHandler(PersonService personService, PersonDatabase personDatabase, NameValidator nameValidator,
                          EmailValidator emailValidator, DateValidator dateValidator) {
        this.personDatabase = personDatabase;
        this.scanner = new Scanner(System.in);
        this.registry = new CommandRegistry(personService, personDatabase, nameValidator, emailValidator, dateValidator);
        this.handleScanner();
    }


    /**
     * Handles user input by reading from the Scanner and executing the corresponding command.
     * The method continues to read and execute commands until the PersonDatabase is no longer running.
     */
    private void handleScanner() {
        System.out.println("Gebe einen Befehl ein. Schreibe help, für eine Liste aller Befehle!");
        System.out.print("> ");
        while (personDatabase.isRunning()) {
            final String str = this.scanner.nextLine();
            if (str == null || str.isBlank()) {
                continue;
            }
            final String[] splitted = str.split(" ");
            final String command = splitted[0];
            final String[] args = splitted.length == 1 ? new String[0] : Arrays.copyOfRange(splitted, 1, splitted.length);
            this.registry.findCommand(command)
                    .ifPresentOrElse(internalCommand -> internalCommand.execute(command, args, this.scanner),
                            () -> System.out.println("Command not found! Type help for a list of commands!"));
            System.out.print("> ");
        }
    }
}