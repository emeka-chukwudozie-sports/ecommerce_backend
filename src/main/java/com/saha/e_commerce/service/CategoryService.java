package com.saha.e_commerce.service;

import com.saha.e_commerce.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category readCategory(String categoryName);

    Optional<Category> readCategoryById(Integer categoryId);

    void createCategory(Category category);

    List<Category> listCategories();

    void updateCategory(Integer categoryId, Category category);

    boolean categoryExistsById(Integer categoryId);
}
