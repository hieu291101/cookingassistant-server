package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {
    Collection<Ingredient> findIngredientsByNameLike (String keyword);
    Boolean existsByName(String name);
    Boolean existsByNameIn(List<String> names);
}
