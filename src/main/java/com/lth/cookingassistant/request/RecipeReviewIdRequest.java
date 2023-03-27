package com.lth.cookingassistant.request;

import lombok.Data;

@Data
public class RecipeReviewIdRequest {
    private Long accountId;
    private Long recipeId; 
}
