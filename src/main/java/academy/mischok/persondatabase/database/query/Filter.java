package academy.mischok.persondatabase.database.query;


import academy.mischok.persondatabase.database.Column;
import academy.mischok.persondatabase.database.Operator;
import lombok.Builder;

@Builder
public record Filter(Column column, FilterType filterType, Operator operator, String value) {

}