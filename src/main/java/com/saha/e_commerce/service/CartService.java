package com.saha.e_commerce.service;

import com.saha.e_commerce.dto.cart.AddToCartDto;
import com.saha.e_commerce.dto.cart.CartDto;
import com.saha.e_commerce.exception.CartItemException;
import com.saha.e_commerce.exception.ProductException;
import com.saha.e_commerce.model.User;

public interface CartService {
    void addProductToCart(AddToCartDto cartDto, User user) throws ProductException;

    CartDto listCartItems(User user);

    void deleteCartItem(User user, int cartItemId) throws CartItemException;
}
