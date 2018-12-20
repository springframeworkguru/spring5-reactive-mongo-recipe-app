package guru.springframework.commands;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@ToString
public class IngredientCommand {

    String id;

    String recipeId;

    String description;

    BigDecimal amount;

    UnitOfMeasureCommand uom;
}
