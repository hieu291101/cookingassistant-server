package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.model.MealDetail;
import com.lth.cookingassistant.model.Planner;
import com.lth.cookingassistant.model.PlannerMealofday;
import com.lth.cookingassistant.repo.MealDetailRepo;
import com.lth.cookingassistant.repo.PlannerMealOfDayRepo;
import com.lth.cookingassistant.repo.PlannerRepo;
import com.lth.cookingassistant.service.MealDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MealDetailServiceImpl implements MealDetailService {
    @Autowired
    MealDetailRepo mealDetailRepo;

    @Autowired
    PlannerMealOfDayRepo plannerMealOfDayRepo;

    @Autowired
    PlannerRepo plannerRepo;
    @Override
    public MealDetail findMealDetailByPlannerMealOfDay(PlannerMealofday pmod) {
        return mealDetailRepo.findByPlannerMealofday(pmod);
    }

    @Override
    public Boolean delete(Long recipeId, Long mealOfdayId) {
        log.info("Deleting recipe by id: {}", recipeId);
        mealDetailRepo.deleteByRecipe_RecipeIdAndPlannerMealofday_MealofdayId(recipeId, mealOfdayId);
        PlannerMealofday pmod = plannerMealOfDayRepo.findById(mealOfdayId).get();
        plannerMealOfDayRepo.deleteById(mealOfdayId);
        Planner pln = plannerRepo.findById(pmod.getPlanner().getPlannerId()).get();
        if(pln != null) {
            pln.getPlannerMealofdays().remove(pmod);
        }
        plannerRepo.save(pln);
        return Boolean.TRUE;
    }
}
