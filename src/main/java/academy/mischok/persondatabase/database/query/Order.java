package academy.mischok.persondatabase.database.query;

import academy.mischok.persondatabase.database.Column;
import lombok.Builder;

@Builder
public record Order(Column column, OrderType orderType) {
}
