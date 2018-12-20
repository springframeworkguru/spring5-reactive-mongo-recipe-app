package guru.springframework.converters;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    CategoryCommandToCategory categoryConverter;

    IngredientCommandToIngredient ingredientConverter;

    NotesCommandToNotes notesConverter;

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        Recipe recipe = new Recipe();

        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (nonNull(source.getCategories())) {
            source.getCategories()
                    .stream()
                    .map(categoryConverter::convert)
                    .forEach(recipe.getCategories()::add);
        }

        if (nonNull(source.getIngredients())) {
            source.getIngredients()
                    .stream()
                    .map(ingredientConverter::convert)
                    .forEach(recipe.getIngredients()::add);
        }

        return recipe;
    }
}
