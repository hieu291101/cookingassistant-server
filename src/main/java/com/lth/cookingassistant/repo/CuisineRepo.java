package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Cuisine;
import com.lth.cookingassistant.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CuisineRepo extends JpaRepository<Cuisine, Integer> {
    Collection<Cuisine> findCuisinesByNameLike (String keyword);
}
