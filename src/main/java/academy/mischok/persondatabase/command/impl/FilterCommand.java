package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.database.Column;
import academy.mischok.persondatabase.database.Operator;
import academy.mischok.persondatabase.database.query.*;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.ScannerUtil;
import academy.mischok.persondatabase.util.Utility;

import java.util.List;
import java.util.Scanner;

/**
 * The FilterCommand class represents a command that filters the person list in the application.
 * It extends the AbstractCommand class and overrides the handleCommand method to provide the functionality for the filter command.
 * The class is annotated with the Command annotation to provide metadata about the command.
 */
@Command(name = "filter", description = "Filter the person list", aliases = "f", usage = "[nachname|beliebig]")
public class FilterCommand extends AbstractCommand {

    /**
     * The PersonService to use for person-related operations.
     */
    private final PersonService personService;

    /**
     * Constructs a FilterCommand object with the given PersonService.
     *
     * @param personService the PersonService to use for person-related operations
     */
    public FilterCommand(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Handles the filter command with the given command, arguments, and Scanner for input.
     * The method checks the arguments to determine the type of filter to apply, and then applies the filter.
     *
     * @param command the command to handle
     * @param args the arguments of the command
     * @param scanner the Scanner to use for input
     */
    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        if (args.length == 1 && args[0].equalsIgnoreCase("nachname")) {
            this.filterByLastName(scanner);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("beliebig")) {
            this.filterByColumns(scanner);
        } else {
            System.out.println("Bitte gebe an, ob du nach einem Nachnamen suchen willst, oder nach beliebigen Spalten.");
            System.out.println("Benutze \"" + command + " nachname\", um nach einem Nachnamen zu suchen.");
            System.out.println("Benutze \"" + command + " beliebig\", um eine komplexe Filteranfrage zu stellen.");
        }
    }

    /**
     * Filters the person list by last name.
     * The method prompts the user to enter a last name, and then applies the filter.
     *
     * @param scanner the Scanner to use for input
     */
    private void filterByLastName(Scanner scanner) {
        String lastName;
        do {
            System.out.print("Gebe einen Nachnamen an: ");
            lastName = scanner.nextLine();
        } while (lastName.isBlank());
        final FilterQuery filterQuery = new FilterQuery(this.personService);
        filterQuery.add(
                Filter.builder()
                        .column(Column.LAST_NAME)
                        .filterType(FilterType.AND)
                        .operator(Operator.LIKE)
                        .value(lastName).build()
        );
        System.out.print("Willst du die Ergebnisse noch sortiert haben? (ja/nein): ");
        if (ScannerUtil.getConfirmation(scanner, "Willst du die Ergebnisse noch sortiert haben? (ja/nein): ")) {
            int index = 0;
            OrderQuery orderQuery = new OrderQuery();
            filterQuery.orderQuery(orderQuery);
            while (index == 0 || !scanner.nextLine().equalsIgnoreCase("done")) {
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
        final List<Person> results = filterQuery.build();
        if (results.isEmpty()) {
            System.out.println("Es wurde keine Person mit diesem Nachnamen gefunden!");
            return;
        }
        System.out.println("Liste aller Personen mit dem Nachnamen \"" + lastName + "\"");
        System.out.println();
        Utility.displayDatabase(results);
    }

    /**
     * Filters the person list by multiple columns.
     * The method prompts the user to enter the columns and filter conditions, and then applies the filter.
     *
     * @param scanner the Scanner to use for input
     */
    private void filterByColumns(Scanner scanner) {
        FilterQuery query = new FilterQuery(this.personService);
        int index = 0;
        String str;
        while (index == 0 || !(str = scanner.nextLine()).equalsIgnoreCase("exit") &&
                !str.equalsIgnoreCase("done")) {
            query.add(
                    Filter.builder()
                            .column(ScannerUtil.getColumn(scanner))
                            .filterType(index == 0 ? FilterType.AND : ScannerUtil.getFilterType(scanner))
                            .operator(ScannerUtil.getOperator(scanner))
                            .value(ScannerUtil.getValue(scanner))
                            .build()
            );
            index++;
            System.out.print("Gebe \"done\" ein, um mit dem Filter suchen. \"exit\" um abzubrechen. Enter zum fortfahren: ");
        }
        if (str.equalsIgnoreCase("exit")) {
            System.out.println("Filter eingabe abgebrochen!");
            return;
        }
        System.out.print("Willst du die Ergebnisse noch sortiert haben? (ja/nein): ");
        if (ScannerUtil.getConfirmation(scanner, "Willst du die Ergebnisse noch sortiert haben? (ja/nein): ")) {
            index = 0;
            OrderQuery orderQuery = new OrderQuery();
            query.orderQuery(orderQuery);
            while (index == 0 || !scanner.nextLine().equalsIgnoreCase("done")) {
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
        final List<Person> result = query.build();
        if (result.isEmpty()) {
            System.out.println("Es wurde keine Person, welche dem Filter entspricht gefunden!");
            return;
        }
        Utility.displayDatabase(result);
    }
}
