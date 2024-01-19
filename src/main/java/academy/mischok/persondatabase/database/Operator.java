package academy.mischok.persondatabase.database;

import lombok.Getter;

@Getter
public enum Operator {

    EQUALS("=", "WoGenau"),
    EQUALS_IGNORE_CASE("=", "Wo"),
    LIKE("LIKE", "Ähnlich"),
    NOT("<>", "Nicht"),
    BIGGER_THAN(">", "GrößerAls"),
    BIGGER_OR_EQUAL_THAN(">=", "GrößerGleich"),
    LESS_THAN("<", "KleinerAls"),
    LESS_OR_EQUAL_THAN("<=", "KleinerGleich")
    ;
    private final String databaseOperator;
    private final String clearName;

    Operator(String databaseOperator, String clearName) {
        this.databaseOperator = databaseOperator;
        this.clearName = clearName;
    }

    public static Operator getFromClearName(String str) {
        for (Operator value : values()) {
            if (value.getClearName().equalsIgnoreCase(str))
                return value;
        }
        return null;
    }

    public static boolean isMetricOperator(Operator operator) {
        return operator == Operator.BIGGER_OR_EQUAL_THAN || operator == Operator.BIGGER_THAN ||
                operator == Operator.LESS_OR_EQUAL_THAN || operator == Operator.LESS_THAN;
    }
}