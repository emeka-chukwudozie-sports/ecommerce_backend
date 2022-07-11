package com.saha.e_commerce.service;

import com.saha.e_commerce.dto.user.LoginDto;
import com.saha.e_commerce.dto.user.LoginResponseDto;
import com.saha.e_commerce.dto.user.SignUpDto;
import com.saha.e_commerce.dto.user.SignUpResponseDto;
import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.exception.CustomException;

public interface UserService {
    SignUpResponseDto registerUser(SignUpDto signUpDto) throws CustomException;
    LoginResponseDto loginUser(LoginDto loginDto) throws CustomException, AuthenticationException;
}
