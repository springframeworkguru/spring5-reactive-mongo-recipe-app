package guru.springframework.controllers;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class IndexController {

    RecipeService recipeService;

    @RequestMapping({ "", "/", "/index" })
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");

        model.addAttribute("recipes", recipeService.getRecipes().collectList().block());

        return "index";
    }
}
