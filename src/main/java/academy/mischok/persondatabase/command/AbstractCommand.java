package academy.mischok.persondatabase.command;

import java.util.Scanner;

/**
 * The AbstractCommand class is an abstract base class for all command classes in the application.
 * It provides two methods to handle commands, one that takes a command and arguments, and another that also takes a Scanner for input.
 * Subclasses are expected to override these methods to provide specific functionality for each command.
 */
public abstract class AbstractCommand {

    /**
     * Handles a command with the given command and arguments.
     * This method is intended to be overridden by subclasses to provide specific functionality for each command.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     */
    public void handleCommand(String command, String[] args) {
    }

    /**
     * Handles a command with the given command, arguments, and Scanner for input.
     * This method is intended to be overridden by subclasses to provide specific functionality for each command.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     * @param scanner the Scanner to use for input
     */
    public void handleCommand(String command, String[] args, Scanner scanner) {
    }
}