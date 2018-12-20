package guru.springframework.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;

public class NotesCommandToNotesTest {

    private NotesCommandToNotes converter = new NotesCommandToNotes();

    @Test
    public void testNullParameter() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new NotesCommand())).isNotNull();
    }

    @Test
    public void convert() {
        //given
        NotesCommand command = new NotesCommand();
        command.setId("1");
        command.setRecipeNotes("Notes");

        //when
        Notes notes = converter.convert(command);

        //then
        assertThat(notes.getId()).isEqualTo(command.getId());
        assertThat(notes.getRecipeNotes()).isEqualTo(command.getRecipeNotes());
    }

}