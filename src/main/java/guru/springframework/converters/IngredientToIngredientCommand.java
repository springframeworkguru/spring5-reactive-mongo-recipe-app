package guru.springframework.converters;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand command = new IngredientCommand();
        command.setId(ingredient.getId());
        command.setAmount(ingredient.getAmount());
        command.setDescription(ingredient.getDescription());
        command.setUom(uomConverter.convert(ingredient.getUom()));
        return command;
    }
}
