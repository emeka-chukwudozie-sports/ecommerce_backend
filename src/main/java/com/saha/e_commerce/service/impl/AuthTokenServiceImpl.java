package com.saha.e_commerce.service.impl;

import com.saha.e_commerce.model.AuthToken;
import com.saha.e_commerce.model.User;
import com.saha.e_commerce.repositories.AuthTokenRepository;
import com.saha.e_commerce.service.AuthTokenService;
import org.springframework.stereotype.Service;

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
}
