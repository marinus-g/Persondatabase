package academy.mischok.persondatabase.util;

import academy.mischok.persondatabase.database.Column;
import academy.mischok.persondatabase.database.Operator;
import academy.mischok.persondatabase.database.query.FilterType;
import academy.mischok.persondatabase.database.query.OrderType;
import academy.mischok.persondatabase.validator.StringValidator;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The ScannerUtil class provides utility methods for handling user input from the console.
 * It includes methods for reading integers, longs, booleans, Columns, Operators, FilterTypes, OrderTypes and valid strings from the console.
 */
@UtilityClass
public class ScannerUtil {

    /**
     * The list of filterable columns.
     */
    private final String filterableColumns;

    /**
     * The list of possible operators.
     */
    private final String possibleOperators;

    /**
     * List of strings that are considered as "yes"
     */
    private final List<String> YES = Arrays.asList("yes", "y", "ja", "j");

    /**
     * List of strings that are considered as "no"
     */
    private final List<String> NO = Arrays.asList("no", "n", "nein");

    static {
        filterableColumns = Arrays.stream(Column.values()).map(Column::getClearName)
                .collect(Collectors.joining(", "));
        possibleOperators = Arrays.stream(Operator.values()).map(Operator::getClearName)
                .collect(Collectors.joining(", "));
    }

    /**
     * Reads a long value from the console.
     *
     * @param scanner the Scanner to read from
     * @param errorMessage the error message to display if the input is not a valid long
     * @return the long value
     */
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

    /**
     * Reads an integer value from the console.
     *
     * @param scanner the Scanner to read from
     * @param errorMessage the error message to display if the input is not a valid integer
     * @return the integer value
     */
    public Integer getIntegerFromScanner(Scanner scanner, String errorMessage) {
        return getIntegerFromScanner(scanner, errorMessage, false);
    }

    /**
     * Reads an integer value from the console.
     *
     * @param scanner the Scanner to read from
     * @param errorMessage the error message to display if the input is not a valid integer
     * @param nullable boolean if the return value can be null
     * @return the integer value
     */
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

    /**
     * Reads an integer value from the console.
     *
     * @param scanner the Scanner to read from
     * @return the integer value
     */
    private Integer readIntegerFromScanner(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }


    /**
     * Reads a boolean value from the console.
     *
     * @param scanner the Scanner to read from
     * @param errorMessage the error message to display if the input is not a valid boolean
     * @return the boolean value
     */
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

    /**
     * Reads a Column value from the console.
     *
     * @param scanner the Scanner to read from
     * @return the Column value
     */
    public static Column getColumn(Scanner scanner) {
        Column column = null;
        while (column == null) {
            System.out.println("Gültige Spalten: " + filterableColumns);
            System.out.print("Gebe eine gültige Spalte an: ");
            column = Column.getFromClearName(scanner.nextLine());
        }
        return column;
    }

    /**
     * Reads an Operator value from the console.
     *
     * @param scanner the Scanner to read from
     * @return the Operator value
     */
    public Operator getOperator(Scanner scanner) {
        Operator operator = null;
        while (operator == null) {
            System.out.println("Gültige Operatoren: " + possibleOperators);
            System.out.println("Gebe eine gültige Operator an");
            operator = Operator.getFromClearName(scanner.nextLine());
        }
        return operator;
    }

    /**
     * Reads a string value from the console.
     *
     * @param scanner the Scanner to read from
     * @return the string value
     */
    public String getValue(Scanner scanner) {
        String str = "";
        while (str.isBlank()) {
            System.out.print("Gebe einen Wert an: ");
            str = scanner.nextLine();
        }
        return str;
    }

    /**
     * Reads a FilterType value from the console.
     *
     * @param scanner the Scanner to read from
     * @return the FilterType value
     */
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

    /**
     * Reads an OrderType value from the console.
     *
     * @param scanner the Scanner to read from
     * @return the OrderType value
     */
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

    /**
     * Reads a valid string from the console.
     *
     * @param scanner the Scanner to read from
     * @param validator the StringValidator to use for validation
     * @param message the message to display before reading the input
     * @return the valid string
     */
    public static String getValidString(Scanner scanner, StringValidator validator, String message) {
        String name;
        do {
            System.out.print(message);
            name = scanner.nextLine();
        } while (!validator.isValidString(name));
        return name;
    }

}