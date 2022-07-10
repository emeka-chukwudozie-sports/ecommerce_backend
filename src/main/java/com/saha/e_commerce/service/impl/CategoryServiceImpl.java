package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.model.Category;
import com.saha.e_commerce.repositories.CategoryRepository;
import com.saha.e_commerce.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl  implements CategoryService {

    private  final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category readCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public Optional<Category> readCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public boolean categoryExistsById(Integer categoryId){
        return categoryRepository.findById(categoryId).isPresent();
    }

    @Override
    public void createCategory(Category category) {
         categoryRepository.save(category);
    }

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void updateCategory(Integer categoryId, Category category) {
        Category existingCategory = this.readCategoryById(categoryId).get();
        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setImageUrl(category.getImageUrl());
        categoryRepository.save(existingCategory);

    }
}
