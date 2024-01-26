package academy.mischok.persondatabase.database.query;

/**
 * The FilterType enum represents the types of logical operators that can be used in a database query filter.
 * It includes values for AND and OR.
 * AND is used to combine multiple conditions, all of which must be true for the row to be included in the result.
 * OR is used to combine multiple conditions, at least one of which must be true for the row to be included in the result.
 */
public enum FilterType {

    /**
     * Represents the AND logical operator.
     */
    AND,

    /**
     * Represents the OR logical operator.
     */
    OR

}