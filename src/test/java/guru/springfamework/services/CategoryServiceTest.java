package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    public static final Long ID = 1L;
    public static final String NAME = "Jimmy";
    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getAllCategories() {

        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(this.categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> dtos = this.categoryService.getAllCategories();

        //then
        assertEquals(3,dtos.size());
    }

    @Test
    public void getCategoryByName() {
        //given:
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);
        when(this.categoryRepository.findByName(anyString())).thenReturn(category);

        //when
        CategoryDTO dto = this.categoryService.getCategoryByName(NAME);

        //then
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
    }
}