package academy.mischok.persondatabase.command.constructor;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.CommandRegistry;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.validator.DateValidator;
import academy.mischok.persondatabase.validator.EmailValidator;
import academy.mischok.persondatabase.validator.NameValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;


/**
 * Handles user input by reading from the Scanner and executing the corresponding command.
 * The method continues to read and execute commands until the PersonDatabase is no longer running.
 */
public class CommandConstructor {

    /**
     * A list of valid classes that can be used as parameters for the command constructor.
     */
    private static final List<Class<?>> VALID_CLASSES = Arrays.asList(PersonService.class,
            CommandRegistry.class,
            PersonDatabase.class,
            NameValidator.class,
            EmailValidator.class,
            DateValidator.class
    );

    /**
     * The constructor to use for creating the command object.
     */
    private final Constructor<?> constructor;

    /**
     * The types of the parameters for the constructor.
     */
    private final Class<?>[] parameters;


    /**
     * Constructs a CommandConstructor object with the given constructor.
     * The constructor's parameter types are stored for later use.
     *
     * @param constructor the constructor to use for creating the command object
     */
    public CommandConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
        Parameter[] parameters = this.constructor.getParameters();
        this.parameters = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            this.parameters[i] = parameters[i].getType();
        }
    }

    /**
     * Checks if the constructor's parameters are all valid classes.
     *
     * @return true if all parameters are valid classes, false otherwise
     */
    public boolean isValidParameters() {
        for (Class<?> parameter : this.parameters) {
            if (!VALID_CLASSES.contains(parameter))
                return false;
        }
        return true;
    }

    /**
     * Constructs a command object using the stored constructor and the given objects as parameters.
     * The objects are sorted to match the order of the constructor's parameters.
     *
     * @param objects the objects to use as parameters for the constructor
     * @return the constructed command object
     */
    public AbstractCommand constructCommand(Object... objects) {
        final Object[] sortedObjects = new Object[parameters.length];
        for (int i = 0; i < sortedObjects.length; i++) {
            int finalI = i;
            sortedObjects[i] = Arrays.stream(objects)
                    .filter(o -> o.getClass().equals(parameters[finalI]))
                    .findFirst().orElse(null);
        }
        try {
            return (AbstractCommand) (sortedObjects.length == 0 ? constructor.newInstance() : constructor.newInstance(sortedObjects));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}