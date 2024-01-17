package academy.mischok.persondatabase.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private long id;
    private @NonNull String firstName;
    private @NonNull String lastName;
    private @NonNull String email;
    private String country;
    private @NonNull Date birthday;
    private int salary;
    private int bonus;

}