package academy.mischok.persondatabase.service;

import academy.mischok.persondatabase.database.query.Filter;
import academy.mischok.persondatabase.database.query.FilterQuery;
import academy.mischok.persondatabase.dto.PersonCreateResponse;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.dto.PersonEditResponse;
import academy.mischok.persondatabase.dto.DetailedError;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.repository.PersonRepository;
import academy.mischok.persondatabase.util.Utility;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;
import academy.mischok.persondatabase.validator.StringValidator;

import java.util.List;
import java.util.Optional;

public class PersonService {

    private final PersonRepository personRepository;
    private final StringValidator emailValidator = new EmailValidator();
    private final StringValidator nameValidator = new NameValidator();
    private final StringValidator dateValidator = new DateValidator();
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


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
                this.personRepository.size() + 1,
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

    public boolean deletePerson(Person person){
        return this.personRepository.deletePerson(person);
    }

    public List<Person> findByFilterQuery(FilterQuery filterQuery) {
        return this.personRepository.findByFilterQuery(filterQuery);
    }

    public Optional<Person> findPersonById(final long id) {
        return this.personRepository.findPersonById(id);
    }

    public List<Person> findPersonByFirstName(final String firstName){
        return this.personRepository.findPersonByFirstName(firstName);
    }
    public List<Person> findPersonByLastNameLike(final String lastName){
        return this.personRepository.findPersonByLastNameLike(lastName);
    }
    public List<Person> findPersonByFirstOrLastName(final String firstOrLastName){
        return this.personRepository.findPersonByFirstOrLastName(firstOrLastName);
    }

    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    private boolean isValidEmail(final String email) {
        return this.emailValidator.isValidString(email);
    }
    
    private boolean isValidName(final String name) {
        return this.nameValidator.isValidString(name);
    }

    private boolean isValidDate(final String date){
        return this.dateValidator.isValidString(date);
    }

    public void deleteAll() {
        this.personRepository.deleteAll();
    }
}