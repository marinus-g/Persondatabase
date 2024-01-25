package academy.mischok.persondatabase.command;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.constructor.CommandConstructor;
import academy.mischok.persondatabase.command.exception.CommandConstructorNotFoundException;
import academy.mischok.persondatabase.command.exception.CommandHandlerNotFoundException;
import academy.mischok.persondatabase.command.impl.*;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;
import lombok.Getter;

import java.util.*;

/**
 * The CommandRegistry class is responsible for registering and managing commands within the application.
 * It maintains a set of InternalCommand objects, each representing a command that can be executed.
 * The class provides methods to register commands, find a command by its name, and build commands and internal commands.
 */
public class CommandRegistry {

    /**
     * The set of InternalCommand objects representing the commands that can be executed.
     */
    @Getter
    private final Set<InternalCommand> commands = new HashSet<>();

    private final PersonService personService;
    private final PersonDatabase personDatabase;
    private final NameValidator nameValidator;
    private final EmailValidator emailValidator;
    private final DateValidator dateValidator;

    /**
     * Constructs a CommandRegistry object with the given parameters and registers the commands.
     *
     * @param personService the PersonService to use
     * @param personDatabase the PersonDatabase to use
     * @param nameValidator the NameValidator to use
     * @param emailValidator the EmailValidator to use
     * @param dateValidator the DateValidator to use
     */
    public CommandRegistry(PersonService personService, PersonDatabase personDatabase, NameValidator nameValidator,
                           EmailValidator emailValidator, DateValidator dateValidator) {
        this.personService = personService;
        this.personDatabase = personDatabase;
        this.nameValidator = nameValidator;
        this.emailValidator = emailValidator;
        this.dateValidator = dateValidator;
        try {
            registerCommands();
        } catch (CommandConstructorNotFoundException | CommandHandlerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Registers the commands.
     *
     * @throws CommandConstructorNotFoundException if the constructor for a command is not found
     * @throws CommandHandlerNotFoundException if the handler for a command is not found
     */
    private void registerCommands() throws CommandConstructorNotFoundException, CommandHandlerNotFoundException {
        final List<Class<? extends AbstractCommand>> clazzes = Arrays.asList(
                CreateCommand.class,
                HelpCommand.class,
                DeleteCommand.class,
                ListCommand.class,
                EditCommand.class,
                ExitCommand.class,
                FilterCommand.class,
                PopulateCommand.class,
                GuiCommand.class,
                ClearCommand.class
        );

        for (final Class<? extends AbstractCommand> commandClass : clazzes) {
            final AbstractCommand abstractCommand = buildCommand(commandClass)
                    .orElseThrow(() -> new CommandConstructorNotFoundException("Constructor not found!"));
            final InternalCommand internalCommand = buildInternalCommand(commandClass, abstractCommand);
            this.commands.add(internalCommand);
        }
    }

    /**
     * Finds a command by its name.
     *
     * @param command the name of the command to find
     * @return an Optional containing the found InternalCommand, or an empty Optional if the command is not found
     */
    public Optional<InternalCommand> findCommand(String command) {
        return commands.stream()
                .filter(internalCommand -> internalCommand.name().equalsIgnoreCase(command)
                        || Arrays.stream(internalCommand.aliases()).anyMatch(s -> s.equalsIgnoreCase(command)))
                .findFirst();
    }

    /**
     * Builds a command.
     *
     * @param clazz the class of the command to build
     * @return an Optional containing the built AbstractCommand, or an empty Optional if the command cannot be built
     */
    private Optional<AbstractCommand> buildCommand(Class<? extends AbstractCommand> clazz) {
        return Arrays.stream(clazz.getConstructors()).map(CommandConstructor::new)
                .filter(CommandConstructor::isValidParameters)
                .map(commandConstructor -> commandConstructor.constructCommand(
                                personService,
                                personDatabase,
                                nameValidator,
                                emailValidator,
                                dateValidator,
                                this
                        )
                ).findFirst();
    }

    /**
     * Builds an internal command.
     *
     * @param commandClass the class of the command to build
     * @param abstractCommand the AbstractCommand to use to build the internal command
     * @return the built InternalCommand
     * @throws CommandHandlerNotFoundException if the handler for the command is not found
     */
    private InternalCommand buildInternalCommand(Class<? extends AbstractCommand> commandClass,
                                                 AbstractCommand abstractCommand) throws CommandHandlerNotFoundException {
        final Command commandAnnotation = commandClass.getAnnotation(Command.class);
        return new InternalCommand(
                abstractCommand,
                commandAnnotation.name(),
                commandAnnotation.description(),
                commandAnnotation.aliases(),
                commandAnnotation.name() + (commandAnnotation.usage().isBlank() ? "" :
                        (" " + commandAnnotation.usage()))
        );
    }
}