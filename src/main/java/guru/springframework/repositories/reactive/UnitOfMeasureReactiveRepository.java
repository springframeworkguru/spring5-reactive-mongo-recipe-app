package guru.springframework.repositories.reactive;

import guru.springframework.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

    public Mono<UnitOfMeasure> findByDescription(String description);
}
