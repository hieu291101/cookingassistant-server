package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.Storage;

import java.util.Collection;

public interface StorageService {
    Storage create(Storage Storage);
    Collection<Storage> list(int limit, Long id);
    Collection<Storage> list(int page, int size, Long id);
    Collection<Storage> filter(int page, int size, Long id, String ingredientName);
    Storage update(Storage storage);
    Boolean delete(Long id, String ingredientName);
    Boolean isStorageExist(Long id);
    Storage get(Long accountId, String ingredientName);
}
