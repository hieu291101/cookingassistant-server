package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<Profile, Long> {
}
