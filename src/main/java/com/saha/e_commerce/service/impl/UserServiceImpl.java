package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.repositories.UserRepository;
import com.saha.e_commerce.service.UserService;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
