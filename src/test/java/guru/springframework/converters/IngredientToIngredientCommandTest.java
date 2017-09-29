package guru.springframework.converters;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;

public class IngredientToIngredientCommandTest {

    private IngredientToIngredientCommand converter = new IngredientToIngredientCommand(
            new UnitOfMeasureToUnitOfMeasureCommand());

    private static Ingredient ingredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        ingredient.setAmount(ONE);
        ingredient.setDescription("Cheeseburger");

        return ingredient;
    }

    @Test
    public void testNullConvert() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new Ingredient())).isNotNull();
    }

    @Test
    public void testConvertNullUOM() {
        //given
        Ingredient ingredient = ingredient();

        //when
        IngredientCommand command = converter.convert(ingredient);

        //then
        assertThat(command.getUom()).isNull();
        assertThat(command.getId()).isEqualTo(ingredient.getId());
        assertThat(command.getAmount()).isEqualTo(ingredient.getAmount());
        assertThat(command.getDescription()).isEqualTo(ingredient.getDescription());
    }

    @Test
    public void testConvertWithUom() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId("2");

        Ingredient ingredient = ingredient();
        ingredient.setUom(uom);

        //when
        IngredientCommand command = converter.convert(ingredient);

        //then
        assertThat(command.getUom().getId()).isEqualTo(ingredient.getUom().getId());
        assertThat(command.getId()).isEqualTo(ingredient.getId());
        assertThat(command.getAmount()).isEqualTo(ingredient.getAmount());
        assertThat(command.getDescription()).isEqualTo(ingredient.getDescription());
    }
}