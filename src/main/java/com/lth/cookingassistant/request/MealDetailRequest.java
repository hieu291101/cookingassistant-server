package com.lth.cookingassistant.request;

import lombok.Data;

@Data
public class MealDetailRequest {
    private Long recipeId;
    private Long mealOfDayId;
}
