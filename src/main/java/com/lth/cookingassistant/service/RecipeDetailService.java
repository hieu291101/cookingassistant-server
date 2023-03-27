package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.RecipesDetail;

import java.util.Collection;
import java.util.List;

public interface RecipeDetailService {
    Collection<RecipesDetail> suggestIngredient(List<String> ingredientName, int totalTime);
    Collection<RecipesDetail> getIngredient(Long recipeId);

}
