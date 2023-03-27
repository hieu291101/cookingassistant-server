package com.lth.cookingassistant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lth.cookingassistant.model.PlannerMealofday;
import com.lth.cookingassistant.repo.MealDetailRepo;
import com.lth.cookingassistant.repo.PlannerMealOfDayRepo;
import com.lth.cookingassistant.repo.PlannerRepo;
import com.lth.cookingassistant.request.DeletePlannerRequest;
import com.lth.cookingassistant.request.PlannerMealRequest;
import com.lth.cookingassistant.service.PlannerMealOfDayService;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PlannerMealOfDayImpl implements PlannerMealOfDayService{
    @Autowired
    PlannerMealOfDayRepo plannerMealOfDayRepo;

    @Autowired
    MealDetailRepo mealDetailRepo;

    @Autowired
    PlannerRepo plannerRepo;

    @Override
    public List<PlannerMealofday> get(PlannerMealRequest plannerMealRequest) {
        log.info("Fetching planner by day: {}", plannerMealRequest.getDayOfWeek() + "-" + plannerMealRequest.getMeal());
        return plannerMealOfDayRepo.findAllByPlanner_DayOfWeekAndMealAndPlanner_Account_AccountId(
                plannerMealRequest.getDayOfWeek(),
                plannerMealRequest.getMeal(),
                plannerMealRequest.getAccountId()
        );
    }

    @Override
    public List<PlannerMealofday> getAll(Long accountId) {
        log.info("Fetching planner by accountId: {}", accountId );
        return plannerMealOfDayRepo.findAllByPlanner_Account_AccountId(
                accountId
        );
    }

    @Override
    public Boolean deleteByAccountAndRecipe(DeletePlannerRequest deletePlannerRequest) {
        PlannerMealofday pmod = plannerMealOfDayRepo.findByPlanner_DayOfWeekAndMealAndPlanner_Account_AccountIdAndMealDetails_Recipe_RecipeId(
            deletePlannerRequest.getDayOfWeek(),
            deletePlannerRequest.getMeal(),
            deletePlannerRequest.getAccountId(),
            deletePlannerRequest.getRecipeId()
        );
        
        if(pmod.getMealDetails() != null) 
            mealDetailRepo.deleteAll(pmod.getMealDetails());
        
        if(pmod != null) 
            plannerMealOfDayRepo.delete(pmod);

        if(pmod.getPlanner() != null) 
            plannerRepo.delete(pmod.getPlanner());
        return Boolean.TRUE;
    }
}
