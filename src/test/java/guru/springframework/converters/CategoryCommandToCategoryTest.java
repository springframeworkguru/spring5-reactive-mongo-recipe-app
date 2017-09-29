package guru.springframework.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;

public class CategoryCommandToCategoryTest {

    private CategoryCommandToCategory converter = new CategoryCommandToCategory();

    @Test
    public void testNullObject() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new CategoryCommand())).isNotNull();
    }

    @Test
    public void convert() throws Exception {
        //given
        CategoryCommand command = new CategoryCommand();
        command.setId("1");
        command.setDescription("description");

        //when
        Category category = converter.convert(command);

        //then
        assertThat(category.getId()).isEqualTo(command.getId());
        assertThat(category.getDescription()).isEqualTo(command.getDescription());
    }

}