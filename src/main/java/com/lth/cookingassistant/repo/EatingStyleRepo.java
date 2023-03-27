package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.EatingStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface EatingStyleRepo extends JpaRepository<EatingStyle, Integer> {
    Collection<EatingStyle> findEatingStylesByEatingStyleLike (String keyword);
}
