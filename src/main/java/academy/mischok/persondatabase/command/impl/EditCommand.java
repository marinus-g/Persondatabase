package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.dto.PersonEditResponse;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.Utility;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;

import java.util.Scanner;
import java.util.UUID;

@SuppressWarnings("DuplicatedCode")
@Command(name = "edit", description = "Edit a new person", aliases = {"edit", "e"})
public class EditCommand extends AbstractCommand {


    private final PersonService personService;
    private final UUID uuid = UUID.randomUUID();
    private final DateValidator dateValidator;
    private final EmailValidator emailValidator;
    private final NameValidator nameValidator;

    private Person tempPerson = null;
    public EditCommand(PersonService personService, DateValidator dateValidator, EmailValidator emailValidator, NameValidator nameValidator) {
        this.personService = personService;
        this.dateValidator = dateValidator;
        this.emailValidator = emailValidator;
        this.nameValidator = nameValidator;
    }

    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        Long idInput = null;
        if (args.length == 2 && args[1].equalsIgnoreCase(uuid.toString())) {
            System.out.println("Du bearbeitest" + tempPerson.getFirstName() + " " + tempPerson.getLastName() + " mit der ID " + tempPerson.getId());
            tempPerson = null;
            System.out.print("Möchtest du abbrechen? (ja/nein): ");
            if (Utility.getConfirmation(scanner, "Möchtest du abbrechen? (ja/nein)")) {
                System.out.println("Abgebrochen!");
                return;
            }
            idInput = Long.parseLong(args[0]);
        }
        if (idInput == null) {
            System.out.print("Gib die ID der Person, welche du bearbeiten willst ein: ");
            idInput = Utility.getLongFromScanner(scanner, "Gib die ID der Person, welche du bearbeiten willst ein: ");
        }
        this.personService.findPersonById(idInput).ifPresentOrElse(person -> {
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
            System.out.print("Möchtest du die neuen Werte speichern? (ja/nein): ");
            if (!Utility.getConfirmation(scanner, "Möchtest du die neuen Werte speichern? (ja/nein): ")) {
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
