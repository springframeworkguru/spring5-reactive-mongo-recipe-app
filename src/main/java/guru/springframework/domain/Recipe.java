package guru.springframework.domain;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@Document
public class Recipe {

    @Id
    String id;
    String description;
    Integer prepTime;
    Integer cookTime;
    Integer servings;
    String source;
    String url;
    String directions;
    Set<Ingredient> ingredients = new HashSet<>();
    byte[] image;
    Difficulty difficulty;
    Notes notes;
    Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if (nonNull(notes)) {
            this.notes = notes;
        }
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }
}
