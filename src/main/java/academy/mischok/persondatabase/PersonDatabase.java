package academy.mischok.persondatabase;

import academy.mischok.persondatabase.command.handler.CommandHandler;
import academy.mischok.persondatabase.configuration.InternalDatabaseConfiguration;
import academy.mischok.persondatabase.database.DatabaseConnection;
import academy.mischok.persondatabase.repository.PersonRepository;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;
import lombok.Getter;

@Getter
public class PersonDatabase {
    private boolean isRunning = true;
    private final PersonService personService;

    PersonDatabase() {
        final NameValidator nameValidator = new NameValidator();
        final EmailValidator emailValidator = new EmailValidator();
        final DateValidator dateValidator = new DateValidator();

        this.personService = new PersonService(
                new PersonRepository(new DatabaseConnection(new InternalDatabaseConfiguration())),
                nameValidator,
                emailValidator,
                dateValidator
        );
        new CommandHandler(this.personService, this,
                nameValidator,
                emailValidator,
                dateValidator);
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /*
    private void testThingy() {
        final PersonDto wolfgangKremer = new PersonDto();
        wolfgangKremer.setFirstName("Wolfgang");
        wolfgangKremer.setLastName("Kremer");
        wolfgangKremer.setEmail("wolfgang@kremer.de");
        wolfgangKremer.setSalary(0);
        wolfgangKremer.setBonus(0);
        wolfgangKremer.setBirthday("1.1.1920");
        wolfgangKremer.setCountry("Germany");

        PersonCreateResponse response = this.personService.addPerson(wolfgangKremer);
        System.out.println("WOlfgang Create: " + response.toString());

        PersonDto invalidPerson = new PersonDto();
        response = this.personService.addPerson(invalidPerson);
        System.out.println("invalid 1: " + response);
        invalidPerson.setFirstName("Test");
        response = this.personService.addPerson(invalidPerson);
        System.out.println("invalid 2: " + response);
        invalidPerson.setFirstName("TEst");
        response = this.personService.addPerson(invalidPerson);
        System.out.println("invalid 3: " + response);
        invalidPerson.setFirstName("Test");
        invalidPerson.setLastName("Test");
        invalidPerson.setEmail("wajkdwajd");
        response = this.personService.addPerson(invalidPerson);
        System.out.println("invalid 4: " + response);

        final PersonDto marinusGerdes = new PersonDto();
        marinusGerdes.setFirstName("Marinus");
        marinusGerdes.setLastName("Gerdes");
        marinusGerdes.setEmail("marinus@marinus.de");
        marinusGerdes.setBirthday("");
        response = this.personService.addPerson(marinusGerdes);
        System.out.println("Marinus Create: " + response);
        for (Person person : this.personService.findAll()) {
            System.out.println(person.toString());
        }
    }
     */
}