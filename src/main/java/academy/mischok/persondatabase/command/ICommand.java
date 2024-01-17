package academy.mischok.persondatabase.command;

public interface ICommand {


    void handleCommand(String command, String[] args);

}