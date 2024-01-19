package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.dto.PersonCreateResponse;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.Utility;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;

import java.util.Scanner;

@Command(name = "create", description = "Creates a new person", aliases = {"add", "a"})
public class CreateCommand extends AbstractCommand {

    private final PersonService personService;
    private final NameValidator nameValidator;
    private final EmailValidator emailValidator;
    private final DateValidator dateValidator;

    public CreateCommand(PersonService personService, NameValidator nameValidator,
                         EmailValidator emailValidator, DateValidator dateValidator) {
        this.personService = personService;
        this.nameValidator = nameValidator;
        this.emailValidator = emailValidator;
        this.dateValidator = dateValidator;
    }

    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {

        final PersonDto personDto = new PersonDto();

        personDto.setFirstName(Utility.getValidString(scanner, nameValidator, "Gib einen validen Vornamen ein: "));
        personDto.setLastName(Utility.getValidString(scanner, nameValidator, "Gib einen validen Nachnamen ein: "));
        personDto.setEmail(Utility.getValidString(scanner, emailValidator, "Gib eine validen Email-Adresse ein: "));
        System.out.print("Gib ein Land an (Lasse leer, für keins): ");
        String input = scanner.nextLine();
        personDto.setCountry(input.isBlank() ? null : input);
        personDto.setBirthday(Utility.getValidString(scanner, dateValidator, "Gib ein valides Geburtsdatum an: "));
        System.out.print("Gib ein Gehalt an: ");
        personDto.setSalary(Utility.getIntegerFromScanner(scanner, "Gib ein Gehalt an: "));
        System.out.print("Gib einen Bonus an: (Lasse leer, für kein Gehalt): ");
        personDto.setBonus(Utility.getIntegerFromScanner(scanner, null, true));
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