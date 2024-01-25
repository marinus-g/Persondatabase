package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.command.CommandRegistry;
import academy.mischok.persondatabase.command.InternalCommand;

/**
 * The HelpCommand class represents a command that displays the help menu in the application.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the help command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "help", description = "Displays the help menu")
public class HelpCommand extends AbstractCommand {

    /**
     * The CommandRegistry to use for command-related operations.
     */
    private final CommandRegistry registry;

    /**
     * Constructs a HelpCommand object with the given CommandRegistry.
     *
     * @param registry the CommandRegistry to use for command-related operations
     */
    public HelpCommand(CommandRegistry registry) {
        this.registry = registry;
    }

    /**
     * Handles the help command with the given command and arguments.
     * The method retrieves the list of commands from the CommandRegistry and displays them.
     * Each command is displayed with its usage and description.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     */
    @Override
    public void handleCommand(String command, String[] args) {
        System.out.println("Liste aller benutzbarer Befehle:");
        for (InternalCommand registryCommand : this.registry.getCommands()) {
            if (registryCommand.object() instanceof HelpCommand)
                continue;
            System.out.println(registryCommand.usage() + " - " + registryCommand.description());
        }
    }
}