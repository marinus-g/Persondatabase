package academy.mischok.persondatabase;

import academy.mischok.persondatabase.command.handler.CommandHandler;
import academy.mischok.persondatabase.configuration.InternalDatabaseConfiguration;
import academy.mischok.persondatabase.database.DatabaseConnection;
import academy.mischok.persondatabase.repository.PersonRepository;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;
import lombok.Getter;

/**
 * This class represents the main entry point for the PersonDatabase application.
 * It initializes the necessary services, validators and command handlers.
 */
@Getter
public class PersonDatabase {

    // Flag to check if the application is running
    private boolean isRunning = true;

    // Service to handle person related operations
    private final PersonService personService;

    /**
     * Constructor for the PersonDatabase class.
     * It initializes the validators, service and command handler.
     */
    PersonDatabase() {
        // Validator for name
        final NameValidator nameValidator = new NameValidator();
        // Validator for email
        final EmailValidator emailValidator = new EmailValidator();
        // Validator for date
        final DateValidator dateValidator = new DateValidator();

        // Initialize the person service with the necessary dependencies
        this.personService = new PersonService(
                new PersonRepository(new DatabaseConnection(new InternalDatabaseConfiguration())),
                nameValidator,
                emailValidator,
                dateValidator
        );
        // Initialize the command handler with the necessary dependencies
        new CommandHandler(this.personService, this,
                nameValidator,
                emailValidator,
                dateValidator);
    }

    /**
     * Setter for the isRunning field.
     * @param running the new value for the isRunning field.
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * Getter for the isRunning field.
     * @return the current value of the isRunning field.
     */
    public boolean isRunning() {
        return isRunning;
    }
}