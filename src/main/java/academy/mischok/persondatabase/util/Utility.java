package academy.mischok.persondatabase.util;

import academy.mischok.persondatabase.database.Column;
import academy.mischok.persondatabase.model.Person;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

/**
 * The Utility class provides utility methods for the application.
 * It includes methods for converting strings to dates and vice versa, checking if a string is an integer,
 * and displaying the database.
 */
@UtilityClass
public class Utility {

    /**
     * The SimpleDateFormat for the date format d.M.yyyy
     */
    private final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("d.M.yyyy");

    /**
     * Converts a string to a Date.
     *
     * @param dateString the string to convert
     * @return the converted Date
     */
    @SneakyThrows
    public Date convertStringToDate(final String dateString) {
        return DATE_TIME_FORMATTER.parse(dateString);
    }

    /**
     * Converts a Date to a string.
     *
     * @param date the Date to convert
     * @return the converted string
     */
    public String convertDateToString(final Date date) {
        return DATE_TIME_FORMATTER.format(date);
    }

    /**
     * Checks if a string can be parsed to an integer.
     *
     * @param str the string to check
     * @return true if the string can be parsed to an integer, false otherwise
     */
    public boolean isInteger(final String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Displays the database.
     *
     * @param personList the list of persons to display
     */
    public void displayDatabase(List<Person> personList) {
        final Map<Column, List<String>> table = new HashMap<>();
        for (Column value : Column.values()) {
            table.put(value, new ArrayList<>());
        }
        for (Person person : personList) {
            table.get(Column.ID).add(String.valueOf(person.getId()));
            table.get(Column.FIRST_NAME).add(person.getFirstName());
            table.get(Column.LAST_NAME).add(person.getLastName());
            table.get(Column.EMAIL).add(person.getEmail());
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