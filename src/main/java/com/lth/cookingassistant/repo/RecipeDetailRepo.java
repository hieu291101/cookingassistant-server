package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.RecipesDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RecipeDetailRepo extends JpaRepository<RecipesDetail, Long> {
    Collection<RecipesDetail>
    findById_IngredientNameInAndRecipe_TotalTimeLessThanEqual
            (Collection<String> id_ingredientName, int totalTime);

    Collection<RecipesDetail> findAllById_RecipeId(Long recipeId);
}
