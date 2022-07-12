package com.saha.e_commerce.controllers;

import com.saha.e_commerce.dto.ProductDto;
import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.exception.CustomException;
import com.saha.e_commerce.payload.ApiResponse;
import com.saha.e_commerce.repositories.ProductRepository;
import com.saha.e_commerce.service.AuthTokenService;
import com.saha.e_commerce.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    private final WishListService wishListService;
    private final AuthTokenService authTokenService;
    private final ProductRepository productRepository;

    public WishListController(WishListService wishListService, AuthTokenService authTokenService, ProductRepository productRepository) {
        this.wishListService = wishListService;
        this.authTokenService = authTokenService;
        this.productRepository = productRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody ProductDto productDto,
     @RequestParam("token")String token) throws AuthenticationException, CustomException {
        wishListService.createWishList(productDto,token);
        return new ResponseEntity<>(new ApiResponse(true,"Added to wishlist"), HttpStatus.OK);
    }
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) throws AuthenticationException {
        return new ResponseEntity<>(wishListService.readAllWishList(token), HttpStatus.OK);
    }
}
