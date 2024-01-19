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


public class CommandConstructor {

    private static final List<Class<?>> VALID_CLASSES = Arrays.asList(PersonService.class,
            CommandRegistry.class,
            PersonDatabase.class,
            NameValidator.class,
            EmailValidator.class,
            DateValidator.class
    );

    private final Constructor<?> constructor;
    private final Class<?>[] parameters;

    public CommandConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
        Parameter[] parameters = this.constructor.getParameters();
        this.parameters = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            this.parameters[i] = parameters[i].getType();
        }
    }

    public boolean isValidParameters() {
        for (Class<?> parameter : this.parameters) {
            if (!VALID_CLASSES.contains(parameter))
                return false;
        }

        return true;
    }

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