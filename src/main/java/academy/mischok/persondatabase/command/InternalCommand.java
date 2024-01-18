package academy.mischok.persondatabase.command;

import academy.mischok.persondatabase.command.exception.CommandHandlerNotFoundException;
import academy.mischok.persondatabase.command.method.MethodDetails;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;

public final class InternalCommand {

    private static final Class<?>[] SCANNER_PARAMETER = new Class[]{String.class, String[].class, Scanner.class};
    private static final Class<?>[] NORMAL_PARAMETER = new Class[]{String.class, String[].class};

    private final AbstractCommand object;
    private final String name;
    private final String description;
    private final String[] aliases;
    private final String usage;

    private MethodDetails methodDetails;

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

    public AbstractCommand object() {
        return object;
    }

    public String name() {
        return name;
    }

    public String usage() {
        return this.usage;
    }

    public String description() {
        return description;
    }

    public String[] aliases() {
        return aliases;
    }

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