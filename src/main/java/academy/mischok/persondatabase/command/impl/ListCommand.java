package academy.mischok.persondatabase.command.impl;

import academy.mischok.persondatabase.command.AbstractCommand;
import academy.mischok.persondatabase.command.Command;
import academy.mischok.persondatabase.database.query.FilterQuery;
import academy.mischok.persondatabase.database.query.Order;
import academy.mischok.persondatabase.database.query.OrderQuery;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.Utility;

import java.util.List;
import java.util.Scanner;

@Command(name = "list", description = "Zeige eine Liste aller Personen an", aliases = "l")
public class ListCommand extends AbstractCommand {

    private final PersonService personService;

    public ListCommand(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void handleCommand(String command, String[] args, Scanner scanner) {
        final FilterQuery filterQuery = new FilterQuery(this.personService);
        System.out.print("Willst du die Ergebnisse sortiert haben? (ja/nein): ");
        if (Utility.getConfirmation(scanner, "Willst du die Ergebnisse sortiert haben? (ja/nein): ")) {
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