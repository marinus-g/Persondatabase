package academy.mischok.persondatabase.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
public class PersonCreateRequest {

    private String errorMessage;
    private Person person;

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    public Optional<Person> getPerson() {
        return Optional.ofNullable(person);
    }
}