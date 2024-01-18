package academy.mischok.persondatabase.command;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.constructor.CommandConstructor;
import academy.mischok.persondatabase.command.exception.CommandConstructorNotFoundException;
import academy.mischok.persondatabase.command.exception.CommandHandlerNotFoundException;
import academy.mischok.persondatabase.command.impl.*;
import academy.mischok.persondatabase.service.PersonService;
import lombok.Getter;

import java.util.*;

public class CommandRegistry {

    @Getter
    private final Set<InternalCommand> commands = new HashSet<>();

    private final PersonService personService;
    private final Scanner scanner;
    private final PersonDatabase personDatabase;

    public CommandRegistry(PersonService personService, Scanner scanner, PersonDatabase personDatabase) {
        this.personService = personService;
        this.scanner = scanner;
        this.personDatabase = personDatabase;
        try {
            registerCommands();
        } catch (CommandConstructorNotFoundException | CommandHandlerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerCommands() throws CommandConstructorNotFoundException, CommandHandlerNotFoundException {
        final List<Class<? extends AbstractCommand>> clazzes = Arrays.asList(
                CreateCommand.class,
                HelpCommand.class,
                DeleteCommand.class,
                ListCommand.class,
                EditCommand.class,
                ExitCommand.class,
                FilterCommand.class,
                PopulateCommand.class
        );
        for (final Class<? extends AbstractCommand> commandClass : clazzes) {
            final AbstractCommand abstractCommand = buildCommand(commandClass)
                    .orElseThrow(() -> new CommandConstructorNotFoundException("Constructor not found!"));
            final InternalCommand internalCommand = buildInternalCommand(commandClass, abstractCommand);
            this.commands.add(internalCommand);
        }
    }

    public Optional<InternalCommand> findCommand(String command) {
        return commands.stream()
                .filter(internalCommand -> internalCommand.name().equalsIgnoreCase(command)
                        || Arrays.stream(internalCommand.aliases()).anyMatch(s -> s.equalsIgnoreCase(command)))
                .findFirst();
    }

    private Optional<AbstractCommand> buildCommand(Class<? extends AbstractCommand> clazz) {
        return Arrays.stream(clazz.getConstructors()).map(CommandConstructor::new)
                .filter(CommandConstructor::isValidParameters)
                .map(commandConstructor -> commandConstructor.constructCommand(personService, this, personDatabase))
                .findFirst();
    }

    private InternalCommand buildInternalCommand(Class<? extends AbstractCommand> commandClass, AbstractCommand abstractCommand) throws CommandHandlerNotFoundException {
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