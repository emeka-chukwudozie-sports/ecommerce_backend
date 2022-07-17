package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.dto.cart.AddToCartDto;
import com.saha.e_commerce.dto.cart.CartDto;
import com.saha.e_commerce.dto.cart.CartItemDto;
import com.saha.e_commerce.exception.ProductException;
import com.saha.e_commerce.model.Cart;
import com.saha.e_commerce.model.Product;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.repositories.CartRepository;
import com.saha.e_commerce.service.CartService;
import com.saha.e_commerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public void addProductToCart(AddToCartDto cartDto, User user) throws ProductException {
       Product product =  productService.getProductById(cartDto.getProductId());
       if(cartDto.getQuantity() <= 0){
           throw new ProductException("Quantity must be greater than 0");
       }
       if(productAlreadyInCart(product,user)){
           Cart userCart = cartRepository.findCartByUserAndProduct(user, product).get();
           userCart.setQuantity(cartDto.getQuantity() + userCart.getQuantity());
           cartRepository.save(userCart);
       } else {
           Cart cart = new Cart(product, cartDto.getQuantity(), user);
           cartRepository.save(cart);
       }

    }

    @Override
    public CartDto listCartItems(User user) {
        List<Cart> userCart = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for(Cart cart: userCart){
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for(CartItemDto cartItemDto: cartItems){
            totalCost += cartItemDto.getQuantity() * cartItemDto.getProduct().getPrice();
        }
        return new CartDto(cartItems, totalCost);
    }

    private boolean productAlreadyInCart(Product product, User user){
        return cartRepository.findCartByUserAndProduct(user, product).isPresent();
    }


}
