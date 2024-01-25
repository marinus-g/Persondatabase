package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import lombok.SneakyThrows;

/**
 * The ClearCommand class represents a command that clears the console.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the clear command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "clear", description = "Clears the console")
public class ClearCommand extends AbstractCommand {

    /**
     * Handles the clear command with the given command and arguments.
     * The method checks the operating system and then clears the console accordingly.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     */
    @SneakyThrows
    @Override
    public void handleCommand(String command, String[] args) {
        final String os = System.getProperty("os.name");

        if (os.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\033[H\033[2J");
        }
    }
}
