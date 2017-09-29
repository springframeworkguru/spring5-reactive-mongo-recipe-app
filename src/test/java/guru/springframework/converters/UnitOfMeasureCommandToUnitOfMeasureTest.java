package guru.springframework.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private UnitOfMeasureCommandToUnitOfMeasure converter = new UnitOfMeasureCommandToUnitOfMeasure();

    @Test
    public void testNullParameter() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new UnitOfMeasureCommand())).isNotNull();
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId("1");
        command.setDescription("description");

        //when
        UnitOfMeasure uom = converter.convert(command);

        //then
        assertThat(uom.getId()).isEqualTo(command.getId());
        assertThat(uom.getDescription()).isEqualTo(command.getDescription());
    }

}