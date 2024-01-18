package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.command.CommandRegistry;
import academy.mischok.persondatabase.command.InternalCommand;

@Command(name = "help", description = "Displays the help menu")
public class HelpCommand extends AbstractCommand {

    private final CommandRegistry registry;
    public HelpCommand(CommandRegistry registry) {
        this.registry = registry;
    }
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