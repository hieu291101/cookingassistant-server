package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.ShoppingList;

import java.util.Collection;
import java.util.List;

public interface ShoppingListService {
    ShoppingList create(ShoppingList shoppingList);
    Collection<ShoppingList> list(int limit, Long id);
    Collection<ShoppingList> list(int page, int size, Long id);
    Collection<ShoppingList> filter(int page, int size, Long id, String ingredientName);
    ShoppingList get(Long id, String ingredientName);
    ShoppingList update(ShoppingList storage);
    Boolean delete(Long id, String ingredientName);
    Boolean deleteAll(Long id);
    Boolean isShoppingListExist(Long id);
    List<ShoppingList> createAll(Collection<ShoppingList> ingredients, Long accountId);
}
