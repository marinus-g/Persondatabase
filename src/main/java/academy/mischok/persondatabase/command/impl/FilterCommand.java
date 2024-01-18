package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.database.Operator;
import academy.mischok.persondatabase.database.query.*;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.database.Column;
import academy.mischok.persondatabase.util.Utility;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

@Command(name = "filter", description = "Filter the person list", aliases = "f", usage = "[nachname|beliebig]")
public class FilterCommand extends AbstractCommand {

    private final PersonService personService;


    public FilterCommand(PersonService personService) {
        this.personService = personService;
    }

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

    private void filterByLastName(Scanner scanner) {
        String lastName = "";
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
        if (Utility.getConfirmation(scanner, "Willst du die Ergebnisse noch sortiert haben? (ja/nein): ")) {
            int index = 0;
            String str;
            OrderQuery orderQuery = new OrderQuery();
            filterQuery.orderQuery(orderQuery);
            while (index == 0 || !(str = scanner.nextLine()).equalsIgnoreCase("done")) {
                orderQuery.add(
                        Order.builder()
                                .column(Utility.getColumn(scanner))
                                .orderType(Utility.getOrderType(scanner))
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

    private void filterByColumns(Scanner scanner) {
        FilterQuery query = new FilterQuery(this.personService);
        int index = 0;
        String str;
        while (index == 0 || !(str = scanner.nextLine()).equalsIgnoreCase("exit") &&
                !str.equalsIgnoreCase("done")) {
            query.add(
                    Filter.builder()
                            .column(Utility.getColumn(scanner))
                            .filterType(index == 0 ? FilterType.AND : Utility.getFilterType(scanner))
                            .operator(Utility.getOperator(scanner))
                            .value(Utility.getValue(scanner))
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
        if (Utility.getConfirmation(scanner, "Willst du die Ergebnisse noch sortiert haben? (ja/nein): ")) {
            index = 0;
            OrderQuery orderQuery = new OrderQuery();
            query.orderQuery(orderQuery);
            while (index == 0 || !(str = scanner.nextLine()).equalsIgnoreCase("done")) {
                orderQuery.add(
                        Order.builder()
                                .column(Utility.getColumn(scanner))
                                .orderType(Utility.getOrderType(scanner))
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
