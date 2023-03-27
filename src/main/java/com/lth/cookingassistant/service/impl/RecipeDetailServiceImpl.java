package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.model.RecipesDetail;
import com.lth.cookingassistant.repo.RecipeDetailRepo;
import com.lth.cookingassistant.service.RecipeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class RecipeDetailServiceImpl implements RecipeDetailService {
    private final RecipeDetailRepo recipeDetailRepo;
    @Override
    public Collection<RecipesDetail> suggestIngredient(List<String> ingredientName, int totalTime) {
        return recipeDetailRepo.findById_IngredientNameInAndRecipe_TotalTimeLessThanEqual(ingredientName, totalTime);
    }

    @Override
    public Collection<RecipesDetail> getIngredient(Long recipeId) {
        return recipeDetailRepo.findAllById_RecipeId(recipeId);
    }
}
