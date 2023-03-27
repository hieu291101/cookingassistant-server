package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Recipe;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RecipeRepo extends JpaRepository<Recipe, Long>{
    Recipe findByTitle(String title);
    // Collection<Recipe> findDistinctByRecipesDetails(Collection<RecipesDetail> recipesDetails);
    List<Recipe> findAllByTitleContains (String title, Pageable pageable);
}
