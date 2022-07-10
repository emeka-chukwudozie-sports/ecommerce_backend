package com.saha.e_commerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @NotNull
    private String productName;

    @NotNull
    private String imageUrl;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    private double price;

    @NotNull
    private String description;

    public Product(String productName, String imageUrl, Category category, double price, String description) {
        super();
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public Product() {

    }
}
