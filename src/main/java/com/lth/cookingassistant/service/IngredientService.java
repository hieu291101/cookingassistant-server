package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.Ingredient;

import java.util.Collection;

public interface IngredientService {
    Collection<Ingredient> searchIngredients(String keyword);
    Collection<Ingredient> getAllIngredient();
    Collection<Ingredient> list(int page, int size);
    long countIngredient();
    Ingredient get(Long id);
    Ingredient update(Ingredient ingredient);
    Ingredient create(Ingredient ingredient);
    Boolean delete(long id);
}
