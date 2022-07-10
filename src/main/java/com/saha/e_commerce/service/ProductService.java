package com.saha.e_commerce.service;

import com.saha.e_commerce.dto.ProductDto;
import com.saha.e_commerce.model.Category;
import com.saha.e_commerce.model.Product;

import java.util.List;

public interface ProductService {
    void createProduct(ProductDto productDto, Category category);

    Product getProductById(Integer productId);

    List<ProductDto> getAllProducts();

    void updateProduct(Integer productId, ProductDto productDto);
    boolean productExistByName(String productName);
}
