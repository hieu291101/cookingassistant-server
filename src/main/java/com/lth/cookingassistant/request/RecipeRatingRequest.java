package com.lth.cookingassistant.request;

import lombok.Data;

@Data
public class RecipeRatingRequest extends RecipeReviewIdRequest{
    private int ratingStar = 5;
}
