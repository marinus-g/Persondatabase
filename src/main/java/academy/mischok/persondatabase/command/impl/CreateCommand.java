package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.command.ICommand;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.service.PersonService;

import java.util.Scanner;

@Command(name = "create", description = "Creates a new person")
public class CreateCommand implements ICommand {

    private final PersonService personService;

    public CreateCommand(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        final PersonDto personDto = new PersonDto();
        System.out.println("Gebe Email an");
        personDto.setEmail(scanner.nextLine());
        this.personService.addPerson(personDto);

        // tesjkltjklesljkt
        // gib land an:
        // Deutschland
        // Submit
    }
}