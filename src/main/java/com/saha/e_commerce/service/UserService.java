package com.saha.e_commerce.service;

import com.saha.e_commerce.dto.user.SignUpDto;
import com.saha.e_commerce.dto.user.SignUpResponseDto;
import com.saha.e_commerce.exception.CustomException;

public interface UserService {
    SignUpResponseDto registerUser(SignUpDto signUpDto) throws CustomException;
}
