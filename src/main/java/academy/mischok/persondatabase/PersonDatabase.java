package academy.mischok.persondatabase;

import academy.mischok.persondatabase.configuration.InternalDatabaseConfiguration;
import academy.mischok.persondatabase.database.DatabaseConnection;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.repository.PersonRepository;
import lombok.Getter;

import java.util.Optional;
import java.util.Scanner;

@Getter
public class PersonDatabase {
    private final DatabaseConnection database;

    PersonDatabase() {
        database = new DatabaseConnection(new InternalDatabaseConfiguration());

    }


    /*
    public void addPerson(Scanner scanner) {
        Person person = new Person();
        System.out.println("Gib den Vornamen ein:");
        person.setFirstName(scanner.nextLine());
        System.out.println("Gib den Nachnamen ein:");
        person.setLastName(scanner.nextLine());
        System.out.println("Gib die Email ein:");
        person.setEmail(scanner.nextLine());
        System.out.println("Gib das Land ein:");
        person.setCountry(scanner.nextLine());
        System.out.println("Gib das Geburtsdatum ein:");
        // person.setBirthday(scanner.nextLine());
        System.out.println("Gib das Gehalt ein:");
        person.setSalary(scanner.nextInt());
        System.out.println("Gib den Bonus ein:");
        person.setBonus(scanner.nextInt());
        savePerson(person);
    }

    public void editPerson(Scanner scanner) {
        System.out.println("Gib id an:");
        long id = scanner.nextLong();
        Optional<Person> personOptional = getPersonById(id);
        personOptional.ifPresentOrElse(person -> {
            System.out.println("Gib den neuen Vornamen ein, lasse leer für keine Änderung:");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) {
                person.setFirstName(firstName);
            }
            System.out.println("Gib den neuen Nachnamen ein, lasse leer für keine Änderung:");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) {
                person.setLastName(lastName);
            }
            System.out.println("Gib die neue Email ein, lasse leer für keine Änderung:");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                person.setEmail(email);
            }
            System.out.println("Gib das neue Land ein, lasse leer für keine Änderung:");
            String country = scanner.nextLine();
            if (!country.isEmpty()) {
                person.setCountry(country);
            }
            System.out.println("Gib das neue Geburtsdatum ein, lasse leer für keine Änderung:");
            String birthday = scanner.nextLine();
            if (!birthday.isEmpty()) {
                //person.setBirthday(birthday);
            }
            System.out.println("Gib das neue Gehalt ein, lasse leer für keine Änderung:");
            int salary = scanner.nextInt();
            if (salary != 0) {
                person.setSalary(salary);
            }
            System.out.println("Gib den neuen Bonus ein, lasse leer für keine Änderung:");
            int bonus = scanner.nextInt();
            if (bonus != 0) {
                person.setBonus(bonus);
            }
            System.out.println("Neue Daten: " + person);
            System.out.println("Willst du diese Speichern?");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("ja")) {
                savePerson(person);
            }
        }, () -> {
            System.out.println("Person nicht gefunden!");
        });
    }

     */



    // SELECT * FROM person WHERE id = ?;
    // if (rs.hasNext()) {
    // UPDATE
    // else
    // INSERT


    // id(pk), column

    // INSERT INTO table (id, column) VALUES (?, ?);
    // INSERT INTO table SET column = value ON DUPLICATE KEY UPDATE column = value;

    // UPDATE table SET column = value WHERE column = ?;
    // UPDATE column, column VON person WO id = ? (value, value)
    // UPDATE first_name, last_name ('Dörte', 'Wasauchimmer')FROM person WHERE id = ?;

    // firsName = Scanner#nextLine();

    // UPDATE person SET first_name = ? WHERE id = ?;

    // lastName = SCanner#nextLine();
    // UPDATE person SET last_name = ? WHERE id = ?;

    // E <id>

    // SUCHE NACH person mit der id
    // wenn gefunden, dann returned es die Person im Optional, wenn nicht returned es ein leeres optional



    // Person person = personOptional.get();

    // firstName, lastName, email, country, birthday, salary, bonus

    // firstName = x;
    // lastName = x;

    // person.setFirstName(x);
    // person.setLastName(x);

    // Gib neuen Vornamen ein, lasse leer für keine Änderung:
    // Hans
    // Gib neuen Nachnamen ein, lasse leer für keine Änderung:
    // Müller
    // Gib neue Email ein, lasse leer für keine Änderung:
    // hans.müller@hans
    // Gib neues Land ein, lasse leer für keine Änderung:
    // Deutschland
    // Gib neues Geburtsdatum ein, lasse leer für keine Änderung:
    //
    // Gib neues Gehalt ein, lasse leer für keine Änderung:


    //String lastName =
}