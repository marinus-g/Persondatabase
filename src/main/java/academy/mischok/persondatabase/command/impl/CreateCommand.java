package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.dto.PersonCreateResponse;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.Utility;

import java.util.Scanner;

@Command(name = "create", description = "Creates a new person", aliases = {"add", "a"})
public class CreateCommand extends AbstractCommand {

    private final PersonService personService;

    public CreateCommand(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {

        final PersonDto personDto = new PersonDto();

        System.out.print("Gib einen Vornamen ein: ");
        personDto.setFirstName(scanner.nextLine());
        System.out.print("Gib einen Nachnamen ein: ");
        personDto.setLastName(scanner.nextLine());
        System.out.print("Gib eine Email-Adresse an: ");
        personDto.setEmail(scanner.nextLine());
        System.out.print("Gib ein Land an (Lasse leer, für keins): ");
        String input = scanner.nextLine();
        personDto.setCountry(input.isBlank() ? null : input);
        System.out.print("Gib ein Geburtsdatum an (d.M.yyyy Format): ");
        personDto.setBirthday(scanner.nextLine());
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