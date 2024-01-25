package academy.mischok.persondatabase.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Operator enum represents the types of operators that can be used in a database query.
 * It includes values for equals, equals ignore case, like, not, bigger than, bigger or equal than, less than, and less or equal than.
 */
@Getter
@AllArgsConstructor
public enum Operator {

    /**
     * Represents the equals operator.
     */
    EQUALS("=", "WoGenau"),

    /**
     * Represents the equals ignore case operator.
     */
    EQUALS_IGNORE_CASE("=", "Wo"),

    /**
     * Represents the like operator.
     */
    LIKE("LIKE", "Ähnlich"),

    /**
     * Represents the not operator.
     */
    NOT("<>", "Nicht"),

    /**
     * Represents the bigger than operator.
     */
    BIGGER_THAN(">", "GrößerAls"),

    /**
     * Represents the bigger or equal than operator.
     */
    BIGGER_OR_EQUAL_THAN(">=", "GrößerGleich"),

    /**
     * Represents the less than operator.
     */
    LESS_THAN("<", "KleinerAls"),

    /**
     * Represents the less or equal than operator.
     */
    LESS_OR_EQUAL_THAN("<=", "KleinerGleich");

    /**
     * The operator as it is used in the database.
     */
    private final String databaseOperator;

    /**
     * The clear name of the operator.
     */
    private final String clearName;

    /**
     * Returns the Operator that matches the given clear name.
     *
     * @param str the clear name to match
     * @return the matching Operator, or null if no match is found
     */
    public static Operator getFromClearName(String str) {
        for (Operator value : values()) {
            if (value.getClearName().equalsIgnoreCase(str))
                return value;
        }
        return null;
    }

    /**
     * Checks if the given operator is a metric operator.
     *
     * @param operator the operator to check
     * @return true if the operator is a metric operator, false otherwise
     */
    public static boolean isMetricOperator(Operator operator) {
        return operator == Operator.BIGGER_OR_EQUAL_THAN || operator == Operator.BIGGER_THAN ||
                operator == Operator.LESS_OR_EQUAL_THAN || operator == Operator.LESS_THAN;
    }
}