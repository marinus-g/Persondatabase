package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.ScannerUtil;

import java.util.Scanner;

/**
 * The DeleteCommand class represents a command that deletes a person in the application.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the delete command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "delete", description = "Deletes a Person", aliases = "d")
public class DeleteCommand extends AbstractCommand {

    /**
     * The PersonService to use for person-related operations.
     */
    private final PersonService personService;

    /**
     * Constructs a DeleteCommand object with the given PersonService.
     *
     * @param personService the PersonService to use for person-related operations
     */
    public DeleteCommand(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Handles the delete command with the given command, arguments, and Scanner for input.
     * The method prompts the user to confirm the deletion, and then deletes the person if confirmed.
     * If the argument is "all", the method deletes all persons after confirmation.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     * @param scanner the Scanner to use for input
     */
    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
            System.out.print("Möchtest du  wirklich alle Personen löschen? (ja/nein): ");
            if (!ScannerUtil.getConfirmation(scanner, "Möchtest du wirklich alle Person löschen? (ja/nein): ")) {
                System.out.println("Löschung abgebrochen!");
                return;
            }
            this.personService.deleteAll();
            System.out.println("Alle Personen wurden gelöscht!");
            return;
        }
        System.out.print("Bitte gib eine ID an: ");
        Long input = ScannerUtil.getLongFromScanner(scanner, "Bitte gib eine richtige ID an: ");
        this.personService.findPersonById(input).ifPresentOrElse(person -> {
            System.out.print("Möchtest du diese Person wirklich löschen? (ja/nein): ");
            if (!ScannerUtil.getConfirmation(scanner, "Möchtest du diese Person wirklich löschen? (ja/nein): ")) {
                System.out.println("Löschung abgebrochen!");
                return;
            }
            if (this.personService.deletePerson(person)) {
                System.out.println("Eintrag gelöscht!");
            } else {
                System.out.println("Konnte nicht gelöscht werden!");
            }
        }, () -> System.out.println("Person nicht gefunden!"));
    }
}