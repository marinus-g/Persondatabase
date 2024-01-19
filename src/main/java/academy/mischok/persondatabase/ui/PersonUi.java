package academy.mischok.persondatabase.ui;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.database.query.FilterQuery;
import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.ui.application.DatabaseApplication;
import academy.mischok.persondatabase.ui.view.DatabaseView;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class PersonUi {

    public static PersonUi PERSON_UI;

    private final PersonDatabase personDatabase;
    private final PersonService personService;
    private final NameValidator nameValidator;
    private final EmailValidator emailValidator;
    private final DateValidator dateValidator;

    private DatabaseApplication databaseApplication;
    private DatabaseView databaseView;

    private final FilterQuery filterQuery;

    @Getter(AccessLevel.NONE)
    private final Map<Person, PersonDto> personDtoToPerson = new HashMap<>();

    private boolean isRunning;
    private boolean ran;

    public PersonUi(final PersonDatabase personDatabase, final PersonService personService,
                    final DateValidator dateValidator, final NameValidator nameValidator,
                    final EmailValidator emailValidator) {
        this.personDatabase = personDatabase;
        this.personService = personService;
        this.dateValidator = dateValidator;
        this.nameValidator = nameValidator;
        this.emailValidator = emailValidator;
        this.filterQuery = new FilterQuery(this.personService);
        PERSON_UI = this;
        if (ran) {
            init();
            return;
        }
        setRunning(true);
        new Thread(() -> {
            DatabaseApplication.startUi(() -> this.databaseApplication.getPrimaryStage().setOnCloseRequest(windowEvent -> Platform.exit()));
        }).start();
    }



    public void init() {
        this.databaseView = new DatabaseView(this, this.personService);
    }

    public PersonDto addPersonDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setPerson(person);
        this.personDtoToPerson.put(person, personDto);
        return personDto;
    }

    public void removePersonDto(Person person) {
        this.personDtoToPerson.remove(person);
    }

    public Optional<PersonDto> getPersonDto(Person person) {
        return Optional.ofNullable(this.personDtoToPerson.get(person));
    }

    public boolean hasPersonDto(Person person) {
        return this.personDtoToPerson.containsKey(person);
    }

    public void show() {
        setRunning(true);
        Platform.runLater(() ->   this.databaseApplication.getPrimaryStage().show());
        Platform.runLater(() -> this.databaseApplication.getPrimaryStage().requestFocus());
        Platform.requestNextPulse();
    }
}