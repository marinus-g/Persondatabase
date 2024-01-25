package academy.mischok.persondatabase.dto;

import academy.mischok.persondatabase.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * The PersonCreateResponse class represents the response of an attempt to create a new person.
 * It includes fields for an error message, a detailed error, and the created person.
 */
@AllArgsConstructor
public class PersonCreateResponse {

    /**
     * The error message of the creation attempt.
     * This field is null if the creation was successful.
     */
    private String errorMessage;

    /**
     * The detailed error of the creation attempt.
     * This field is accessible even if the creation was successful.
     */
    @Getter
    private DetailedError detailedError;

    /**
     * The created person.
     * This field is null if the creation was unsuccessful.
     */
    private Person person;

    /**
     * Returns the error message of the creation attempt.
     *
     * @return an Optional containing the error message, or an empty Optional if there was no error
     */
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    /**
     * Returns the created person.
     *
     * @return an Optional containing the created person, or an empty Optional if the creation was unsuccessful
     */
    public Optional<Person> getPerson() {
        return Optional.ofNullable(person);
    }

    /**
     * Checks if the creation was successful.
     *
     * @return true if the creation was successful, false otherwise
     */
    public boolean isCreated() {
        return this.getPerson().isPresent();
    }
}