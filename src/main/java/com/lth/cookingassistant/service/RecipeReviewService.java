package com.lth.cookingassistant.service;

import java.util.Collection;

import com.lth.cookingassistant.model.RecipeReview;
import com.lth.cookingassistant.request.RecipeReviewRequest;

public interface RecipeReviewService {
    RecipeReview create(RecipeReviewRequest recipeReviewReq);
    Collection<RecipeReview> getReview( Long recipeId);
}
