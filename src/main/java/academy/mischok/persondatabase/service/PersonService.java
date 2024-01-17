package academy.mischok.persondatabase.service;

import academy.mischok.persondatabase.model.PersonCreateRequest;
import academy.mischok.persondatabase.repository.PersonRepository;

public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
        // * FROM person WHERE ? = ?;
    }


    public PersonCreateRequest addPerson(String firstName, String lastName, String email) {
        final PersonCreateRequest request;
        request = new PersonCreateRequest("Falsche Email!", null);
        return request;
    }
}


// U <id>

// Gebe den neuen Vornamen ein, lasse leer für keine Änderung:
// Hans
// Gebe den neuen Nachnamen ein, lasse leer für keine Änderung:
// Müller



// U <id> Nachname=test Vorname=Test Email=Test
// Nachname = last_name
// Vorname = first_name

// für alles in liste splitted string
// builder.append(spllited[i] )

// SELECT * FROM person + stringbuilder;
