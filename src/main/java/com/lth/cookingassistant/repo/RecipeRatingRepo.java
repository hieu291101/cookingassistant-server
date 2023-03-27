package com.lth.cookingassistant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lth.cookingassistant.model.RecipeRating;

public interface RecipeRatingRepo extends JpaRepository<RecipeRating, Long>{
    @Query(value = "SELECT AVG(rr.ratingStar) from RecipeRating rr where rr.account.accountId <> ?1 and rr.recipe.recipeId <> ?2", nativeQuery = true)
    int getAverageStar(Long accountId, Long recipeId);

    boolean existsByAccount_AccountIdAndRecipe_RecipeId(Long accountId, Long recipeId);
}
