package com.saha.e_commerce.repositories;

import com.saha.e_commerce.model.Cart;
import com.saha.e_commerce.model.Product;
import com.saha.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserAndProduct(User user, Product product);
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    Optional<Cart> findCartByUserAndProduct(User user, Product product);
}
