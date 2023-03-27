package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public String execute(Connection<?> connection) {
        Account user = new Account();
        user.setUsername(connection.getDisplayName());
        user.setPassword(randomAlphabetic(8));
        accountRepo.save(user);
        return user.getUsername();
    }
}