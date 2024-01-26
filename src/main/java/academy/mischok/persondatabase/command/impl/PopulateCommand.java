package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.ScannerUtil;

import java.util.Scanner;

/**
 * The PopulateCommand class represents a command that populates the person list in the application.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the populate command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "populate", description = "Populates the person list")
public class PopulateCommand extends AbstractCommand {

    /**
     * The PersonService to use for person-related operations.
     */
    private final PersonService personService;

    /**
     * Constructs a PopulateCommand object with the given PersonService.
     *
     * @param personService the PersonService to use for person-related operations
     */
    public PopulateCommand(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Handles the populate command with the given command, arguments, and Scanner for input.
     * The method prompts the user to enter an amount, then adds that many persons to the person list.
     * Each person is created with default values for their properties.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     * @param scanner the Scanner to use for input
     */
    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        System.out.println();
        System.out.print("Gebe eine Anzahl an: ");
        final int amount = ScannerUtil.getIntegerFromScanner(scanner, "Gebe eine Anzahl an: ");
        for (int i = 0; i < amount; i++) {
            PersonDto personDto = new PersonDto();
            personDto.setFirstName("Max");
            personDto.setLastName("Mustermann");
            personDto.setBirthday("31.12.1980");
            personDto.setBonus(0);
            personDto.setSalary(0);
            personDto.setEmail("max@mustermann.com");
            personDto.setCountry("England");
            this.personService.addPerson(personDto);
        }
        System.out.println("Personen hinzugefügt.");
    }
}