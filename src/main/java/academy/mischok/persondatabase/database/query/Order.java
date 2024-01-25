package academy.mischok.persondatabase.database.query;

import academy.mischok.persondatabase.database.Column;
import lombok.Builder;

/**
 * The Order class represents an order clause in a database query.
 * It is a record with two fields: a Column and an OrderType.
 * The Column represents the column to order by, and the OrderType represents the type of order (ascending or descending).
 * The class is annotated with @Builder, which allows for the creation of Order objects using a builder pattern.
 */
@Builder
public record Order(Column column, OrderType orderType) {
}