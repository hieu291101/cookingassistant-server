package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.CategoriesMain;
import com.lth.cookingassistant.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CategoriesMainRepo extends JpaRepository<CategoriesMain, Integer> {
    Collection<CategoriesMain> findCategoriesMainsByNameLike (String keyword);
}
