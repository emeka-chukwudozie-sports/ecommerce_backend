package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.dto.user.LoginDto;
import com.saha.e_commerce.dto.user.LoginResponseDto;
import com.saha.e_commerce.dto.user.SignUpDto;
import com.saha.e_commerce.dto.user.SignUpResponseDto;
import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.exception.CustomException;
import com.saha.e_commerce.model.AuthToken;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.repositories.UserRepository;
import com.saha.e_commerce.service.AuthTokenService;
import com.saha.e_commerce.service.UserService;
import static com.saha.e_commerce.utils.MessageString.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Repository
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final AuthTokenService tokenService;

    public UserServiceImpl(UserRepository userRepository, AuthTokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public SignUpResponseDto registerUser(SignUpDto signUpDto) throws CustomException {

        if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()){
            throw new CustomException(EMAIL_EXISTS);
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
            return new SignUpResponseDto("SUCCESS", USER_SIGNED_UP);
        } catch (Exception exception){
            throw new CustomException(exception.getMessage());
        }
    }

    @Override
    public LoginResponseDto loginUser(LoginDto loginDto) throws CustomException, AuthenticationException {
        User user = userRepository.findByEmail(loginDto.getEmail()).
       orElseThrow(() -> new CustomException(NO_USER_WITH_EMAIL));
        try {
            if (!user.getPassword().equals(hashPassword(loginDto.getPassword()))){
                throw new AuthenticationException(WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error hashing password: {}",e.getMessage());
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
        AuthToken userToken = tokenService.getTokenForUser(user)
                .orElseThrow(() -> new AuthenticationException(TOKEN_NOT_PRESENT));
        if(tokenService.tokenExpired(userToken.getToken())){
            //todo: extend the expiry time
            System.out.println("Token expired");
            userToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            tokenService.saveConfirmationToken(userToken);
        }
        tokenService.authenticate(userToken.getToken());
       return (userToken.isValid())?(new LoginResponseDto(userToken.getToken(), "SUCCESS")):
               (new LoginResponseDto("Token Expired: Authentication Error", "FAILURE"));
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
