package academy.mischok.persondatabase.dto;

import academy.mischok.persondatabase.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
public class PersonCreateResponse {

    private String errorMessage;
    @Getter
    private DetailedError detailedError;
    private Person person;

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    public Optional<Person> getPerson() {
        return Optional.ofNullable(person);
    }

    public boolean isCreated() {
        return this.getPerson().isPresent();
    }


    @Override
    public String toString() {
        return "PersonCreateResponse{" +
                "errorMessage='" + errorMessage + '\'' +
                ", detailedError=" + detailedError +
                ", person=" + person +
                '}';
    }
}