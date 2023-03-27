package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.PasswordRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordRefreshToken, Long>  {
    Optional<PasswordRefreshToken> findByToken(String token);

    @Modifying
    int deleteByAccount(Account account);
}
