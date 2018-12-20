package guru.springframework.converters;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    CategoryToCategoryCommand categoryConverter;

    IngredientToIngredientCommand ingredientConverter;

    NotesToNotesCommand notesConverter;

    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (isNull(source)) {
            return null;
        }

        RecipeCommand command = new RecipeCommand();

        command.setId(source.getId());
        command.setCookTime(source.getCookTime());
        command.setPrepTime(source.getPrepTime());
        command.setDescription(source.getDescription());
        command.setDifficulty(source.getDifficulty());
        command.setDirections(source.getDirections());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setImage(source.getImage());
        command.setNotes(notesConverter.convert(source.getNotes()));

        if (nonNull(source.getCategories())) {
            source.getCategories()
                    .stream()
                    .map(categoryConverter::convert)
                    .forEach(command.getCategories()::add);
        }

        if (nonNull(source.getIngredients())) {
            source.getIngredients()
                    .stream()
                    .map(ingredientConverter::convert)
                    .forEach(command.getIngredients()::add);
        }

        return command;
    }
}
