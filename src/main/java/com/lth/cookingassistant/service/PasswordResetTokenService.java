package com.lth.cookingassistant.service;

import com.lth.cookingassistant.exception.TokenRefreshException;
import com.lth.cookingassistant.model.PasswordRefreshToken;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.repo.PasswordResetTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    @Value("${lth.app.jwtRefreshExpirationMs}")
    private Long passwordResetMs;

    @Autowired
    private PasswordResetTokenRepo passwordResetTokenRepo;

    @Autowired
    private AccountRepo accountRepo;

    public Optional<PasswordRefreshToken> findByToken(String token) {
        return passwordResetTokenRepo.findByToken(token);
    }

    public PasswordRefreshToken createPasswordResetToken(Long userId) {
        PasswordRefreshToken passwordResetToken = new PasswordRefreshToken();

        passwordResetToken.setAccount(accountRepo.findById(userId).get());
        passwordResetToken.setExpiryDate(Instant.now().plusMillis(passwordResetMs));
        passwordResetToken.setToken(UUID.randomUUID().toString());

        passwordResetToken = passwordResetTokenRepo.save(passwordResetToken);
        return passwordResetToken;
    }

    public PasswordRefreshToken verifyExpiration(PasswordRefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            passwordResetTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return passwordResetTokenRepo.deleteByAccount(accountRepo.findById(userId).get());
    }
}