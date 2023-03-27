package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.RecipeReview;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long>{
	Account findById(long id);
	Account findByUsername(String username);
	Account findByRecipeReviews(RecipeReview recipeReview);
	Optional<Account> findByEmail(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	Optional<Account> getAccountByPasswordResetToken(String password);
}
