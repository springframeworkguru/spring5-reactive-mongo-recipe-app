package guru.springframework.commands;

import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import guru.springframework.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class RecipeCommand {
    private String id;

    @NotBlank
    @Size(min = 3, max = 255)
    String description;

    @Min(1)
    @Max(999)
    Integer prepTime;

    @Min(1)
    @Max(999)
    Integer cookTime;

    @Min(1)
    @Max(100)
    Integer servings;

    String source;

    @URL
    private String url;

    @NotBlank
    String directions;

    List<IngredientCommand> ingredients = new ArrayList<>();

    byte[] image;

    Difficulty difficulty;

    NotesCommand notes;

    List<CategoryCommand> categories = new ArrayList<>();
}
