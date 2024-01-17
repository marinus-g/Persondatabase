package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.command.ICommand;

@Command(name = "create", description = "Creates a new person")
public class CreateCommand implements ICommand {

    @Override
    public void handleCommand(String command, String[] args) {

        System.getenv("test")

    }
}