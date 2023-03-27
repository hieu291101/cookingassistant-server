package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.RecipeRating;
import com.lth.cookingassistant.request.RecipeRatingIdRequest;
import com.lth.cookingassistant.request.RecipeRatingRequest;

public interface RecipeRatingService {
    int getAverageStar(RecipeRatingIdRequest recipeRatingReq);
    RecipeRating create(RecipeRatingRequest recipeRatingReq);
}
