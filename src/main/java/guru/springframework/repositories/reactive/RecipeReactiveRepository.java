package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
