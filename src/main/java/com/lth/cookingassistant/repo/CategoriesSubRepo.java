package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.CategoriesSub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CategoriesSubRepo extends JpaRepository<CategoriesSub, Integer> {
    Collection<CategoriesSub> findCategoriesSubsByCategoriesMain_CategoryMainId ( int mainId);
}
