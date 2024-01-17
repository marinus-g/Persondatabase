package academy.mischok.persondatabase.command;

import java.util.*;

public class CommandRegistry {

    private final Set<InternalCommand> commands = new HashSet<>();

    public CommandRegistry() {
        registerCommands();
    }

    private void registerCommands() {

    }

    public Optional<InternalCommand> findCommand(String command) {
        return commands.stream().filter(internalCommand -> internalCommand.getName().equalsIgnoreCase(command)).findFirst();
    }


}