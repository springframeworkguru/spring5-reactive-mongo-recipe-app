package guru.springframework.domain;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = PRIVATE)
public class Notes {

    @Id
    String id;

    String recipeNotes;
}
