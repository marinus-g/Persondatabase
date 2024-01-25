package academy.mischok.persondatabase.model;

import lombok.*;

import java.util.Date;

/**
 * The Person class represents a person with their details.
 * It includes fields for the person's ID, first name, last name, email, country, birthday, salary, and bonus.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    /**
     * The ID of the person.
     */
    private long id;

    /**
     * The first name of the person.
     * This field is required.
     */
    private @NonNull String firstName;

    /**
     * The last name of the person.
     * This field is required.
     */
    private @NonNull String lastName;

    /**
     * The email of the person.
     * This field is required.
     */
    private @NonNull String email;

    /**
     * The country of the person.
     */
    private String country;

    /**
     * The birthday of the person.
     * This field is required.
     */
    private @NonNull Date birthday;

    /**
     * The salary of the person.
     * This field is required.
     */
    private @NonNull Integer salary;

    /**
     * The bonus of the person.
     */
    private Integer bonus;

}