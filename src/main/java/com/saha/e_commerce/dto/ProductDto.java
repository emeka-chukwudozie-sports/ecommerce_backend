package com.saha.e_commerce.dto;

import com.saha.e_commerce.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductDto {

    private Integer productId;
    @NotNull
    private String productName;
    @NotNull
    private String imageUrl;
    @NotNull
    private double price;
    @NotNull
    private String description;
    @NotNull
    private Integer categoryId;


    public ProductDto(String productName, String imageUrl, double price, String description, Integer categoryId) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    public ProductDto (Product product){
        this.setProductId(product.getProductId());
        this.setProductName(product.getProductName());
        this.setDescription(product.getDescription());
        this.setImageUrl(product.getImageUrl());
        this.setPrice(product.getPrice());
        this.setCategoryId(product.getCategory().getCategoryId());
    }

    public ProductDto() {
    }
}
