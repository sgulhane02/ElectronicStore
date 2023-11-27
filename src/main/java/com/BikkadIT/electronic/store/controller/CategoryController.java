package com.BikkadIT.electronic.store.controller;

import com.BikkadIT.electronic.store.constants.AppConstant;
import com.BikkadIT.electronic.store.constants.UrlConstant;
import com.BikkadIT.electronic.store.dtos.CategoryDto;
import com.BikkadIT.electronic.store.payload.ApiResponseMessage;
import com.BikkadIT.electronic.store.payload.PageableResponse;
import com.BikkadIT.electronic.store.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UrlConstant.BASE_URL)
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        log.info("Entering request for create category data");
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        log.info("Completed request for create category data");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId)
    {
        log.info("Entering request for update the category data with category id :{}",categoryId);
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        log.info("Completed request for update category with category id :{}",categoryId);
        return new ResponseEntity<>(updateCategory,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
        log.info("Entering request for get category by id :{}",categoryId);
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        log.info("Completed request for get category by id :{}",categoryId);
        return new ResponseEntity<>(categoryById,HttpStatus.OK);

    }
    @GetMapping("/categories")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber",defaultValue = UrlConstant.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = UrlConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = UrlConstant.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = UrlConstant.SORT_DIRECTION,required = false) String sortDir
    ){
        log.info("Entering request for get all categories by pagination and sorting");
        PageableResponse<CategoryDto> allCategories = this.categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for get All categories By pagination And Sorting");
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        log.info("Entering Request for delete the category with category id:{}",categoryId);
        this.categoryService.deleteCategory(categoryId);
        log.info("Completed request for delete the category with category id :{}",categoryId);
        return new ResponseEntity<>(new ApiResponseMessage(),HttpStatus.OK);
    }
}
