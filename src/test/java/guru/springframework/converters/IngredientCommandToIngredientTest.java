package guru.springframework.converters;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;

public class IngredientCommandToIngredientTest {

    private IngredientCommandToIngredient converter = new IngredientCommandToIngredient(
            new UnitOfMeasureCommandToUnitOfMeasure());

    private static IngredientCommand command() {
        IngredientCommand command = new IngredientCommand();
        command.setId("1");
        command.setAmount(ONE);
        command.setDescription("Cheeseburger");

        return command;
    }

    @Test
    public void testNullObject() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new IngredientCommand())).isNotNull();
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("2");

        IngredientCommand command = command();
        command.setUom(unitOfMeasureCommand);

        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertThat(ingredient.getId()).isEqualTo(command.getId());
        assertThat(ingredient.getAmount()).isEqualTo(command.getAmount());
        assertThat(ingredient.getDescription()).isEqualTo(command.getDescription());
        assertThat(ingredient.getUom().getId()).isEqualTo(command.getUom().getId());
    }

    @Test
    public void convertWithNullUOM() {
        //given
        IngredientCommand command = command();

        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertThat(ingredient.getUom()).isNull();
        assertThat(ingredient.getId()).isEqualTo(command.getId());
        assertThat(ingredient.getAmount()).isEqualTo(command.getAmount());
        assertThat(ingredient.getDescription()).isEqualTo(command.getDescription());
    }

}