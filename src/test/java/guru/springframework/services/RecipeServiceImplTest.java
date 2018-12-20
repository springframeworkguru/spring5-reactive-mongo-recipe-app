package guru.springframework.services;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import reactor.test.StepVerifier;

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

        StepVerifier.create(recipeService.findById(recipe.getId()))
                .expectNext(recipe)
                .expectComplete()
                .verify();

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

        StepVerifier.create(recipeService.findCommandById(command.getId()))
                .expectNext(command)
                .expectComplete()
                .verify();

        verify(recipeReactiveRepository).findById(recipe.getId());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() {
        when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe()));

        StepVerifier.create(recipeService.getRecipes())
                .expectNextCount(1)
                .expectComplete()
                .verify();

        verify(recipeReactiveRepository).findAll();
        verify(recipeReactiveRepository, never()).findById(anyString());
    }

    @Test
    public void testDeleteById() {
        //given
        String idToDelete = "2";

        when(recipeReactiveRepository.deleteById(idToDelete)).thenReturn(Mono.empty());

        //when
        StepVerifier.create(recipeService.deleteById(idToDelete))
                .expectComplete()
                .verify();

        //then
        verify(recipeReactiveRepository).deleteById(idToDelete);
    }
}