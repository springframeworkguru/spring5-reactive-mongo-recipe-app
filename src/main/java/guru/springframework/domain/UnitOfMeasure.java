package guru.springframework.domain;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = PRIVATE)
@Document
public class UnitOfMeasure {

    @Id
    String id;

    String description;
}
