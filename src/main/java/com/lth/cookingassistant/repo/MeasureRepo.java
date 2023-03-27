package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MeasureRepo extends JpaRepository<Measure, Integer> {
    Collection<Measure> findMeasuresByNameLike (String keyword);
    Boolean existsByName(String name);
}
