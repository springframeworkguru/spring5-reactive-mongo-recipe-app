package guru.springframework.controllers;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.IOException;
import java.io.UncheckedIOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageController {

    ImageService imageService;

    RecipeService recipeService;

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id).block());

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(id, file).block();

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) {
        recipeService.findCommandById(id)
                .filter(command -> nonNull(command.getImage()))
                .doOnNext(command -> {
                    response.setContentType(IMAGE_JPEG_VALUE);
                    try {
                        response.getOutputStream().write(command.getImage());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                })
                .block();
    }
}
