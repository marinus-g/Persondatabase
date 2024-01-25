package academy.mischok.persondatabase.database.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The OrderType enum represents the types of order that can be used in a database query.
 * It includes values for ascending and descending order.
 * Each order type has a database name and a clear name.
 */
@AllArgsConstructor
@Getter
public enum OrderType {

    /**
     * Represents the ascending order type.
     */
    ASCENDING("ASC", "Aufsteigend"),

    /**
     * Represents the descending order type.
     */
    DESCENDING("DESC", "Absteigend");

    /**
     * The name of the order type as it is used in the database.
     */
    private final String databaseName;

    /**
     * The clear name of the order type.
     */
    private final String clearName;

    /**
     * Returns the OrderType that matches the given clear name.
     *
     * @param s the clear name to match
     * @return the matching OrderType, or null if no match is found
     */
    public static OrderType getByClearName(String s) {
        for (OrderType value : values()) {
            if (value.getClearName().equalsIgnoreCase(s))
                return value;
        }
        return null;
    }
}