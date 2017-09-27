package guru.springframework.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {

    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveRepository.findById(recipe.getId())).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = recipeService.findById(recipe.getId()).block();

        assertThat(recipeReturned).isEqualTo(recipe);
        verify(recipeReactiveRepository).findById(recipe.getId());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void getRecipeCommandByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveRepository.findById(recipe.getId())).thenReturn(Mono.just(recipe));

        RecipeCommand command = new RecipeCommand();
        command.setId("1");

        when(recipeToRecipeCommand.convert(recipe)).thenReturn(command);

        RecipeCommand commandById = recipeService.findCommandById(command.getId()).block();

        assertThat(commandById).isEqualTo(command);

        verify(recipeReactiveRepository).findById(recipe.getId());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() {
        when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe()));

        List<Recipe> recipes = recipeService.getRecipes().collectList().block();

        assertThat(recipes).hasSize(1);

        verify(recipeReactiveRepository).findAll();
        verify(recipeReactiveRepository, never()).findById(anyString());
    }

    @Test
    public void testDeleteById() {
        //given
        String idToDelete = "2";

        when(recipeReactiveRepository.deleteById(idToDelete)).thenReturn(Mono.empty());

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeReactiveRepository).deleteById(idToDelete);
    }
}