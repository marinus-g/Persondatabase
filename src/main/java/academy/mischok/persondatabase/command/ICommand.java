package academy.mischok.persondatabase.command;

import java.util.Scanner;

public interface ICommand {


    default void handleCommand(String command, String[] args) {
    }

    default void handleCommand(String command, String[] args, Scanner scanner) {
    }
}