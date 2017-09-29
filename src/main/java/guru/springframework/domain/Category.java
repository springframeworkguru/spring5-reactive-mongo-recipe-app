package guru.springframework.domain;

import static lombok.AccessLevel.PRIVATE;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = { "recipes" })
@FieldDefaults(level = PRIVATE)
@Document
public class Category {

    @Id
    String id;

    String description;

    Set<Recipe> recipes;
}
