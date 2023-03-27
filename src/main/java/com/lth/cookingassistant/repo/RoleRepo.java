package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.ERole;
import com.lth.cookingassistant.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole role);
}
