package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.Recipe;
import com.lth.cookingassistant.model.RecipesDetail;
import com.lth.cookingassistant.request.RecipeRequest;

import java.util.Collection;

public interface RecipeService {
    Recipe create(RecipeRequest recipeRequest) throws IllegalAccessException;
    Collection<Recipe> list(int limit);
    Collection<Recipe> list(int page, int size);
    Collection<Recipe> filter(int page, int size, String title, int totalTime, String cuisine);
    Recipe get(Long id);
    Recipe update(Recipe recipe);
    Boolean delete(Long id);
    Boolean isRecipeExist(Long id);
    Boolean isTitleExist(String title);
    Collection<Recipe> searchByTitle(String title, int page, int size);
    Collection<Recipe> suggest(Collection<RecipesDetail> recipesDetails);
    long countRecipe();
}
