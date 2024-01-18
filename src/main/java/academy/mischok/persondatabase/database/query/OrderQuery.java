package academy.mischok.persondatabase.database.query;

import java.util.ArrayList;
import java.util.List;

public class OrderQuery {

    private final List<Order> orderChain = new ArrayList<>();

    public OrderQuery add(Order order) {
        this.orderChain.add(order);
        return this;
    }

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