package academy.mischok.persondatabase.database.query;

import academy.mischok.persondatabase.database.Operator;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.Utility;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;

public class FilterQuery {

    private final PersonService personService;
    private final List<Filter> filterChain = new ArrayList<>();
    private OrderQuery orderQuery;

    public FilterQuery(PersonService personService) {
        this.personService = personService;
    }

    public FilterQuery add(Filter filter) {
        filterChain.add(filter);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public FilterQuery orderQuery(OrderQuery orderQuery) {
        this.orderQuery = orderQuery;
        return this;
    }

    public String buildFilter() {
        if (this.filterChain.isEmpty()) {
            return orderQuery != null ? orderQuery.buildDatabaseQuery() : ";";
        }
        final StringBuilder sb = new StringBuilder("WHERE ");
        for (int i = 0; i < this.filterChain.size(); i++) {
            final Filter filter = this.filterChain.get(i);
            if (i != 0) {
                sb.append(" ")
                        .append(filter.filterType().name().toUpperCase())
                        .append(" ");
            }
            sb
                    .append(mapFieldName(filter))
                    .append(" ")
                    .append(mapOperator(filter))
                    .append(" ")
                    .append("'")
                    .append(mapFilterValue(filter))
                    .append("'");
        }
        if (orderQuery != null) {
            sb.append(orderQuery.buildDatabaseQuery());
        } else {
            sb.append(";");
        }
        return sb.toString();
    }

    private String mapFilterValue(final Filter filter) {
        if (filter.operator() == Operator.EQUALS_IGNORE_CASE) {
            return filter.value().toLowerCase();
        }
        if (filter.operator() == Operator.LIKE && filter.column().getType() == JDBCType.VARCHAR) {
            return "%" + filter.value() + "%";
        }
        return filter.value();
    }

    private String mapFieldName(final Filter filter) {
        if (filter.operator() == Operator.EQUALS_IGNORE_CASE && filter.column().getType() == JDBCType.VARCHAR) {
            return "LOWER(" + filter.column().getTableName() + ")";
        }
        return filter.column().getTableName();
    }

    public String mapOperator(final Filter filter) {
        if (filter.operator() == Operator.LIKE && filter.column().getType() != JDBCType.VARCHAR) {
            return Operator.EQUALS.getDatabaseOperator();
        }
        return Operator.isMetricOperator(filter.operator())
                && !Utility.isInteger(filter.value()) ? Operator.EQUALS.getDatabaseOperator()
                : filter.operator().getDatabaseOperator();
    }

    public List<Person> build() {
        return this.personService.findByFilterQuery(this);
    }
}