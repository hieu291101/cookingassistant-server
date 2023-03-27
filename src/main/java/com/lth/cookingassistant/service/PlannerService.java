package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.EDayOfWeek;
import com.lth.cookingassistant.model.EMeal;
import com.lth.cookingassistant.model.Planner;
import com.lth.cookingassistant.model.Recipe;

public interface PlannerService {
    Planner addRecipeToPlanner(Long accountId, Long recipeId, EDayOfWeek dayOfWeek, EMeal meal);
    Boolean delete(Long id);
    Planner get(Long id);
    Planner terminate(Long id);
}
