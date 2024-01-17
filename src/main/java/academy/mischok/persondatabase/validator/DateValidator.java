package academy.mischok.persondatabase.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


public class DateValidator implements StringValidator { // dd.MM.yyyy

    private static Pattern pattern = Pattern.compile("^[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");
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

    public static void main(String[] args) {
        final DateValidator dateValidator = new DateValidator();
        System.out.println(dateValidator.isValidString("12.3.2000"));
        System.out.println(dateValidator.isValidString("99.99.9999"));
        System.out.println(dateValidator.isValidString("3.13.2000"));
    }
}