package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.ui.PersonUi;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;

/**
 * The GuiCommand class represents a command that opens the GUI in the application.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the GUI command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "gui", description = "Öffnet die GUI")
public class GuiCommand extends AbstractCommand {

    /**
     * The PersonService to use for person-related operations.
     */
    private final PersonService personService;

    /**
     * The PersonDatabase to use for database-related operations.
     */
    private final PersonDatabase personDatabase;

    /**
     * The NameValidator to use for name validation.
     */
    private final NameValidator nameValidator;

    /**
     * The DateValidator to use for date validation.
     */
    private final DateValidator dateValidator;

    /**
     * The EmailValidator to use for email validation.
     */
    private final EmailValidator emailValidator;

    /**
     * The PersonUi to use for GUI-related operations.
     */
    private PersonUi personUi;

    /**
     * Constructs a GuiCommand object with the given PersonService, PersonDatabase, NameValidator, DateValidator, and EmailValidator.
     *
     * @param personService the PersonService to use for person-related operations
     * @param personDatabase the PersonDatabase to use for database-related operations
     * @param nameValidator the NameValidator to use for name validation
     * @param dateValidator the DateValidator to use for date validation
     * @param emailValidator the EmailValidator to use for email validation
     */
    public GuiCommand(PersonService personService, PersonDatabase personDatabase, NameValidator nameValidator,
                      DateValidator dateValidator, EmailValidator emailValidator) {
        this.personService = personService;
        this.personDatabase = personDatabase;
        this.nameValidator = nameValidator;
        this.dateValidator = dateValidator;
        this.emailValidator = emailValidator;
    }

    /**
     * Handles the GUI command with the given command and arguments.
     * The method checks if the GUI is already open, and if not, it creates a new GUI and shows it.
     * If the GUI is already open, it simply shows the existing GUI.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     */
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