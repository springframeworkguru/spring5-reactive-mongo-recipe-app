package guru.springframework.converters;

import static guru.springframework.domain.Difficulty.EASY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;

public class RecipeToRecipeCommandTest {

    private RecipeToRecipeCommand converter = new RecipeToRecipeCommand(
            new CategoryToCategoryCommand(),
            new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
            new NotesToNotesCommand());

    @Test
    public void testNullObject() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new Recipe())).isNotNull();
    }

    @Test
    public void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");
        recipe.setCookTime(5);
        recipe.setPrepTime(7);
        recipe.setDescription("My Recipe");
        recipe.setDifficulty(EASY);
        recipe.setDirections("Directions");
        recipe.setServings(3);
        recipe.setSource("Source");
        recipe.setUrl("Some URL");

        Notes notes = new Notes();
        notes.setId("9");

        recipe.setNotes(notes);

        recipe.getCategories().add(new Category());
        recipe.getCategories().add(new Category());

        recipe.getIngredients().add(new Ingredient());
        recipe.getIngredients().add(new Ingredient());

        //when
        RecipeCommand command = converter.convert(recipe);

        //then
        assertThat(command.getId()).isEqualTo(recipe.getId());
        assertThat(command.getCookTime()).isEqualTo(recipe.getCookTime());
        assertThat(command.getPrepTime()).isEqualTo(recipe.getPrepTime());
        assertThat(command.getDescription()).isEqualTo(recipe.getDescription());
        assertThat(command.getDirections()).isEqualTo(recipe.getDirections());
        assertThat(command.getServings()).isEqualTo(recipe.getServings());
        assertThat(command.getSource()).isEqualTo(recipe.getSource());
        assertThat(command.getUrl()).isEqualTo(recipe.getUrl());
        assertThat(command.getNotes().getId()).isEqualTo(recipe.getNotes().getId());
        assertThat(command.getCategories()).hasSize(2);
        assertThat(command.getIngredients()).hasSize(2);
    }
}