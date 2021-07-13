package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
    public Mono<Recipe> findByDescription(String description);
}
