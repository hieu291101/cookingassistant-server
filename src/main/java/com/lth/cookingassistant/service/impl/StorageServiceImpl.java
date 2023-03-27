package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.Recipe;
import com.lth.cookingassistant.model.Storage;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.repo.StorageRepo;
import com.lth.cookingassistant.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class StorageServiceImpl implements StorageService {
    private final StorageRepo storageRepo;
    private final AccountRepo accountRepo;

    @Override
    public Storage create(Storage storage) {
        log.info("Saving a new storage: {}", storage.getId().getIngredientName());
        return storageRepo.save(storage);
    }

    @Override
    public Collection<Storage> list(int limit, Long id) {
        log.info("Fetching all storages");
        Account account = accountRepo.findById(id).get();
        return storageRepo.findAllByAccountEquals(account, PageRequest.of(0, limit));
    }

    @Override
    public Collection<Storage> list(int page, int size, Long id) {
        log.info(String.format("Fetching storages from %d to %d", page, page * size));
        Account account = accountRepo.findById(id).get();
        return storageRepo.findAllByAccountEquals(account, PageRequest.of(page, size));
    }

    @Override
    public Collection<Storage> filter(int page, int size, Long id, String ingredientName) {
        log.info(String.format("Fetching recipes from %d to %d", page, page * size));

        Storage storage = new Storage();
        storage.getId().setIngredientName(ingredientName);
        storage.getId().setAccountId(id);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("accountId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("ingredientName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Storage> recipeExample = Example.of(storage, exampleMatcher);
        return storageRepo.findAll(recipeExample, PageRequest.of(page - 1, size)).toList();
    }

    @Override
    public Storage get(Long accountId, String ingredientName) {
        log.info("Fetching storage by id: {}", accountId);
        return storageRepo.findById_AccountIdAndId_IngredientName(accountId, ingredientName).get();
    }

    @Override
    public Storage update(Storage storage) {
        String ingredientName = storage.getId().getIngredientName();
        log.info("Updating storage: {}", ingredientName);
        long id = storage.getId().getAccountId();

        if(!storageRepo.existsById_AccountIdAndId_IngredientName(id, ingredientName)) {
            throw new CANotFoundException(
                    new StringBuffer()
                            .append("Storage '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        return storageRepo.save(storage);
    }

    @Override
    public Boolean delete(Long id, String IngredientName) {
        log.info("Deleting storage by id: {}", id);
        storageRepo.deleteById_AccountIdAndId_IngredientName(id, IngredientName);
        return Boolean.TRUE;
    }

    @Override
    public Boolean isStorageExist(Long id) {
        return storageRepo.existsById(id);
    }

}
