package guru.springframework.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Captor
    private ArgumentCaptor<Recipe> argumentCaptor;

    @Test
    public void saveImageFile() throws IOException {
        //given
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveRepository.findById(recipe.getId())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));

        //when
        StepVerifier.create(imageService.saveImageFile(recipe.getId(), multipartFile))
                .expectComplete()
                .verify();

        //then
        verify(recipeReactiveRepository).save(argumentCaptor.capture());

        Recipe savedRecipe = argumentCaptor.getValue();
        assertThat(savedRecipe.getImage()).isEqualTo(multipartFile.getBytes());
    }
}