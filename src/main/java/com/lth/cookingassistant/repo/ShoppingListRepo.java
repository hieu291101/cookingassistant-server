package com.lth.cookingassistant.repo;

import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.ShoppingList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepo extends JpaRepository<ShoppingList, Long> {
    List<ShoppingList> findAllByAccountEquals(Account account, Pageable pageable);
    void deleteById_AccountIdAndId_IngredientName(Long id, String ingredientName);
    void deleteById_AccountId(Long id);
    Optional<ShoppingList> findById_AccountIdAndId_IngredientName(Long id, String ingredientName);
    boolean existsById_AccountIdAndId_IngredientName(Long id, String ingredientName);
}
