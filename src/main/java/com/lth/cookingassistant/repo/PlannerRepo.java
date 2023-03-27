package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface PlannerRepo extends JpaRepository<Planner, Long> {
}
