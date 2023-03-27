package com.lth.cookingassistant.utils;

import com.lth.cookingassistant.model.Recipe;
import com.lth.cookingassistant.request.RecipeRequest;
import com.lth.cookingassistant.service.AccountService;
import com.lth.cookingassistant.service.CategoryService;
import com.lth.cookingassistant.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RecipeUtils extends GenericUtils<Recipe>{
    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    public void copyValuesFromRequest(RecipeRequest req, Recipe db) {
        if(!req.getTitle().isEmpty())
            db.setTitle(req.getTitle());

        if(!req.getBriefDescription().isEmpty())
            db.setBriefDescription(req.getBriefDescription());

        db.setAccessModifier(req.getAccessModifier());

        if(!req.getMainIngredient().isEmpty())
            db.setMainIngredient(req.getMainIngredient());

        if(!req.getCuisine().isEmpty())
            db.setCuisine(req.getCuisine());

        if(req.getYields() > 0)
            db.setYields(req.getYields());

        if(req.getTotalTime() > 0)
            db.setTotalTime(req.getTotalTime());

        if(req.getActiveTime() > 0)
            db.setActiveTime(req.getActiveTime());

        db.setPhotoPath(req.getPhotoPath());

        if(!req.getPreparation().isEmpty())
            db.setPreparation(req.getPreparation());
        
        if(accountService.isAccountExist(req.getAccountId())) {
            db.setAccount(accountService.get(req.getAccountId()));
        }

        if(categoryService.isCategoryExist(req.getCategoriesSubId())) {
            db.setCategoriesSub(categoryService.get(req.getCategoriesSubId()));
        }
    }
}
