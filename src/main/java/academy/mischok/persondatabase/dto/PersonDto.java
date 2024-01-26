package academy.mischok.persondatabase.dto;

import academy.mischok.persondatabase.model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The PersonDto class is a data transfer object that represents a person's details.
 * It includes fields for the person's first name, last name, email, country, birthday, salary, and bonus.
 */
@NoArgsConstructor
@Setter
@Getter
public class PersonDto {

    /**
     * The first name of the person.
     */
    private String firstName;

    /**
     * The last name of the person.
     */
    private String lastName;

    /**
     * The email of the person.
     */
    private String email;

    /**
     * The country of the person.
     */
    private String country;

    /**
     * The birthday of the person.
     */
    private String birthday;

    /**
     * The salary of the person.
     */
    private Integer salary;

    /**
     * The bonus of the person.
     */
    private Integer bonus;

    /**
     * Sets the details of the person from a Person object.
     *
     * @param person the Person object to get the details from
     */
    public void setPerson(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.country = person.getCountry();
        this.birthday = person.getBirthday().toString();
        this.salary = person.getSalary();
        this.bonus = person.getBonus();
    }

    /**
     * Checks if the person's details are ready to be submitted.
     *
     * @return true if the first name, last name, email, birthday, and salary are not null, false otherwise
     */
    public boolean canSubmit() {
        return this.firstName != null && this.lastName != null && this.email != null
                && this.birthday != null && this.salary != null;
    }

    /**
     * Resets the person's details.
     */
    public void reset() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.country = null;
        this.birthday = null;
        this.salary = null;
        this.bonus = null;
    }
}