package academy.mischok.persondatabase.database.query;

import academy.mischok.persondatabase.database.Operator;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;

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

    public FilterQuery orderQuery(OrderQuery orderQuery) {
        this.orderQuery = orderQuery;
        return this;
    }

    public String buildFilter() {
        if (this.filterChain.isEmpty()) {
            return orderQuery != null ? orderQuery.buildDatabaseQuery() : ";";
        }
        // SELECT * FROM person WHERE first_name = 'Wolfgang' AND/OR last_name = 'x
        final StringBuilder sb = new StringBuilder("WHERE ");
        for (int i = 0; i < this.filterChain.size(); i++) {
            final Filter filter = this.filterChain.get(i);
            if (i != 0) {
                sb.append(" ")
                        .append(filter.filterType().name().toUpperCase())
                        .append(" ");
            }
            sb
                    .append(filter.operator() == Operator.EQUALS_IGNORE_CASE ?
                            "LOWER(" + filter.column().getTableName() + ")" : filter.column().getTableName())
                    .append(" ")
                    .append(filter.operator().getDatabaseOperator())
                    .append(" ")
                    .append("'")
                    .append(filter.operator() == Operator.EQUALS_IGNORE_CASE ? filter.value().toLowerCase() : filter.value())
                    .append("'");
        }
        if (orderQuery != null) {
            sb.append(orderQuery.buildDatabaseQuery());
        } else {
            sb.append(";");
        }
        return sb.toString();
    }

    public List<Person> build() {
        return this.personService.findByFilterQuery(this);
    }
}