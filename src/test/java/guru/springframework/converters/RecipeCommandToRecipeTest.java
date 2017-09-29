package guru.springframework.converters;

import static guru.springframework.domain.Difficulty.EASY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

public class RecipeCommandToRecipeTest {

    private RecipeCommandToRecipe converter = new RecipeCommandToRecipe(
            new CategoryCommandToCategory(),
            new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
            new NotesCommandToNotes()
    );

    private static CategoryCommand categoryCommand(String id) {
        CategoryCommand command = new CategoryCommand();
        command.setId(id);
        return command;
    }

    private static IngredientCommand ingredientCommand(String id) {
        IngredientCommand command = new IngredientCommand();
        command.setId(id);
        return command;
    }

    @Test
    public void testNullObject() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new RecipeCommand())).isNotNull();
    }

    @Test
    public void convert() {
        //given
        NotesCommand notes = new NotesCommand();
        notes.setId("4");

        RecipeCommand command = new RecipeCommand();
        command.setId("1");
        command.setCookTime(5);
        command.setPrepTime(7);
        command.setDescription("My Recipe");
        command.setDifficulty(EASY);
        command.setDirections("Directions");
        command.setServings(3);
        command.setSource("Source");
        command.setUrl("Some URL");
        command.setNotes(notes);

        command.getCategories().add(categoryCommand("1"));
        command.getCategories().add(categoryCommand("2"));

        command.getIngredients().add(ingredientCommand("1"));
        command.getIngredients().add(ingredientCommand("2"));

        //when
        Recipe recipe = converter.convert(command);

        //then
        assertThat(recipe.getId()).isEqualTo(command.getId());
        assertThat(recipe.getCookTime()).isEqualTo(command.getCookTime());
        assertThat(recipe.getPrepTime()).isEqualTo(command.getPrepTime());
        assertThat(recipe.getDescription()).isEqualTo(command.getDescription());
        assertThat(recipe.getDifficulty()).isEqualTo(command.getDifficulty());
        assertThat(recipe.getDirections()).isEqualTo(command.getDirections());
        assertThat(recipe.getServings()).isEqualTo(command.getServings());
        assertThat(recipe.getSource()).isEqualTo(command.getSource());
        assertThat(recipe.getUrl()).isEqualTo(command.getUrl());
        assertThat(recipe.getNotes().getId()).isEqualTo(command.getNotes().getId());
        assertThat(recipe.getCategories()).hasSize(2);
        assertThat(recipe.getIngredients()).hasSize(2);
    }

}