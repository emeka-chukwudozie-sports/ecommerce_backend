package com.saha.e_commerce.service;

import com.saha.e_commerce.model.AuthToken;
import com.saha.e_commerce.model.User;

import java.util.Optional;

public interface AuthTokenService {
    void saveConfirmationToken(AuthToken token);

    Optional<AuthToken> getTokenForUser(User user);
}
