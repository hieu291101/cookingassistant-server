package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.Storage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepo extends JpaRepository<Storage, Long> {
    List<Storage> findByAccountEquals(Account account);
    List<Storage> findAllByAccountEquals(Account account, Pageable pageable);
    void deleteById_AccountIdAndId_IngredientName(Long id, String ingredientName);
    Optional<Storage> findById_AccountIdAndId_IngredientName(Long id, String ingredientName);
    boolean existsById_AccountIdAndId_IngredientName(Long id, String ingredientName);
}
