package com.lth.cookingassistant.service;

import java.util.Date;
import java.util.List;

import com.lth.cookingassistant.model.PlannerMealofday;
import com.lth.cookingassistant.request.DeletePlannerRequest;
import com.lth.cookingassistant.request.PlannerMealRequest;

public interface PlannerMealOfDayService {
    List<PlannerMealofday> get(PlannerMealRequest plannerMealRequest);
    List<PlannerMealofday> getAll(Long accountId);
    Boolean deleteByAccountAndRecipe(DeletePlannerRequest deletePlannerRequest);
}
