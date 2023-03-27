package com.lth.cookingassistant.service;

import java.util.Collection;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.RecipeReview;
import com.lth.cookingassistant.request.LoginRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AccountService {
	Account create(Account account);
	Collection<Account> list(int limit);
	Collection<Account> list(int page, int size);
	Account get(Long id);
	Account update(Account account);
	Account findByUsername(String username);
	Account findByRecipeReviews(RecipeReview recipeReview);
	Boolean delete(Long id);
	Boolean isAccountExist(Long id);
	Boolean isUsernameExist(String username);
	Boolean isSignInSuccess(LoginRequest loginForm);
	UserDetails loadUserById(Long userId);
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	void changeAccountPassword(Account account, String newPassword);
}
