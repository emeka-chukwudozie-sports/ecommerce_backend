package com.saha.e_commerce.controllers;

import com.saha.e_commerce.dto.ProductDto;
import com.saha.e_commerce.exception.ProductException;
import com.saha.e_commerce.model.Category;
import com.saha.e_commerce.payload.ApiResponse;
import com.saha.e_commerce.service.CategoryService;
import com.saha.e_commerce.service.ProductService;
import static com.saha.e_commerce.utils.MessageString.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct (@Valid @RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryService.readCategoryById(productDto.getCategoryId());
        if (optionalCategory.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false,INVALID_CATEGORY), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        if (productService.productExistByName(productDto.getProductName())){
            return new ResponseEntity<>(new ApiResponse(false,PRODUCT_EXISTS),HttpStatus.CONFLICT);
        }
        productService.createProduct(productDto, category);
        return new ResponseEntity<>(new ApiResponse(true, PRODUCT_CREATED), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId,
                                                     @Valid @RequestBody ProductDto productDto) throws ProductException {
        Optional<Category> optionalCategory  = categoryService.readCategoryById(productDto.getCategoryId());
        if(optionalCategory.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false,INVALID_CATEGORY),HttpStatus.NOT_FOUND);
        }
        Category category = optionalCategory.get();
        if (productService.getProductById(productDto.getProductId()) == null){
            return new ResponseEntity<>(new ApiResponse(false,INVALID_PRODUCT), HttpStatus.NOT_FOUND);
        }
        productService.updateProduct(productId, productDto);
        return new ResponseEntity<>(new ApiResponse(true,PRODUCT_UPDATED), HttpStatus.OK);
    }
}
