package com.saha.e_commerce.controllers;

import com.saha.e_commerce.dto.user.LoginDto;
import com.saha.e_commerce.dto.user.LoginResponseDto;
import com.saha.e_commerce.dto.user.SignUpDto;
import com.saha.e_commerce.dto.user.SignUpResponseDto;
import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.exception.CustomException;
import com.saha.e_commerce.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpDto signUpDto) throws CustomException {
        return userService.registerUser(signUpDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login (@RequestBody LoginDto loginDto) throws AuthenticationException, CustomException {
        return userService.loginUser(loginDto);

    }
}
