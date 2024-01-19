package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.ui.PersonUi;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;
import academy.mischok.persondatabase.validator.StringValidator;

@Command(name = "gui", description = "Öffnet die GUI")
public class GuiCommand extends AbstractCommand {

    private final PersonService personService;
    private final PersonDatabase personDatabase;
    private final NameValidator nameValidator;
    private final DateValidator dateValidator;
    private final EmailValidator emailValidator;

    private PersonUi personUi;

    public GuiCommand(PersonService personService, PersonDatabase personDatabase, NameValidator nameValidator,
                      DateValidator dateValidator, EmailValidator emailValidator) {
        this.personService = personService;
        this.personDatabase = personDatabase;
        this.nameValidator = nameValidator;
        this.dateValidator = dateValidator;
        this.emailValidator = emailValidator;
    }

    @Override
    public void handleCommand(String command, String[] args) {
        System.out.println("Zeige GUI an..");
        if (personUi == null ) {
            personUi = new PersonUi(personDatabase, personService, dateValidator, nameValidator, emailValidator);
        } else {
            personUi.show();
        }
    }
}