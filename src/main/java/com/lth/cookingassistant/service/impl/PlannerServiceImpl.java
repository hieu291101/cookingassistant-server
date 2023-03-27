package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.*;
import com.lth.cookingassistant.repo.*;
import com.lth.cookingassistant.service.PlannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class PlannerServiceImpl implements PlannerService {

    @Autowired
    PlannerRepo plannerRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    RecipeRepo recipeRepo;

    @Autowired
    PlannerMealOfDayRepo plannerMealOfDayRepo;

    @Autowired
    MealDetailRepo mealDetailRepo;

    public Planner addRecipeToPlanner(Long accountId, Long recipeId, EDayOfWeek dayOfWeek, EMeal meal) {
        if(plannerMealOfDayRepo.existsByPlanner_DayOfWeekAndMealAndPlanner_Account_AccountIdAndMealDetails_Recipe_RecipeId(
            dayOfWeek, meal, accountId, recipeId))
            return null;

        Planner planner = new Planner();
        PlannerMealofday mealofday = new PlannerMealofday();
        MealDetail mealDetail = new MealDetail();
        Optional<Recipe> recipe = recipeRepo.findById(recipeId);
        log.info("Create a new plan for: {}", recipe.get().getTitle());

        if(!recipe.isEmpty()) {
            planner.setDayOfWeek(dayOfWeek);
            planner.setAccount(accountRepo.findById(accountId).get());
            planner = plannerRepo.save(planner);

            mealofday.setMeal(meal);
            mealofday.setPlanner(planner);
            mealofday = plannerMealOfDayRepo.save(mealofday);

            mealDetail.setRecipe(recipe.get());
            mealDetail.setPlannerMealofday(mealofday);
            mealDetailRepo.save(mealDetail);
            planner.getPlannerMealofdays().add(mealofday);
        }
    
        return plannerRepo.save(planner);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting planner by id: {}", id);
        plannerRepo.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Planner get(Long id) {
        log.info("Fetching planner by id: {}", id);
        return plannerRepo.findById(id).get();
    }

    @Override
    public Planner terminate(Long id) {
        log.info("terminate planner by id: {}", id);
//        Planner planner = plannerRepo.findById(id).get();
//        planner.setIsDelete((byte) 1);
//        plannerMealOfDayRepo.fin
//        plannerRepo.save
        return null;
    }
}
