package guru.springframework.converters;

import static java.util.Objects.isNull;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (isNull(unitOfMeasure)) {
            return null;
        }

        UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
        uomc.setId(unitOfMeasure.getId());
        uomc.setDescription(unitOfMeasure.getDescription());
        return uomc;
    }
}
