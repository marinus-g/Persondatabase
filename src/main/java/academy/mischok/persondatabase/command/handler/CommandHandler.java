package academy.mischok.persondatabase.command.handler;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.CommandRegistry;
import academy.mischok.persondatabase.service.PersonService;

import java.util.Arrays;
import java.util.Scanner;

public class CommandHandler {

    private final Scanner scanner;
    private final CommandRegistry registry;
    private final PersonDatabase personDatabase;

    public CommandHandler(PersonService personService, PersonDatabase personDatabase) {
        this.personDatabase = personDatabase;
        this.registry = new CommandRegistry(personService, this.scanner = new Scanner(System.in), personDatabase);
        this.handleScanner();
    }

    private void handleScanner() {
        System.out.println("Gebe einen Befehl ein. Schreibe help, für eine Liste aller Befehle!");
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
        }
    }
}