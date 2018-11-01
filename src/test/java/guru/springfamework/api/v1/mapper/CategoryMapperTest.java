package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    public static final String NAME = "Joe";
    public static final long ID = 1L;
    CategoryMapper mapper  = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        //given:
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        //when
        CategoryDTO dto = mapper.categoryToCategoryDTO(category);

        //then
        assertEquals(Long.valueOf(ID), dto.getId());
        assertEquals(NAME, dto.getName());
    }
}