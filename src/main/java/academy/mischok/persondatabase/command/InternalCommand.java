package academy.mischok.persondatabase.command;

import academy.mischok.persondatabase.command.exception.CommandHandlerNotFoundException;
import academy.mischok.persondatabase.command.method.MethodDetails;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;

/**
 * The InternalCommand class represents a command that can be executed within the application.
 * It contains information about the command such as its name, description, aliases, and usage.
 * It also contains a reference to the object that will handle the command and the method that will be invoked to execute the command.
 * The class provides methods to execute the command and to get information about the command.
 */
public final class InternalCommand {

    private static final Class<?>[] SCANNER_PARAMETER = new Class[]{String.class, String[].class, Scanner.class};
    private static final Class<?>[] NORMAL_PARAMETER = new Class[]{String.class, String[].class};

    private final AbstractCommand object;
    private final String name;
    private final String description;
    private final String[] aliases;
    private final String usage;

    private MethodDetails methodDetails;

    /**
     * Constructs an InternalCommand object with the given parameters.
     *
     * @param object the object that will handle the command
     * @param name the name of the command
     * @param description the description of the command
     * @param aliases the aliases of the command
     * @param usage the usage of the command
     * @throws CommandHandlerNotFoundException if the command handler for the command is not found
     */
    public InternalCommand(AbstractCommand object, String name, String description, String[] aliases, String usage) throws CommandHandlerNotFoundException {
        this.object = object;
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.usage = usage;
        this.init();
        if (this.methodDetails == null) {
            throw new CommandHandlerNotFoundException("Comman handler for command " + name + " not found!");
        }
    }

    /**
     * Executes the command with the given parameters.
     *
     * @param command the command to execute
     * @param args the arguments of the command
     * @param scanner the scanner to use for input
     */
    public void execute(String command, String[] args, Scanner scanner) {
        final Object[] objects = new Object[methodDetails.needsScanner() ? 3 : 2];
        objects[0] = command;
        objects[1] = args;
        if (this.methodDetails.needsScanner()) {
            objects[2] = scanner;
        }
        try {
            methodDetails.method().invoke(object, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the object that will handle the command.
     *
     * @return the object that will handle the command
     */
    public AbstractCommand object() {
        return object;
    }

    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    public String name() {
        return name;
    }

    /**
     * Returns the usage of the command.
     *
     * @return the usage of the command
     */
    public String usage() {
        return this.usage;
    }

    /**
     * Returns the description of the command.
     *
     * @return the description of the command
     */
    public String description() {
        return description;
    }

    /**
     * Returns the aliases of the command.
     *
     * @return the aliases of the command
     */
    public String[] aliases() {
        return aliases;
    }

    /**
     * Initializes the method details of the command.
     */
    private void init() {
        for (Method declaredMethod : this.object.getClass().getDeclaredMethods()) {
            if (declaredMethod.getName().equals("handleCommand")) {
                final Parameter[] parameters = declaredMethod.getParameters();
                if (matches(parameters, SCANNER_PARAMETER)) {
                 this.methodDetails = new MethodDetails(declaredMethod, true);
                 break;
                }
                if (matches(parameters, NORMAL_PARAMETER)) {
                    this.methodDetails = new MethodDetails(declaredMethod, false);
                    break;
                }
            }
        }
    }

    /**
     * Checks if the parameters of the command match the given classes.
     *
     * @param parameters the parameters of the command
     * @param classes the classes to match
     * @return true if the parameters match the classes, false otherwise
     */
    private boolean matches(Parameter[] parameters, Class<?>[] classes) {
        if (parameters.length != classes.length) {
            return false;
        }
        for (int i = 0; i < parameters.length; i++) {
            if (!parameters[i].getType().equals(classes[i])) {
                return false;
            }
        }
        return true;
    }
}