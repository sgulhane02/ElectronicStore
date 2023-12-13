package com.BikkadIT.electronic.store.services;

import com.BikkadIT.electronic.store.dtos.CategoryDto;
import com.BikkadIT.electronic.store.entities.Category;
import com.BikkadIT.electronic.store.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    private Category category;

    @BeforeEach
    public void init() {

        category = Category
                .builder()
                .title("Mobile phones")
                .description("this is related to phones")
                .coverImage("mob.png")
                .build();
    }

    @Test
    public void createCategoryTest(){

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto category1 = categoryService.createCategory(modelMapper.map(category, CategoryDto.class));
        Assertions.assertNotNull(category1);
        Assertions.assertEquals("Mobile phones",category1.getTitle());

    }

    @Test
    public void updateCategoryTest(){
        String categoryId="";

        CategoryDto categoryDto=CategoryDto
                .builder()
                .title("Mobile phones")
                .description("this is related to phones")
                .coverImage("mob.png")
                .build();

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        Assertions.assertNotNull(categoryDto);
    }
}