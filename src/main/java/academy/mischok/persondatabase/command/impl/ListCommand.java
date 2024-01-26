package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.database.query.FilterQuery;
import academy.mischok.persondatabase.database.query.Order;
import academy.mischok.persondatabase.database.query.OrderQuery;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.ScannerUtil;
import academy.mischok.persondatabase.util.Utility;

import java.util.List;
import java.util.Scanner;

/**
 * The ListCommand class represents a command that lists all persons in the application.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the list command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "list", description = "Zeige eine Liste aller Personen an", aliases = "l")
public class ListCommand extends AbstractCommand {

    /**
     * The PersonService to use for person-related operations.
     */
    private final PersonService personService;

    /**
     * Constructs a ListCommand object with the given PersonService.
     *
     * @param personService the PersonService to use for person-related operations
     */
    public ListCommand(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Handles the list command with the given command, arguments, and Scanner for input.
     * The method prompts the user to decide if they want the results sorted, and if so, allows them to specify the sorting order.
     * It then retrieves the list of persons and displays it.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     * @param scanner the Scanner to use for input
     */
    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        final FilterQuery filterQuery = new FilterQuery(this.personService);
        System.out.print("Willst du die Ergebnisse sortiert haben? (ja/nein): ");
        if (ScannerUtil.getConfirmation(scanner, "Willst du die Ergebnisse sortiert haben? (ja/nein): ")) {
            int index = 0;
            OrderQuery orderQuery = new OrderQuery();
            filterQuery.orderQuery(orderQuery);
            while (index == 0 || !(scanner.nextLine()).equalsIgnoreCase("done")) {
                orderQuery.add(
                        Order.builder()
                                .column(ScannerUtil.getColumn(scanner))
                                .orderType(ScannerUtil.getOrderType(scanner))
                                .build()
                );
                System.out.print("Gebe \"done\" ein, um mit dem Sortieren abzuschließen. Leertaste um nach mehren Spalten zu sortieren.: ");
                index++;
            }
        }
        final List<Person> result = filterQuery.build();

        if (result.isEmpty()) {
            System.out.println("Keine Personen in der Datenbank!");
            return;
        }
        System.out.println("Liste aller Personen: ");
        System.out.println();
        Utility.displayDatabase(result);
    }
}