package academy.mischok.persondatabase.dto;

import academy.mischok.persondatabase.model.Person;
import lombok.AllArgsConstructor;
import java.util.Optional;

@AllArgsConstructor
public class PersonEditResponse {

    private String errorMessage;
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
        return "PersonEditResponse{" +
                "errorMessage='" + errorMessage + '\'' +
                ", detailedError=" + detailedError +
                ", person=" + person +
                '}';
    }
}