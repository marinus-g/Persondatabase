package academy.mischok.persondatabase.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The Command annotation is used to annotate classes that represent commands in the application.
 * It provides metadata about the command such as its name, description, aliases, and usage.
 * The annotation is retained at runtime and can be applied to types (classes, interfaces, enums, and annotation types).
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    String name();

    /**
     * Returns the description of the command.
     *
     * @return the description of the command
     */
    String description();

    /**
     * Returns the aliases of the command.
     * By default, a command has no aliases.
     *
     * @return the aliases of the command
     */
    String[] aliases() default {};

    /**
     * Returns the usage of the command.
     * By default, a command has no specific usage.
     *
     * @return the usage of the command
     */
    String usage() default "";
}