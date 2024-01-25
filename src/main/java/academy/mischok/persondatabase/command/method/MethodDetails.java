package academy.mischok.persondatabase.command.method;

import java.lang.reflect.Method;

/**
 * The MethodDetails record provides a compact way to store details about a method that is used in a command.
 * It stores a reference to the Method object and a boolean indicating whether the method needs a Scanner for input.
 * This record is used in the command handling system of the application to store and retrieve details about the methods that are invoked to execute commands.
 */
public record MethodDetails(Method method, boolean needsScanner) {

}