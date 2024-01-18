package academy.mischok.persondatabase.database.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderType {

    ASCENDING("ASC", "Aufsteigend"),
    DESCENDING("DESC", "Absteigend");

    private final String databaseName;
    private final String clearName;

    public static OrderType getByClearName(String s) {
        for (OrderType value : values()) {
            if (value.getClearName().equalsIgnoreCase(s))
                return value;
        }
        return null;
    }
}
