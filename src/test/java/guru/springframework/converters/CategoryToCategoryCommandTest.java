package guru.springframework.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;

public class CategoryToCategoryCommandTest {

    private CategoryToCategoryCommand converter = new CategoryToCategoryCommand();

    @Test
    public void testNullObject() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObject() {
        assertThat(converter.convert(new Category())).isNotNull();
    }

    @Test
    public void convert() {
        //given
        Category category = new Category();
        category.setId("1");
        category.setDescription("descript");

        //when
        CategoryCommand command = converter.convert(category);

        //then
        assertThat(command.getId()).isEqualTo(category.getId());
        assertThat(command.getDescription()).isEqualTo(category.getDescription());
    }

}