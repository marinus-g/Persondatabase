package academy.mischok.persondatabase.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * The DateValidator class implements the StringValidator interface.
 * It provides a specific implementation of the isValidString method for validating dates.
 */
public class DateValidator implements StringValidator { // dd.MM.yyyy

    /**
     * The pattern for the date format dd.MM.yyyy
     */
    private static final Pattern pattern = Pattern.compile("^[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}$");

    /**
     * The DateTimeFormatter for the date format d.M.yyyy
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");

    /**
     * Checks if the provided string is a valid date.
     * A valid date matches the pattern "^[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}$" and can be parsed into a LocalDate.
     *
     * @param string the date to validate
     * @return true if the date is valid, false otherwise
     */
    @Override
    public boolean isValidString(String string) {
        if (pattern.matcher(string).matches()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                LocalDate.parse(string, DATE_TIME_FORMATTER);
                return true;
            } catch (DateTimeException e) {
                return false;
            }
        }
        return false;
    }
}