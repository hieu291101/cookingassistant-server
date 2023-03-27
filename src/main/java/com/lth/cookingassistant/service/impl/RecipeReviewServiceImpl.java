package com.lth.cookingassistant.service.impl;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.Recipe;
import com.lth.cookingassistant.model.RecipeReview;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.repo.RecipeRepo;
import com.lth.cookingassistant.repo.RecipeReviewRepo;
import com.lth.cookingassistant.request.RecipeReviewRequest;
import com.lth.cookingassistant.service.RecipeReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeReviewServiceImpl implements RecipeReviewService{
    private final RecipeReviewRepo recipeReviewRepo;
    private final AccountRepo accountRepo;
    private final RecipeRepo recipeRepo;

    @Override
    public RecipeReview create(RecipeReviewRequest recipeReviewReq){
        log.info("create recipe review: {}", recipeReviewReq.getAccountId());
        Optional<Account> ac = accountRepo.findById(recipeReviewReq.getAccountId());
        Optional<Recipe> rc = recipeRepo.findById(recipeReviewReq.getRecipeId());

        if(!ac.isPresent() || !rc.isPresent() || recipeReviewReq.getReviewText().isEmpty() )
            return null;
        
        RecipeReview rr = new RecipeReview();
        rr.setAccount(ac.get());
        rr.setRecipe(rc.get());
        rr.setReviewText(recipeReviewReq.getReviewText());

        return recipeReviewRepo.save(rr);
    }

    @Override 
    public Collection<RecipeReview> getReview(Long recipeId) {
        return recipeReviewRepo.findAllByRecipe_RecipeId(recipeId);
    }
}
