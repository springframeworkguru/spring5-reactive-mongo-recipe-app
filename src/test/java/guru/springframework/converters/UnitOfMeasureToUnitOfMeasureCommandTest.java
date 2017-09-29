package guru.springframework.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();

    @Test
    public void testNullObjectConvert() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObj() {
        assertThat(converter.convert(new UnitOfMeasure())).isNotNull();
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId("1");
        uom.setDescription("description");

        //when
        UnitOfMeasureCommand command = converter.convert(uom);

        //then
        assertThat(command.getId()).isEqualTo(uom.getId());
        assertThat(command.getDescription()).isEqualTo(uom.getDescription());
    }

}