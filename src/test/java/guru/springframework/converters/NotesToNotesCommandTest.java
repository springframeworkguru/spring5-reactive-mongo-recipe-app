package guru.springframework.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;

public class NotesToNotesCommandTest {

    private NotesToNotesCommand converter = new NotesToNotesCommand();

    @Test
    public void convert() {
        //given
        Notes notes = new Notes();
        notes.setId("1");
        notes.setRecipeNotes("Notes");

        //when
        NotesCommand command = converter.convert(notes);

        //then
        assertThat(command.getId()).isEqualTo(notes.getId());
        assertThat(command.getRecipeNotes()).isEqualTo(notes.getRecipeNotes());
    }

    @Test
    public void testNull() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new Notes())).isNotNull();
    }
}