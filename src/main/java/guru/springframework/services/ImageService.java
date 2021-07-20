package guru.springframework.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 7/3/17.
 */
public interface ImageService {

    Mono<Void> saveImageFile(String recipeId, MultipartFile file);
}
