package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.Utility;

import java.util.Scanner;

@Command(name = "delete", description = "Deletes a Person", aliases = "d")
public class DeleteCommand extends AbstractCommand {

    private final PersonService personService;

    public DeleteCommand(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
            System.out.print("Möchtest du  wirklich alle Personen löschen? (ja/nein): ");
            if (!Utility.getConfirmation(scanner, "Möchtest du wirklich alle Person löschen? (ja/nein): ")) {
                System.out.println("Löschung abgebrochen!");
                return;
            }
            this.personService.deleteAll();
            System.out.println("Alle Personen wurden gelöscht!");
            return;
        }
        System.out.print("Bitte gib eine ID an: ");
        Long input = Utility.getLongFromScanner(scanner, "Bitte gib eine richtige ID an: ");
        this.personService.findPersonById(input).ifPresentOrElse(person -> {
            System.out.print("Möchtest du diese Person wirklich löschen? (ja/nein): ");
            if (!Utility.getConfirmation(scanner, "Möchtest du diese Person wirklich löschen? (ja/nein): ")) {
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