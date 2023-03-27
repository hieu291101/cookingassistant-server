package com.lth.cookingassistant.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.Recipe;
import com.lth.cookingassistant.model.RecipeRating;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.repo.RecipeRatingRepo;
import com.lth.cookingassistant.repo.RecipeRepo;
import com.lth.cookingassistant.request.RecipeRatingIdRequest;
import com.lth.cookingassistant.request.RecipeRatingRequest;
import com.lth.cookingassistant.service.RecipeRatingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeRatingServiceImpl implements RecipeRatingService{
    private final RecipeRatingRepo recipeRatingRepo;
    private final AccountRepo accountRepo;
    private final RecipeRepo recipeRepo;

    @Override
    public int getAverageStar(RecipeRatingIdRequest recipeRatingReq){
        log.info("create recipe rating: {}", recipeRatingReq.getRecipeId());
        return recipeRatingRepo.getAverageStar(recipeRatingReq.getAccountId(), recipeRatingReq.getRecipeId());
    }

    @Override
    public RecipeRating create(RecipeRatingRequest recipeRatingReq){
        Long accountId = recipeRatingReq.getAccountId();
        Long recipeId = recipeRatingReq.getRecipeId();
        int ratingStar = recipeRatingReq.getRatingStar();
        if(recipeRatingRepo.existsByAccount_AccountIdAndRecipe_RecipeId(accountId, recipeId)) {
            return null;
        }
        
        Optional<Account> ac = accountRepo.findById(accountId);
        Optional<Recipe> rc = recipeRepo.findById(recipeId);
        RecipeRating rr = new RecipeRating();
        if(ac.isPresent() && rc.isPresent() && ratingStar > 0 && ratingStar <= 5){
            rr.setAccount(ac.get());
            rr.setRecipe(rc.get());
            rr.setRatingStar(ratingStar);
        } else {
            return null;
        }
            
        return recipeRatingRepo.save(rr);
    }
}
