package academy.mischok.persondatabase.command.handler;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.CommandRegistry;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;

import java.util.Arrays;
import java.util.Scanner;

public class CommandHandler {

    private final Scanner scanner;
    private final CommandRegistry registry;
    private final PersonDatabase personDatabase;

    public CommandHandler(PersonService personService, PersonDatabase personDatabase, NameValidator nameValidator,
                          EmailValidator emailValidator, DateValidator dateValidator) {
        this.personDatabase = personDatabase;
        this.scanner = new Scanner(System.in);
        this.registry = new CommandRegistry(personService, personDatabase, nameValidator, emailValidator, dateValidator);
        this.handleScanner();
    }

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