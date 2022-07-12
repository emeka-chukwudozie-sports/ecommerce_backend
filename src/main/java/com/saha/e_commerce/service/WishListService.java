package com.saha.e_commerce.service;

import com.saha.e_commerce.dto.ProductDto;
import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.exception.CustomException;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.model.WishList;

import java.util.List;

public interface WishListService {

    void createWishList(ProductDto productDto,String token) throws AuthenticationException, CustomException;
    List<ProductDto> readAllWishList(String token) throws AuthenticationException;
}
