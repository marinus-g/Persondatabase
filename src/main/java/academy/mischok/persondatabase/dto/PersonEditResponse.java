package academy.mischok.persondatabase.dto;

import academy.mischok.persondatabase.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * The PersonEditResponse class represents the response of an attempt to edit a person's details.
 * It includes fields for an error message, a detailed error, and the edited person.
 */
@AllArgsConstructor
public class PersonEditResponse {

    /**
     * The error message of the edit attempt.
     * This field is null if the edit was successful.
     */
    private String errorMessage;

    /**
     * The detailed error of the edit attempt.
     * This field is null if the edit was successful.
     */
    @Getter
    private DetailedError detailedError;

    /**
     * The edited person.
     * This field is null if the edit was unsuccessful.
     */
    private Person person;

    /**
     * Returns the error message of the edit attempt.
     *
     * @return an Optional containing the error message, or an empty Optional if there was no error
     */
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    /**
     * Returns the edited person.
     *
     * @return an Optional containing the edited person, or an empty Optional if the edit was unsuccessful
     */
    public Optional<Person> getPerson() {
        return Optional.ofNullable(person);
    }

    /**
     * Checks if the edit was successful.
     *
     * @return true if the edit was successful, false otherwise
     */
    public boolean isEdited() {
        return this.getPerson().isPresent();
    }
}