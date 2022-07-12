package com.saha.e_commerce.service;

import com.saha.e_commerce.dto.ProductDto;
import com.saha.e_commerce.exception.ProductException;
import com.saha.e_commerce.model.Category;
import com.saha.e_commerce.model.Product;

import java.util.List;

public interface ProductService {
    void createProduct(ProductDto productDto, Category category);

    Product getProductById(Integer productId) throws ProductException;

    List<ProductDto> getAllProducts();

    void updateProduct(Integer productId, ProductDto productDto) throws ProductException;
    boolean productExistByName(String productName);
}
