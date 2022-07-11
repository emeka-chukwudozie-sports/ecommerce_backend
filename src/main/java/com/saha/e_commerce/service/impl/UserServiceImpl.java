package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.dto.user.SignUpDto;
import com.saha.e_commerce.dto.user.SignUpResponseDto;
import com.saha.e_commerce.exception.CustomException;
import com.saha.e_commerce.model.AuthToken;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.repositories.UserRepository;
import com.saha.e_commerce.service.AuthTokenService;
import com.saha.e_commerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Repository
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final AuthTokenService tokenService;

    public UserServiceImpl(UserRepository userRepository, AuthTokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public SignUpResponseDto registerUser(SignUpDto signUpDto) throws CustomException {

        if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()){
            throw new CustomException("User with Email Already Exists");
        }
        String encryptedPassword = signUpDto.getPassword();
        try{
            encryptedPassword = hashPassword(signUpDto.getPassword());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("Password Hash Failed, {}",e.getMessage());
        }
        User newUser = new User(signUpDto.getFirstName(),signUpDto.getLastName(),
                signUpDto.getEmail(),encryptedPassword);
        try {
            final AuthToken token = new AuthToken(newUser);
            userRepository.save(newUser);
            tokenService.saveConfirmationToken(token);
            return new SignUpResponseDto("SUCCESS", "User Successfully signed Up");
        } catch (Exception exception){
            throw new CustomException(exception.getMessage());
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte [] digest = messageDigest.digest();
        String passwordHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        System.out.println(passwordHash+" is the hash for password: "+password);
        return passwordHash;
    }
}
