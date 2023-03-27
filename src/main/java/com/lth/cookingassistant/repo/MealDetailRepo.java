package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.MealDetail;
import com.lth.cookingassistant.model.PlannerMealofday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface MealDetailRepo extends JpaRepository<MealDetail, Date> {
    MealDetail findByPlannerMealofday(PlannerMealofday pmod);
    void deleteByRecipe_RecipeIdAndPlannerMealofday_MealofdayId(Long recipeId, Long mealOfDayId);
}
