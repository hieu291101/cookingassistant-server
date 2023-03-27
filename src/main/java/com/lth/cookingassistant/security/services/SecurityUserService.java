package com.lth.cookingassistant.security.services;

import com.lth.cookingassistant.model.PasswordRefreshToken;
import com.lth.cookingassistant.repo.PasswordResetTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SecurityUserService {
    @Autowired
    PasswordResetTokenRepo passwordResetTokenRepo;
    public String validatePasswordResetToken(String token) {
        final PasswordRefreshToken passToken = passwordResetTokenRepo.findByToken(token).get();

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordRefreshToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordRefreshToken passToken) {
        final Instant cal = Instant.now();
        return passToken.getExpiryDate().isBefore(cal);
    }
}
