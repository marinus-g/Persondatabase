package academy.mischok.persondatabase.util;

import academy.mischok.persondatabase.database.Column;
import academy.mischok.persondatabase.database.Operator;
import academy.mischok.persondatabase.database.query.FilterType;
import academy.mischok.persondatabase.database.query.OrderType;
import academy.mischok.persondatabase.model.Person;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class Utility {

    private final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("d.M.yyyy");
    private final List<String> YES = Arrays.asList("yes", "y", "ja", "j");
    private final List<String> NO = Arrays.asList("no", "n", "nein");

    private final String filterableColumns;
    private final String possibleOperators;

    static {
        filterableColumns = Arrays.stream(Column.values()).map(Column::getClearName)
                .collect(Collectors.joining(", "));
        possibleOperators = Arrays.stream(Operator.values()).map(Operator::getClearName)
                .collect(Collectors.joining(", "));
    }

    @SneakyThrows
    public Date convertStringToDate(final String dateString) {
        return DATE_TIME_FORMATTER.parse(dateString);
    }

    public String convertDateToString(final Date date) {
        return DATE_TIME_FORMATTER.format(date);
    }

    public Long getLongFromScanner(Scanner scanner, String errorMessage) {
        Long number = null;
        do {
            try {
                number = Long.parseLong(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        } while (number == null);
        return number;
    }

    public Integer getIntegerFromScanner(Scanner scanner, String errorMessage) {
        return getIntegerFromScanner(scanner, errorMessage, false);
    }
    public Integer getIntegerFromScanner(Scanner scanner, String errorMessage, boolean nullable) {
        Integer number = null;
        if (!nullable) {
            do {
                try {
                    number = readIntegerFromScanner(scanner);
                } catch (Exception e) {
                    System.out.print(errorMessage);
                }
            } while (number == null);
        } else {
            try {
                number = readIntegerFromScanner(scanner);
            } catch (Exception ignored) {

            }
        }

        return number;
    }

    private Integer readIntegerFromScanner(Scanner scanner) throws Exception {
        return Integer.parseInt(scanner.nextLine());
    }

    public boolean getConfirmation(Scanner scanner, String errorMessage) {
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            if (YES.contains(input)) {
                return true;
            } else if (NO.contains(input)) {
                return false;
            } else {
                System.out.print(errorMessage);
            }
        }
    }

    public static Column getColumn(Scanner scanner) {
        Column column = null;
        while (column == null) {
            System.out.println("Gültige Spalten: " + filterableColumns);
            System.out.print("Gebe eine gültige Spalte an: ");
            column = Column.getFromClearName(scanner.nextLine());
        }
        return column;
    }

    public Operator getOperator(Scanner scanner) {
        Operator operator = null;
        while (operator == null) {
            System.out.println("Gültige Operatoren: " + possibleOperators);
            System.out.println("Gebe eine gültige Operator an");
            operator = Operator.getFromClearName(scanner.nextLine());
        }
        return operator;
    }

    public String getValue(Scanner scanner) {
        String str = "";
        while (str.isBlank()) {
            System.out.print("Gebe einen Wert an: ");
            str = scanner.nextLine();
        }
        return str;
    }

    public FilterType getFilterType(Scanner scanner) {
        FilterType filterType = null;
        while (filterType == null) {
            System.out.println("Gültige Filter Typen sind  \"AND\" und \"OR\"");
            System.out.print("Gebe den Filter Typ an: ");
            try {
                filterType = FilterType.valueOf(scanner.nextLine().toUpperCase());
            } catch (Exception ignored) {
            }
        }
        return filterType;
    }

    public OrderType getOrderType(Scanner scanner) {
        OrderType orderType = null;
        while (orderType == null) {
            System.out.println("Gültige Sortierungs Typen sind  \"Aufsteigend\" und \"Absteigend\"");
            System.out.print("Gebe den Sortierungs Typ an: ");
            try {
                orderType = OrderType.getByClearName(scanner.nextLine());
            } catch (Exception ignored) {
            }
        }
        return orderType;
    }

    public void displayDatabase(List<Person> personList) {
        final Map<Column, List<String>> table = new HashMap<>();
        for (Column value : Column.values()) {
            table.put(value, new ArrayList<>());
        }
        for (Person person : personList) {
            table.get(Column.ID).add(String.valueOf(person.getId()));
            table.get(Column.FIRST_NAME).add(String.valueOf(person.getFirstName()));
            table.get(Column.LAST_NAME).add(String.valueOf(person.getLastName()));
            table.get(Column.EMAIL).add(String.valueOf(person.getEmail()));
            table.get(Column.COUNTRY).add(String.valueOf(person.getCountry()));
            table.get(Column.BIRTHDAY).add(String.valueOf(Utility.convertDateToString(person.getBirthday())));
            table.get(Column.SALARY).add(String.valueOf(person.getSalary()));
            table.get(Column.BONUS).add(String.valueOf(person.getBonus()));
        }
        int i = 1;
        for (Map.Entry<Column, List<String>> entry : table.entrySet()) {
            final Column column = entry.getKey();
            final String longest = entry.getValue().stream().max(Comparator.comparingInt(String::length)).orElseThrow();
            String columnClearName = (++i == 0 ? " " : "| ") + column.getClearName() + " ";
            StringBuilder sb = new StringBuilder(columnClearName);
            if (longest.length() > columnClearName.length()) {
                final StringBuilder spaces = new StringBuilder();
                int amount = ((longest.length() - columnClearName.length()) + 2) / 2;
                IntStream.of(amount).forEach(value -> spaces.append(" "));
                columnClearName = sb.insert(columnClearName.contains("|") ? columnClearName.indexOf("|") : 0, spaces).append(spaces).toString();
            } else if (longest.length() == columnClearName.length()) {
                columnClearName = sb.insert(columnClearName.contains("|") ? columnClearName.indexOf("|") : 0, " ").append(" ").toString();
            } else {
                columnClearName = sb.insert(columnClearName.contains("|") ? columnClearName.indexOf("|") : 0, " ").append(" ").toString();
            }
            int length = columnClearName.length();
            for (int k = 0; k < entry.getValue().size(); k++) {
                String str = entry.getValue().get(k);
                StringBuilder fieldBuilder = new StringBuilder(str);
                final int missing = (length / 2) - 2;
                final StringBuilder spaces = new StringBuilder();
                IntStream.of(missing).forEach(value -> spaces.append(" "));
                fieldBuilder.insert(0, spaces).insert(0, "|").append(spaces).append(" ");
                entry.getValue().set(k, fieldBuilder.toString());
            }
            entry.getValue().addFirst(columnClearName);
        }
        for (int j = 0; j < table.get(Column.ID).size(); j++) {
            StringBuilder sb = new StringBuilder();
            for (Column value : Column.values()) {
                sb.append(table.get(value).get(j));
            }
            System.out.println(sb);
        }
    }
}