package academy.mischok.persondatabase.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.JDBCType;

@Getter
@AllArgsConstructor
public enum Column {
    ID("ID", "id", JDBCType.BIGINT),
    FIRST_NAME("Vorname", "first_name", JDBCType.VARCHAR),
    LAST_NAME("Nachname", "last_name", JDBCType.VARCHAR),
    EMAIL("Email", "email", JDBCType.VARCHAR),
    COUNTRY("Land", "country", JDBCType.VARCHAR),
    BIRTHDAY("Geburtstag", "birthday", JDBCType.DATE),
    SALARY("Gehalt", "salary", JDBCType.INTEGER),
    BONUS("Bonus", "bonus", JDBCType.INTEGER);

    private final String clearName;
    private final String tableName;
    private final JDBCType type;

    public static Column getFromClearName(String clearName) {
        for (Column value : values()) {
            if (value.getClearName().equalsIgnoreCase(clearName))
                return value;
        }
        return null;
    }
}