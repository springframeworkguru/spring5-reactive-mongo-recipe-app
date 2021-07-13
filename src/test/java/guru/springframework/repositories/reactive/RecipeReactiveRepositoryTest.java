package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {

    public static final String NEW_RECIPE = "new recipe";
    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testRecipeSave() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setDescription(NEW_RECIPE);

        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();

        assertEquals(Long.valueOf(1), count);
    }

    @Test
    public void testFindByDescription() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setDescription(NEW_RECIPE);

        Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

        assertEquals(NEW_RECIPE, savedRecipe.getDescription());
    }
}