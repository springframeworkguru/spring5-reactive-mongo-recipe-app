package guru.springframework.domain;

import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = { "description", "amount", "uom" })
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
public class Ingredient {

    String id = randomUUID().toString();

    String description;

    BigDecimal amount;

    UnitOfMeasure uom;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }
}
