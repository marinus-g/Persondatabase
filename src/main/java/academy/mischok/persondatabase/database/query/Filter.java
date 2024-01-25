package academy.mischok.persondatabase.database.query;

import academy.mischok.persondatabase.database.Column;
import academy.mischok.persondatabase.database.Operator;
import lombok.Builder;

/**
 * The Filter class represents a condition in a database query filter.
 * It is a record with four fields: a Column, a FilterType, an Operator, and a value.
 * The Column represents the column to filter by, the FilterType represents the type of logical operator (AND or OR),
 * the Operator represents the comparison operator (such as equals, not equals, less than, etc.), and the value is the value to compare the column's value with.
 * The class is annotated with @Builder, which allows for the creation of Filter objects using a builder pattern.
 */
@Builder
public record Filter(Column column, FilterType filterType, Operator operator, String value) {

}