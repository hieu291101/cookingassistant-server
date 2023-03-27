package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.EDayOfWeek;
import com.lth.cookingassistant.model.EMeal;
import com.lth.cookingassistant.model.Planner;
import com.lth.cookingassistant.model.PlannerMealofday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PlannerMealOfDayRepo extends JpaRepository<PlannerMealofday, Long> {
    boolean existsByPlanner(Planner planner);
    boolean existsByPlanner_DayOfWeekAndMealAndPlanner_Account_AccountIdAndMealDetails_Recipe_RecipeId(
        EDayOfWeek dayOfWeek,
        EMeal meal,
        Long accountId,
        Long recipeId
);
    PlannerMealofday findByPlannerAndCreatedDate(Planner planner, Date createdDate);
    List<PlannerMealofday> findAllByPlanner_DayOfWeekAndMealAndPlanner_Account_AccountId(
            EDayOfWeek dayOfWeek,
            EMeal meal,
            Long accountId
    );

    List<PlannerMealofday> findAllByPlanner_Account_AccountId(
            Long accountId
    );

    PlannerMealofday findByPlanner_DayOfWeekAndMealAndPlanner_Account_AccountIdAndMealDetails_Recipe_RecipeId(
            EDayOfWeek dayOfWeek,
            EMeal meal,
            Long accountId,
            Long recipeId
    );

    @Query(value = "DELETE p, pm, md " + 
        "from Planner p inner join PlannerMealofday pm on p.plannerId = pm.plannerId " + 
        "and PlannerMealofday pm inner join MealDetail md on pm.mealofdayId = md.mealofdayId " + 
        "where p.accountId = ?1 and md.recipeId = ?2", nativeQuery = true)
    int deleteByAccountAndRecipe(Long accountId, Long recipeId);
}
