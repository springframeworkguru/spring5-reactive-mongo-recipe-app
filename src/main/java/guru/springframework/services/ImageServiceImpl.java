package guru.springframework.services;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    RecipeReactiveRepository recipeReactiveRepository;

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
        return recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    try {
                        recipe.setImage(file.getBytes());
                        return recipe;
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                })
                .doOnNext(recipeReactiveRepository::save)
                .then();
    }
}
