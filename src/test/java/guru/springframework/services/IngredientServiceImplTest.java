package guru.springframework.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceImplTest {

    private IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(
            new UnitOfMeasureCommandToUnitOfMeasure());

    private IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(
            new UnitOfMeasureToUnitOfMeasureCommand());

    @Mock
    private RecipeReactiveRepository recipeRepository;

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    private IngredientService ingredientService;

    @Before
    public void setUp() {
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndId() {
        // ???
    }

    @Test
    public void findByRecipeIdAndReceiptIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("1");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeRepository.findById(recipe.getId())).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService
                .findByRecipeIdAndIngredientId(recipe.getId(), ingredient3.getId()).block();

        //when
        assertThat(ingredientCommand.getId()).isEqualTo(ingredient3.getId());
        verify(recipeRepository).findById(recipe.getId());
    }

    @Test
    public void testSaveRecipeCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");
        command.setUom(new UnitOfMeasureCommand());
        command.getUom().setId("1234");

        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(command.getRecipeId())).thenReturn(Mono.just(savedRecipe));
        when(unitOfMeasureRepository.findById(command.getUom().getId())).thenReturn(Mono.just(new UnitOfMeasure()));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertThat(savedCommand.getId()).isEqualTo(command.getId());

        verify(recipeRepository).findById(command.getRecipeId());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        // and
        Recipe recipe = new Recipe();
        recipe.setId("1");
        recipe.addIngredient(ingredient);
        // and
        when(recipeRepository.findById(recipe.getId())).thenReturn(Mono.just(recipe));

        //when
        ingredientService.deleteById(recipe.getId(), ingredient.getId()).block();

        //then
        verify(recipeRepository).findById(recipe.getId());
        verify(recipeRepository).save(any(Recipe.class));
    }
}