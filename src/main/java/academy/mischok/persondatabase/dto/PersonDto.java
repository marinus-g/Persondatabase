package academy.mischok.persondatabase.dto;

import academy.mischok.persondatabase.model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PersonDto {

    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String birthday;
    private Integer salary;
    private Integer bonus;

    public void setPerson(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.country = person.getCountry();
        this.birthday = person.getBirthday().toString();
        this.salary = person.getSalary();
        this.bonus = person.getBonus();
    }

    public boolean canSubmit() {
        return this.firstName != null && this.lastName != null && this.email != null
                && this.birthday != null && this.salary != null;
    }

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