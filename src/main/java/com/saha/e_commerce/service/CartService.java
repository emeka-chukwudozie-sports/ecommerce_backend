package com.saha.e_commerce.service;

import com.saha.e_commerce.dto.AddToCartDto;
import com.saha.e_commerce.exception.ProductException;
import com.saha.e_commerce.model.User;

public interface CartService {
    void addProductToCart(AddToCartDto cartDto, User user) throws ProductException;
}
