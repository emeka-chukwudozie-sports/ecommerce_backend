package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.exception.AuthenticationException;
import com.saha.e_commerce.model.AuthToken;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.repositories.AuthTokenRepository;
import com.saha.e_commerce.service.AuthTokenService;
import com.saha.e_commerce.utils.MessageString;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository tokenRepository;

    public AuthTokenServiceImpl(AuthTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
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
        if(tokenRepository.findAuthTokenByToken(token).isEmpty() || Objects.isNull(token)){
            throw new AuthenticationException(MessageString.TOKEN_NOT_PRESENT);
        }
        if(Objects.isNull(getUserForToken(token))){
            throw new AuthenticationException(MessageString.INVALID_TOKEN+" : "+MessageString.NO_USER_FOR_TOKEN);
        }

        if(tokenRepository.findAuthTokenByToken(token).get().getExpiryDate().isAfter(LocalDate.now())){
            throw new AuthenticationException(MessageString.TOKEN_EXPIRED);
        }

    }
}
