package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.MealDetail;
import com.lth.cookingassistant.model.Planner;
import com.lth.cookingassistant.model.PlannerMealofday;

public interface MealDetailService {
    MealDetail findMealDetailByPlannerMealOfDay(PlannerMealofday pmod);
    Boolean delete(Long recipeId, Long mealOfdayId);

}
