package guru.springframework.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<Void> saveImageFile(String recipeId, MultipartFile file);
}
