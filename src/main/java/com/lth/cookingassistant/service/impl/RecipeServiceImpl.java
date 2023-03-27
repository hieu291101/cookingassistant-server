package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Recipe;
import com.lth.cookingassistant.model.RecipesDetail;
import com.lth.cookingassistant.repo.RecipeRepo;
import com.lth.cookingassistant.request.RecipeFilter;
import com.lth.cookingassistant.request.RecipeRequest;
import com.lth.cookingassistant.service.RecipeService;
import com.lth.cookingassistant.utils.RecipeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepo recipeRepo;

    private final RecipeUtils recipeUtils;

    @Override
    public Recipe create(RecipeRequest recipeRequest) throws IllegalAccessException {
        Recipe recipe = new Recipe();
        recipeUtils.copyValuesFromRequest(recipeRequest, recipe);
        log.info("Saving a new recipe: {} {}", recipe.getAccount().getUsername(), recipe.getCategoriesSub().getName());

        return recipeRepo.save(recipe);
    }

    @Override
    public Collection<Recipe> list(int limit) {
        log.info("Fetching all recipes");
        return recipeRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Collection<Recipe> list(int page, int size) {
        log.info(String.format("Fetching recipes from %d to %d", page, page * size));
        return recipeRepo.findAll(PageRequest.of(page - 1, size)).toList();
    }

    @Override
    public Collection<Recipe> searchByTitle(String title, int page, int size) {
        log.info("Searching recipes by title: {}", title);
        return recipeRepo.findAllByTitleContains(title, PageRequest.of(page - 1, size));
    }

    @Override
    public Collection<Recipe> filter(int page, int size, String title, int totalTime, String cuisine) {
        log.info(String.format("Fetching recipes from %d to %d", page, page * size));

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        if(totalTime > 0)
            recipe.setTotalTime(totalTime);
        recipe.setCuisine(cuisine);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("total_time", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("cuisine", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Recipe> recipeExample = Example.of(recipe, exampleMatcher);
        return recipeRepo.findAll(recipeExample, PageRequest.of(page - 1, size)).toList();
    }

    @Override
    public Recipe get(Long id) {
        log.info("Fetching recipe by id: {}", id);
        return recipeRepo.findById(id).get();
    }

    @Override
    public Recipe update(Recipe recipe) {
        log.info("Updating recipe: {}", recipe.getTitle());
        long id = recipe.getRecipeId();

        if(!recipeRepo.existsById(id)) {
            throw new CANotFoundException(
                    new StringBuffer()
                            .append("Recipe '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        return recipeRepo.save(recipe);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting recipe by id: {}", id);
        recipeRepo.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean isRecipeExist(Long id) {
        return recipeRepo.existsById(id);
    }

    @Override
    public Boolean isTitleExist(String title) {
        return isRecipeExist(recipeRepo.findByTitle(title).getRecipeId());
    }

    @Override
    public Collection<Recipe> suggest(Collection<RecipesDetail> recipesDetails) {
        // return recipeRepo.findDistinctByRecipesDetails(recipesDetails);
        return null;
    }

    @Override
    public long countRecipe() {
        return recipeRepo.count();
    }
}
