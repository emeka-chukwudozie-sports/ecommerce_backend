package com.saha.e_commerce.repositories;

import com.saha.e_commerce.model.AuthToken;
import com.saha.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Integer> {
    Optional<AuthToken> findAuthTokenByUser(User user);
    Optional<AuthToken> findAuthTokenByToken(String token);
}
