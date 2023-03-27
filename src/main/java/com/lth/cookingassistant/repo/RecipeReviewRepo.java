package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.RecipeReview;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeReviewRepo extends JpaRepository<RecipeReview, Long>{
    Collection<RecipeReview> findAllByAccount_AccountIdAndRecipe_RecipeId(Long accountId, Long recipeId);
    Collection<RecipeReview> findAllByRecipe_RecipeId(Long recipeId);
}
