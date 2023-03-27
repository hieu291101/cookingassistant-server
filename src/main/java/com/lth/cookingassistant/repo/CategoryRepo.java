package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.CategoriesSub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoriesSub, Integer> {
    
}
