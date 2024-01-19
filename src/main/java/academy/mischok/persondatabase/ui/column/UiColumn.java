package academy.mischok.persondatabase.ui.column;

import academy.mischok.persondatabase.database.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UiColumn {
    ID(100),
    FIRST_NAME(100),
    LAST_NAME(100),
    EMAIL(200),
    COUNTRY(100),
    BIRTHDAY(200),
    SALARY(100),
    BONUS(100);

    private final double size;

    public static Column toColumn(UiColumn uiColumn) {
        switch (uiColumn) {
            case ID -> {
                return Column.ID;
            }
            case FIRST_NAME -> {
                return Column.FIRST_NAME;
            }
            case LAST_NAME -> {
                return Column.LAST_NAME;
            }
            case EMAIL -> {
                return Column.EMAIL;
            }
            case COUNTRY -> {
                return Column.COUNTRY;
            }
            case BIRTHDAY -> {
                return Column.BIRTHDAY;
            }
            case SALARY -> {
                return Column.SALARY;
            }
            case BONUS -> {
                return Column.BONUS;
            }
        }
        return null;
    }

    public static UiColumn fromColumn(Column colum) {
        switch (colum) {
            case ID -> {
                return UiColumn.ID;
            }
            case FIRST_NAME -> {
                return UiColumn.FIRST_NAME;
            }
            case LAST_NAME -> {
                return UiColumn.LAST_NAME;
            }
            case EMAIL -> {
                return UiColumn.EMAIL;
            }
            case COUNTRY -> {
                return UiColumn.COUNTRY;
            }
            case BIRTHDAY -> {
                return UiColumn.BIRTHDAY;
            }
            case SALARY -> {
                return UiColumn.SALARY;
            }
            case BONUS -> {
                return UiColumn.BONUS;
            }
        }
        return null;
    }
}