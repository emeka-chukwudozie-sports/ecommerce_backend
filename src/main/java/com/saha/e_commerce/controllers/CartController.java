package com.saha.e_commerce.controllers;

import com.saha.e_commerce.dto.cart.AddToCartDto;
import com.saha.e_commerce.dto.cart.CartDto;
import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.exception.CartItemException;
import com.saha.e_commerce.exception.ProductException;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.payload.ApiResponse;
import com.saha.e_commerce.service.AuthTokenService;
import com.saha.e_commerce.service.CartService;
import com.saha.e_commerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final CartService cartService;
    private final AuthTokenService authTokenService;

    public CartController(ProductService productService, CartService cartService, AuthTokenService authTokenService) {
        this.productService = productService;
        this.cartService = cartService;
        this.authTokenService = authTokenService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto cartDto,
        @RequestParam("token")String token) throws AuthenticationException, ProductException {
        authTokenService.authenticate(token);
        User user = authTokenService.getUserForToken(token);
        cartService.addProductToCart(cartDto, user);
        return new ResponseEntity<>(new ApiResponse(true,"Product Added To Cart"), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token")String token)
            throws AuthenticationException {
        authTokenService.authenticate(token);
        User user = authTokenService.getUserForToken(token);
       CartDto cartDto =  cartService.listCartItems(user);
       return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable("cartItemId") int cartItemId,
       @RequestParam("token") String token) throws AuthenticationException, CartItemException {
        authTokenService.authenticate(token);
        User user = authTokenService.getUserForToken(token);
        cartService.deleteCartItem(user,cartItemId);
        return new ResponseEntity<>(new ApiResponse(true,"Cart Item removed"), HttpStatus.OK);

    }
}
