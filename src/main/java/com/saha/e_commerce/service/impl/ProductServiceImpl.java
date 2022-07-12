package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.dto.ProductDto;
import com.saha.e_commerce.exception.ProductException;
import com.saha.e_commerce.model.Category;
import com.saha.e_commerce.model.Product;
import com.saha.e_commerce.repositories.CategoryRepository;
import com.saha.e_commerce.repositories.ProductRepository;
import com.saha.e_commerce.service.ProductService;
import com.saha.e_commerce.utils.MessageString;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createProduct(ProductDto productDto, Category category) {
        Product newProduct = new Product();
        newProduct.setProductName(productDto.getProductName());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setImageUrl(productDto.getImageUrl());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setCategory(category);
        productRepository.save(newProduct);

    }

    @Override
    public Product getProductById(Integer productId) throws ProductException {
        if (!productRepository.existsById(productId)){
            throw new ProductException(MessageString.INVALID_PRODUCT);
        }
        return productRepository.getById(productId);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products =  productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product: products){
            productDtos.add(new ProductDto(product));
        }
        return productDtos;
    }

    @Override
    public void updateProduct(Integer productId, ProductDto productDto) throws ProductException {
        Product existingProduct = this.getProductById(productId);
        Category category = categoryRepository.getById(productDto.getCategoryId());
        existingProduct.setProductName(productDto.getProductName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.setCategory(category);
        productRepository.save(existingProduct);

    }

    @Override
    public boolean productExistByName(String productName) {
       return productRepository.findByProductName(productName).isPresent();
    }
}
