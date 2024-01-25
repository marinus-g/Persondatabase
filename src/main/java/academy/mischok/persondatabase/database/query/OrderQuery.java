package academy.mischok.persondatabase.database.query;

import java.util.ArrayList;
import java.util.List;

/**
 * The OrderQuery class is responsible for building a database query with an order clause.
 * It maintains a list of Order objects, each representing a column and an order type (ascending or descending).
 * The class provides methods to add an order to the query and to build the final database query string.
 */
public class OrderQuery {

    /**
     * The list of Order objects representing the order clause of the query.
     */
    private final List<Order> orderChain = new ArrayList<>();

    /**
     * Adds an Order object to the order clause of the query.
     *
     * @param order the Order object to add
     * @return this OrderQuery object, allowing for method chaining
     */
    public OrderQuery add(Order order) {
        this.orderChain.add(order);
        return this;
    }

    /**
     * Builds the final database query string.
     * If the order clause is empty, it returns a string with just a semicolon.
     * Otherwise, it constructs the order clause using the column names and order types from the Order objects,
     * and appends it to the string " ORDER BY ".
     *
     * @return the final database query string
     */
    public String buildDatabaseQuery() {
        if (orderChain.isEmpty()) {
            return ";";
        }

        final StringBuilder sb = new StringBuilder(" ORDER BY ");
        for (int i = 0; i < orderChain.size(); i++) {
            final Order order = this.orderChain.get(i);
            if (i != 0) {
                sb.append(",")
                        .append(" ");
            }
            sb.append(order.column().getTableName())
                    .append(" ")
                    .append(order.orderType().getDatabaseName());
        }
        sb.append(";");
        return sb.toString();
    }
}