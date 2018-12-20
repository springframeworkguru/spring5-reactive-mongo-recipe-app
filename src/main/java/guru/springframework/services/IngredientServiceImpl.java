package guru.springframework.services;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@Slf4j
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    RecipeReactiveRepository recipeRepository;

    UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeRepository
                .findById(recipeId)
                .publishOn(Schedulers.parallel())
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        Mono<Recipe> recipeMono = findRecipeById(command.getRecipeId());
        Mono<Ingredient> ingredientMono = findOrCreateIngredient(recipeMono, command);
        return recipeMono.and(ingredientMono)
                .publishOn(Schedulers.parallel())
                .doOnNext(recipeAndIngredient -> recipeRepository.save(recipeAndIngredient.getT1()))
                .map(recipeAndIngredient -> {
                    Ingredient ingredient = recipeAndIngredient.getT2();
                    Recipe recipe = recipeAndIngredient.getT1();

                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
                    ingredientCommand.setRecipeId(recipe.getId());
                    return ingredientCommand;
                })
                .defaultIfEmpty(new IngredientCommand());
    }

    private Mono<Recipe> findRecipeById(String id) {
        return recipeRepository.findById(id)
                .doOnTerminate((recipe, throwable) -> {
                    if (isNull(recipe)) {
                        log.error("Recipe not found for id: {}", id);
                    }
                });
    }

    private Mono<Ingredient> findOrCreateIngredient(Mono<Recipe> recipeMono, IngredientCommand command) {
        return recipeMono.flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .single()
                .and(unitOfMeasureRepository.findById(command.getUom().getId()))
                .doOnNext(updateIngredient(command))
                .map(Tuple2::getT1)
                .switchIfEmpty(addNewIngredientIntoRecipe(recipeMono, command));
    }

    private Consumer<Tuple2<Ingredient, UnitOfMeasure>> updateIngredient(IngredientCommand command) {
        return ingredientAndUnit -> {
            Ingredient ingredient = ingredientAndUnit.getT1();
            UnitOfMeasure unitOfMeasure = ingredientAndUnit.getT2();

            ingredient.setDescription(command.getDescription());
            ingredient.setAmount(command.getAmount());
            ingredient.setUom(unitOfMeasure);
        };
    }

    private Mono<Ingredient> addNewIngredientIntoRecipe(Mono<Recipe> recipeMono, IngredientCommand command) {
        return recipeMono
                .and(Mono.just(ingredientCommandToIngredient.convert(command)))
                .doOnNext(recipeAndNewIngredient -> {
                    Recipe recipe = recipeAndNewIngredient.getT1();
                    Ingredient ingredient = recipeAndNewIngredient.getT2();
                    recipe.addIngredient(ingredient);
                })
                .map(Tuple2::getT2);
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String idToDelete) {
        log.debug("Deleting ingredient {} from recipe {}", idToDelete, recipeId);
        return recipeRepository
                .findById(recipeId)
                .publishOn(Schedulers.parallel())
                .doOnNext(recipe -> {
                    log.debug("found recipe {}", recipe.getId());
                    recipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(idToDelete));
                    recipeRepository.save(recipe);
                })
                .doOnError(throwable -> log.error("Unexpected exception happened during obtaining recipe " + recipeId, throwable))
                .doOnTerminate((recipe, throwable) -> {
                    if (isNull(recipe)) {
                        log.debug("Recipe was not found {}", recipeId);
                    }
                })
                .then();
    }
}