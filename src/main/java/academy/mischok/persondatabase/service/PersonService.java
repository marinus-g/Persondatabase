package academy.mischok.persondatabase.service;

import academy.mischok.persondatabase.database.query.FilterQuery;
import academy.mischok.persondatabase.dto.DetailedError;
import academy.mischok.persondatabase.dto.PersonCreateResponse;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.dto.PersonEditResponse;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.repository.PersonRepository;
import academy.mischok.persondatabase.util.Utility;
import academy.mischok.persondatabase.validator.StringValidator;

import java.util.List;
import java.util.Optional;

/**
 * The PersonService class provides services for managing persons.
 * It includes methods for adding, editing, deleting, and finding persons.
 */
public class PersonService {

    private final PersonRepository personRepository;
    private final StringValidator emailValidator;
    private final StringValidator nameValidator;
    private final StringValidator dateValidator;

    /**
     * Constructs a new PersonService with the given repository and validators.
     *
     * @param personRepository the repository to use for storing persons
     * @param nameValidator the validator to use for validating names
     * @param emailValidator the validator to use for validating email addresses
     * @param dateValidator the validator to use for validating dates
     */
    public PersonService(PersonRepository personRepository, StringValidator nameValidator,
                         StringValidator emailValidator, StringValidator dateValidator) {
        this.personRepository = personRepository;
        this.emailValidator = emailValidator;
        this.nameValidator = nameValidator;
        this.dateValidator = dateValidator;
    }

    /**
     * Adds a new person to the repository.
     *
     * @param personDto the data transfer object containing the person's details
     * @return a PersonCreateResponse containing the result of the operation
     */
    public PersonCreateResponse addPerson(PersonDto personDto) {
        if (personDto.getFirstName() == null || !isValidName(personDto.getFirstName())) {
            return new PersonCreateResponse("Bitte gib einen gültigen Vornamen an!",
                    DetailedError.FIRST_NAME,
                    null);
        }
        if (personDto.getLastName() == null || !isValidName(personDto.getLastName())) {
            return new PersonCreateResponse("Bitte gib einen gültigen Nachnamen an!",
                    DetailedError.LAST_NAME,
                    null);
        }
        if (!isValidEmail(personDto.getEmail())) {
            return new PersonCreateResponse("Bitte gib eine gültige Email Adresse an!",
                    DetailedError.EMAIL,
                    null);
        }
        if (!isValidDate(personDto.getBirthday())){
            return new PersonCreateResponse("Bitte ein gültiges Datum eingeben!",
            DetailedError.DATE,
            null);
        }

        final Person person = new Person(
                null,
                personDto.getFirstName(),
                personDto.getLastName(),
                personDto.getEmail(),
                personDto.getCountry(),
                Utility.convertStringToDate(personDto.getBirthday()),
                personDto.getSalary(),
                personDto.getBonus()
        );
        this.personRepository.savePerson(person);
        return new PersonCreateResponse(null, null, person);
    }

    /**
     * Edits an existing person in the repository.
     *
     * @param oldPerson the existing person to edit
     * @param personDto the data transfer object containing the new details of the person
     * @return a PersonEditResponse containing the result of the operation
     */
    public PersonEditResponse editPerson(final Person oldPerson, final PersonDto personDto) {
        if (personDto.getFirstName() == null || !isValidName(personDto.getFirstName())) {
            return new PersonEditResponse("Bitte gib einen gültigen Vornamen an!",
                    DetailedError.FIRST_NAME,
                    null);
        }
        if (personDto.getLastName() == null || !isValidName(personDto.getLastName())) {
            return new PersonEditResponse("Bitte gib einen gültigen Nachnamen an!",
                    DetailedError.LAST_NAME,
                    null);
        }
        if (!isValidEmail(personDto.getEmail())) {
            return new PersonEditResponse("Bitte gib eine gültige Email Adresse an!",
                    DetailedError.EMAIL,
                    null);
        }
        if (!isValidDate(personDto.getBirthday())){
            return new PersonEditResponse("Bitte ein gültiges Datum eingeben!",
                    DetailedError.DATE,
                    null);
        }
        final Person person = new Person(
                oldPerson.getId(),
                personDto.getFirstName(),
                personDto.getLastName(),
                personDto.getEmail(),
                personDto.getCountry(),
                Utility.convertStringToDate(personDto.getBirthday()),
                personDto.getSalary(),
                personDto.getBonus()
        );
        this.personRepository.savePerson(person);
        return new PersonEditResponse(null, null, person);
    }

    /**
     * Deletes a person from the repository.
     *
     * @param person the person to delete
     * @return true if the person was deleted, false otherwise
     */
    public boolean deletePerson(Person person){
        return this.personRepository.deletePerson(person);
    }

    /**
     * Finds persons in the repository that match the given filter query.
     *
     * @param filterQuery the filter query to use for finding persons
     * @return a list of persons that match the filter query
     */
    public List<Person> findByFilterQuery(FilterQuery filterQuery) {
        return this.personRepository.findByFilterQuery(filterQuery);
    }

    /**
     * Finds a person in the repository by their ID.
     *
     * @param id the ID of the person to find
     * @return an Optional containing the found person, or an empty Optional if no person was found
     */
    public Optional<Person> findPersonById(final long id) {
        return this.personRepository.findPersonById(id);
    }

    /**
     * Deletes all persons from the repository.
     */
    public void deleteAll() {
        this.personRepository.deleteAll();
    }

    /**
     * Checks if a string is a valid email address.
     *
     * @param email the string to check
     * @return true if the string is a valid email address, false otherwise
     */
    private boolean isValidEmail(final String email) {
        return this.emailValidator.isValidString(email);
    }

    /**
     * Checks if a string is a valid name.
     *
     * @param name the string to check
     * @return true if the string is a valid name, false otherwise
     */
    private boolean isValidName(final String name) {
        return this.nameValidator.isValidString(name);
    }

    /**
     * Checks if a string is a valid date.
     *
     * @param date the string to check
     * @return true if the string is a valid date, false otherwise
     */
    private boolean isValidDate(final String date){
        return this.dateValidator.isValidString(date);
    }
}