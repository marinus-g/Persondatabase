package academy.mischok.persondatabase.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.JDBCType;

/**
 * The Column enum represents the columns that can be used in a database query.
 * It includes values for ID, first name, last name, email, country, birthday, salary, and bonus.
 * Each column has a clear name, a table name, and a type.
 */
@Getter
@AllArgsConstructor
public enum Column {

    /**
     * Represents the ID column.
     */
    ID("ID", "id", JDBCType.BIGINT),

    /**
     * Represents the first name column.
     */
    FIRST_NAME("Vorname", "first_name", JDBCType.VARCHAR),

    /**
     * Represents the last name column.
     */
    LAST_NAME("Nachname", "last_name", JDBCType.VARCHAR),

    /**
     * Represents the email column.
     */
    EMAIL("Email", "email", JDBCType.VARCHAR),

    /**
     * Represents the country column.
     */
    COUNTRY("Land", "country", JDBCType.VARCHAR),

    /**
     * Represents the birthday column.
     */
    BIRTHDAY("Geburtstag", "birthday", JDBCType.DATE),

    /**
     * Represents the salary column.
     */
    SALARY("Gehalt", "salary", JDBCType.INTEGER),

    /**
     * Represents the bonus column.
     */
    BONUS("Bonus", "bonus", JDBCType.INTEGER);

    /**
     * The clear name of the column.
     */
    private final String clearName;

    /**
     * The table name of the column.
     */
    private final String tableName;

    /**
     * The type of the column.
     */
    private final JDBCType type;

    /**
     * Returns the Column that matches the given clear name.
     *
     * @param clearName the clear name to match
     * @return the matching Column, or null if no match is found
     */
    public static Column getFromClearName(String clearName) {
        for (Column value : values()) {
            if (value.getClearName().equalsIgnoreCase(clearName))
                return value;
        }
        return null;
    }
}