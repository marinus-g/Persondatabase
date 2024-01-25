package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.dto.PersonCreateResponse;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.ScannerUtil;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;

import java.util.Scanner;

/**
 * The CreateCommand class represents a command that creates a new person in the application.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the create command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "create", description = "Creates a new person", aliases = {"add", "a"})
public class CreateCommand extends AbstractCommand {

    /**
     * The PersonService to use for person-related operations.
     */
    private final PersonService personService;

    /**
     * The NameValidator to use for name validation.
     */
    private final NameValidator nameValidator;

    /**
     * The EmailValidator to use for email validation.
     */
    private final EmailValidator emailValidator;

    /**
     * The DateValidator to use for date validation.
     */
    private final DateValidator dateValidator;

    /**
     * Constructs a CreateCommand object with the given PersonService, NameValidator, EmailValidator, and DateValidator.
     *
     * @param personService the PersonService to use for person-related operations
     * @param nameValidator the NameValidator to use for name validation
     * @param emailValidator the EmailValidator to use for email validation
     * @param dateValidator the DateValidator to use for date validation
     */
    public CreateCommand(PersonService personService, NameValidator nameValidator,
                         EmailValidator emailValidator, DateValidator dateValidator) {
        this.personService = personService;
        this.nameValidator = nameValidator;
        this.emailValidator = emailValidator;
        this.dateValidator = dateValidator;
    }

    /**
     * Handles the create command with the given command, arguments, and Scanner for input.
     * The method prompts the user to enter the details of the person to create, and then creates the person.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     * @param scanner the Scanner to use for input
     */
    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {

        final PersonDto personDto = new PersonDto();

        personDto.setFirstName(ScannerUtil.getValidString(scanner, nameValidator, "Gib einen validen Vornamen ein: "));
        personDto.setLastName(ScannerUtil.getValidString(scanner, nameValidator, "Gib einen validen Nachnamen ein: "));
        personDto.setEmail(ScannerUtil.getValidString(scanner, emailValidator, "Gib eine validen Email-Adresse ein: "));
        System.out.print("Gib ein Land an (Lasse leer, für keins): ");
        String input = scanner.nextLine();
        personDto.setCountry(input.isBlank() ? null : input);
        personDto.setBirthday(ScannerUtil.getValidString(scanner, dateValidator, "Gib ein valides Geburtsdatum an: "));
        System.out.print("Gib ein Gehalt an: ");
        personDto.setSalary(ScannerUtil.getIntegerFromScanner(scanner, "Gib ein Gehalt an: "));
        System.out.print("Gib einen Bonus an: (Lasse leer, für kein Gehalt): ");
        personDto.setBonus(ScannerUtil.getIntegerFromScanner(scanner, null, true));
        PersonCreateResponse createResponse = this.personService.addPerson(personDto);
        if (!createResponse.isCreated()) {
            //noinspection OptionalGetWithoutIsPresent
            System.out.println(createResponse.getErrorMessage().get());
            System.out.println("Gebe \"" + command + "\" erneut ein, um neu zu starten!");
        } else {
            System.out.println("Person erfolgreich erstellt!");
        }
    }
}