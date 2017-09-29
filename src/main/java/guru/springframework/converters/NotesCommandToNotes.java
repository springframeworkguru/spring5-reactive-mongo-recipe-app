package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return null;
        }

        Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}
