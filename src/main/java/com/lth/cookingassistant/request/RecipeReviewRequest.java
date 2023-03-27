package com.lth.cookingassistant.request;

import lombok.Data;

@Data
public class RecipeReviewRequest extends RecipeReviewIdRequest{
    private String reviewText;
}
