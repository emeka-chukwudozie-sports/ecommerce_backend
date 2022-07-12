package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.model.AuthToken;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.repositories.AuthTokenRepository;
import com.saha.e_commerce.repositories.UserRepository;
import com.saha.e_commerce.service.AuthTokenService;
import com.saha.e_commerce.utils.MessageString;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public AuthTokenServiceImpl(AuthTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveConfirmationToken(AuthToken token) {
        tokenRepository.save(token);
    }

    @Override
    public Optional<AuthToken> getTokenForUser(User user) {
        return tokenRepository.findAuthTokenByUser(user);
    }

    @Override
    public User getUserForToken(String token) {
        Optional<AuthToken> authToken = tokenRepository.findAuthTokenByToken(token);
        if(authToken.isPresent() && Objects.nonNull(authToken.get().getUser())){
            return authToken.get().getUser();
        }
        return null;
    }

    @Override
    public void authenticate(String token) throws AuthenticationException {
       var authToken =  tokenRepository.findAuthTokenByToken(token);
        if(tokenRepository.findAuthTokenByToken(token).isEmpty() || Objects.isNull(token)){
            authToken.get().setValid(false);
            throw new AuthenticationException(MessageString.TOKEN_NOT_PRESENT);
        }
        if(Objects.isNull(getUserForToken(token))){
            authToken.get().setValid(false);
            throw new AuthenticationException(MessageString.INVALID_TOKEN+" : "+MessageString.NO_USER_FOR_TOKEN);
        }

        if(tokenExpired(token)){
            authToken.get().setValid(false);
            throw new AuthenticationException(MessageString.TOKEN_EXPIRED);
        }

        authToken.get().setValid(true);
        tokenRepository.save(authToken.get());
    }

    @Override
    public boolean tokenExpired(String token){
        return tokenRepository.findAuthTokenByToken(token).get().getExpiryDate().isBefore(LocalDateTime.now());
    }
}
