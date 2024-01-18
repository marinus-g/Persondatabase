package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.Utility;

import java.util.Scanner;

@Command(name = "populate", description = "Populates the person list")
public class PopulateCommand extends AbstractCommand {

    private final PersonService personService;

    public PopulateCommand(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        System.out.println();
        System.out.print("Gebe eine Anzahl an: ");
        final int amount = Utility.getIntegerFromScanner(scanner, "Gebe eine Anzahl an: ");
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
