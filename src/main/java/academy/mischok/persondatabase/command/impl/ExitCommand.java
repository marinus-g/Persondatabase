package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.PersonDatabase;
import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;

@Command(name = "exit", description = "Ends the program", aliases = "end")
public class ExitCommand extends AbstractCommand {

    private final PersonDatabase personDatabase;

    public ExitCommand(PersonDatabase personDatabase) {
        this.personDatabase = personDatabase;
    }

    @Override
    public void handleCommand(String command, String[] args) {
        this.personDatabase.setRunning(false);
        System.out.println("Ended the program!");
    }
}
