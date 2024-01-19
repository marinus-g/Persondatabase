package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import lombok.SneakyThrows;

@Command(name = "clear", description = "Clears the console")
public class ClearCommand extends AbstractCommand {

    @SneakyThrows
    @Override
    public void handleCommand(String command, String[] args) {
        final String os = System.getProperty("os.name");

        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\033[H\033[2J");
        }
    }
}
