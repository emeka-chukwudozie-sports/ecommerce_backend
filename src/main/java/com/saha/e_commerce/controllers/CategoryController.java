package com.saha.e_commerce.controllers;

import com.saha.e_commerce.model.Category;
import com.saha.e_commerce.payload.ApiResponse;
import com.saha.e_commerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     *This method creates a new Category only when no category has been created with
     * the name provided in request
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category){
        if(Objects.nonNull(categoryService.readCategory(category.getCategoryName()))){
            return new ResponseEntity<>(
                    new ApiResponse(false, "Category Already Exists"), HttpStatus.CONFLICT);
        } categoryService.createCategory(category);
        return new ResponseEntity<>(
                new ApiResponse(true, "Category created successfully"),HttpStatus.CREATED);
    }

    /**
     * This returns a list of all categories existing
     */
    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> allCategories = categoryService.listCategories();
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }

    /**
     * This method returns a single Category name and description if it has been created
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("categoryId")Integer categoryId){
        if (Objects.nonNull(categoryService.readCategoryById(categoryId)) && categoryService.categoryExistsById(categoryId)){
           Category category =  categoryService.readCategoryById(categoryId).get();
            return new ResponseEntity<>(new ApiResponse(true,
                    category.getCategoryName()+" : "+category.getDescription()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false,"Category doesn't exist"),HttpStatus.NOT_FOUND);
    }

    /**
     *This method checks if a category exists by the Id provided
     * It updates the existing category if its in existence
     */
    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId")Integer categoryId,
                                                      @Valid @RequestBody Category category){
        if(Objects.nonNull(categoryService.readCategoryById(categoryId))){
            categoryService.updateCategory(categoryId,category);
            return new ResponseEntity<>(new ApiResponse(true,"Category Updated"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false,"Category does not exist"), HttpStatus.NOT_FOUND);
    }
}
