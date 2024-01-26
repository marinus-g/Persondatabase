package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.dto.PersonEditResponse;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.ScannerUtil;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;

import java.util.Scanner;
import java.util.UUID;

/**
 * The EditCommand class represents a command that edits a person in the application.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the edit command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@SuppressWarnings("DuplicatedCode")
@Command(name = "edit", description = "Edit a new person", aliases = {"edit", "e"})
public class EditCommand extends AbstractCommand {


    /**
     * The PersonService to use for person-related operations.
     */
    private final PersonService personService;

    /**
     * A UUID to use for unique identification.
     */
    private final UUID uuid = UUID.randomUUID();

    /**
     * The DateValidator to use for date validation.
     */
    private final DateValidator dateValidator;

    /**
     * The EmailValidator to use for email validation.
     */
    private final EmailValidator emailValidator;

    /**
     * The NameValidator to use for name validation.
     */
    private final NameValidator nameValidator;

    /**
     * The temporary Person object to use for editing.
     */
    private Person tempPerson = null;

    /**
     * Constructs an EditCommand object with the given PersonService, DateValidator, EmailValidator, and NameValidator.
     *
     * @param personService the PersonService to use for person-related operations
     * @param dateValidator the DateValidator to use for date validation
     * @param emailValidator the EmailValidator to use for email validation
     * @param nameValidator the NameValidator to use for name validation
     */
    public EditCommand(PersonService personService, DateValidator dateValidator, EmailValidator emailValidator, NameValidator nameValidator) {
        this.personService = personService;
        this.dateValidator = dateValidator;
        this.emailValidator = emailValidator;
        this.nameValidator = nameValidator;
    }

    /**
     * Handles the edit command with the given command, arguments, and Scanner for input.
     * The method prompts the user to enter the details of the person to edit, and then applies the changes.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     * @param scanner the Scanner to use for input
     */
    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        Long idInput = null;
        if (args.length == 2 && args[1].equalsIgnoreCase(uuid.toString())) {
            System.out.println("Du bearbeitest" + tempPerson.getFirstName() + " " + tempPerson.getLastName() + " mit der ID " + tempPerson.getId());
            tempPerson = null;
            System.out.print("Möchtest du abbrechen? (ja/nein): ");
            if (ScannerUtil.getConfirmation(scanner, "Möchtest du abbrechen? (ja/nein)")) {
                System.out.println("Abgebrochen!");
                return;
            }
            idInput = Long.parseLong(args[0]);
        }
        if (idInput == null) {
            System.out.print("Gib die ID der Person, welche du bearbeiten willst ein: ");
            idInput = ScannerUtil.getLongFromScanner(scanner, "Gib die ID der Person, welche du bearbeiten willst ein: ");
        }
        this.personService.findPersonById(idInput).ifPresentOrElse(person -> {
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
            System.out.print("Möchtest du die neuen Werte speichern? (ja/nein): ");
            if (!ScannerUtil.getConfirmation(scanner, "Möchtest du die neuen Werte speichern? (ja/nein): ")) {
                System.out.println("Bearbeitung abgebrochen!");
                return;
            }
            PersonEditResponse editResponse = this.personService.editPerson(person, personDto);
            if (!editResponse.isEdited()) {
                //noinspection OptionalGetWithoutIsPresent
                System.out.println(editResponse.getErrorMessage().get());
                System.out.println("Gebe jetzt die Person erneut ein!");
                tempPerson = person;
                handleCommand(command, new String[]{String.valueOf(person.getId()), uuid.toString()}, scanner);
            } else {
                System.out.println("Person erfolgreich editiert!");
            }
        }, () -> System.out.println("Person konnte nicht gefunden werden!"));
    }
}