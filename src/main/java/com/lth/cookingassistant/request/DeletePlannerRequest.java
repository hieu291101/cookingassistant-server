package com.lth.cookingassistant.request;

import lombok.Data;

@Data
public class DeletePlannerRequest extends PlannerMealRequest{
    private Long recipeId;
}
