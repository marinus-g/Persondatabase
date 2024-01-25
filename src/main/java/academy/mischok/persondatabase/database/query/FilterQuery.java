package academy.mischok.persondatabase.database.query;

import academy.mischok.persondatabase.database.Operator;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.util.Utility;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;

/**
 * The FilterQuery class is responsible for building a database query with a filter clause.
 * It maintains a list of Filter objects, each representing a condition in the filter clause.
 * The class provides methods to add a filter to the query, to set an order query, and to build the final database query string.
 */
public class FilterQuery {

    /**
     * The PersonService object used to find persons by filter query.
     */
    private final PersonService personService;

    /**
     * The list of Filter objects representing the filter clause of the query.
     */
    private final List<Filter> filterChain = new ArrayList<>();

    /**
     * The OrderQuery object representing the order clause of the query.
     */
    private OrderQuery orderQuery;


    /**
     * Constructs a FilterQuery object with the given PersonService.
     *
     * @param personService the PersonService to use
     */
    public FilterQuery(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Adds a Filter object to the filter clause of the query.
     *
     * @param filter the Filter object to add
     * @return this FilterQuery object, allowing for method chaining
     */
    public FilterQuery add(Filter filter) {
        filterChain.add(filter);
        return this;
    }

    /**
     * Sets the OrderQuery object for the order clause of the query.
     *
     * @param orderQuery the OrderQuery object to set
     * @return this FilterQuery object, allowing for method chaining
     */
    @SuppressWarnings("UnusedReturnValue")
    public FilterQuery orderQuery(OrderQuery orderQuery) {
        this.orderQuery = orderQuery;
        return this;
    }

    /**
     * Builds the final database query string.
     * If the filter clause is empty, it returns the order query if it exists, or a string with just a semicolon.
     * Otherwise, it constructs the filter clause using the column names, operators, and values from the Filter objects,
     * and appends it to the string "WHERE ".
     *
     * @return the final database query string
     */
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

    /**
     * Maps the filter value based on the operator and column type.
     *
     * @param filter the Filter object to map
     * @return the mapped filter value
     */
    private String mapFilterValue(final Filter filter) {
        if (filter.operator() == Operator.EQUALS_IGNORE_CASE) {
            return filter.value().toLowerCase();
        }
        if (filter.operator() == Operator.LIKE && filter.column().getType() == JDBCType.VARCHAR) {
            return "%" + filter.value() + "%";
        }
        return filter.value();
    }

    /**
     * Maps the field name based on the operator and column type.
     *
     * @param filter the Filter object to map
     * @return the mapped field name
     */
    private String mapFieldName(final Filter filter) {
        if (filter.operator() == Operator.EQUALS_IGNORE_CASE && filter.column().getType() == JDBCType.VARCHAR) {
            return "LOWER(" + filter.column().getTableName() + ")";
        }
        return filter.column().getTableName();
    }

    /**
     * Maps the operator based on the operator, column type, and filter value.
     *
     * @param filter the Filter object to map
     * @return the mapped operator
     */
    public String mapOperator(final Filter filter) {
        if (filter.operator() == Operator.LIKE && filter.column().getType() != JDBCType.VARCHAR) {
            return Operator.EQUALS.getDatabaseOperator();
        }
        return Operator.isMetricOperator(filter.operator())
                && !Utility.isInteger(filter.value()) ? Operator.EQUALS.getDatabaseOperator()
                : filter.operator().getDatabaseOperator();
    }

    /**
     * Builds the filter query and finds persons by the filter query.
     *
     * @return the list of persons found by the filter query
     */
    public List<Person> build() {
        return this.personService.findByFilterQuery(this);
    }
}