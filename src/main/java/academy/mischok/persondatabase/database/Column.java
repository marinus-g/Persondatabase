package academy.mischok.persondatabase.database;

import lombok.Getter;

@Getter
public enum Column {
    ID("ID", "id"),
    FIRST_NAME("Vorname", "first_name"),
    LAST_NAME("Nachname", "last_name"),
    EMAIL("Email", "email"),
    COUNTRY("Land", "country"),
    BIRTHDAY("Geburtstag", "birthday"),
    SALARY("Gehalt", "salary"),
    BONUS("Bonus", "bonus");

    private final String clearName;
    private final String tableName;

    Column(String clearName, String tableName) {
        this.clearName = clearName;
        this.tableName = tableName;
    }

    public static Column getFromClearName(String clearName) {
        for (Column value : values()) {
            if (value.getClearName().equalsIgnoreCase(clearName))
                return value;
        }
        return null;
    }
}