package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.dto.ProductDto;
import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.exception.CustomException;
import com.saha.e_commerce.model.Product;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.model.WishList;
import com.saha.e_commerce.repositories.ProductRepository;
import com.saha.e_commerce.repositories.WishListRepository;
import com.saha.e_commerce.service.AuthTokenService;
import com.saha.e_commerce.service.WishListService;
import static com.saha.e_commerce.utils.MessageString.INVALID_PRODUCT;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final AuthTokenService tokenService;
    private final ProductRepository productRepository;

    public WishListServiceImpl(WishListRepository wishListRepository,
       AuthTokenService tokenService, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.tokenService = tokenService;
        this.productRepository = productRepository;
    }

    @Override
    public void createWishList(ProductDto productDto,String token) throws AuthenticationException, CustomException {
        tokenService.authenticate(token);
        User user = new User();
        if(Objects.nonNull(tokenService.getUserForToken(token)))
            user = tokenService.getUserForToken(token);
        Product product = productRepository.findById(productDto.getProductId())
                .orElseThrow(() -> new CustomException(INVALID_PRODUCT));
        WishList wishList = new WishList(user,product);
        List<WishList> wishListsForProduct = wishListRepository.findAllByUserAndProduct(user,product);
        if(wishListsForProduct.isEmpty()){
            wishListRepository.save(wishList);
        }else {
            throw new CustomException("Product already added to wish List");
        }
    }

    @Override
    public List<ProductDto> readAllWishList(String token) throws AuthenticationException {
        tokenService.authenticate(token);
        List<ProductDto> productDtos = new ArrayList<>();
        User user = tokenService.getUserForToken(token);
        if(tokenService.getTokenForUser(user).get().isValid()) {
            List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
            wishLists.forEach(wishList -> productDtos.add(new ProductDto(wishList.getProduct())));
        }
            return productDtos;
    }
}
