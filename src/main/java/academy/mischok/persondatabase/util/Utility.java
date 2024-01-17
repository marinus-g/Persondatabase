package academy.mischok.persondatabase.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class Utility {

    private final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("d.M.yyyy");

    @SneakyThrows
    public Date convertStringToDate(final String dateString) {
        return DATE_TIME_FORMATTER.parse(dateString);
    }
}