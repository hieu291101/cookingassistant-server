package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.ShoppingList;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.repo.ShoppingListRepo;
import com.lth.cookingassistant.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ShoppingListServiceImpl implements ShoppingListService {
    private final ShoppingListRepo shoppingListRepo;
    private final AccountRepo accountRepo;

    @Override
    public ShoppingList create(ShoppingList shoppinglist) {
        log.info("Saving a new shoppinglist: {}", shoppinglist.getId().getIngredientName());
        return shoppingListRepo.save(shoppinglist);
    }

    @Override
    public Collection<ShoppingList> list(int limit, Long id) {
        log.info("Fetching all shoppinglists");
        Account account = accountRepo.findById(id).get();
        return shoppingListRepo.findAllByAccountEquals(account, PageRequest.of(0, limit));
    }

    @Override
    public Collection<ShoppingList> list(int page, int size, Long id) {
        log.info(String.format("Fetching shoppinglists from %d to %d", page, page * size));
        Account account = accountRepo.findById(id).get();
        return shoppingListRepo.findAllByAccountEquals(account, PageRequest.of(page, size));
    }

    @Override
    public Collection<ShoppingList> filter(int page, int size, Long id, String ingredientName) {
        log.info(String.format("Fetching recipes from %d to %d", page, page * size));

        ShoppingList shoppinglist = new ShoppingList();
        shoppinglist.getId().setIngredientName(ingredientName);
        shoppinglist.getId().setAccountId(id);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("accountId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("ingredientName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<ShoppingList> recipeExample = Example.of(shoppinglist, exampleMatcher);
        return shoppingListRepo.findAll(recipeExample, PageRequest.of(page - 1, size)).toList();
    }

    @Override
    public ShoppingList get(Long accountId, String ingredientName) {
        log.info("Fetching shoppinglist by ingredient name: {}", ingredientName);
        return shoppingListRepo.findById_AccountIdAndId_IngredientName(accountId, ingredientName).get();
    }

    @Override
    public ShoppingList update(ShoppingList shoppinglist) {
        String ingredientName = shoppinglist.getId().getIngredientName();
        log.info("Updating shoppinglist: {}", ingredientName);
        long id = shoppinglist.getId().getAccountId();

        if(!shoppingListRepo.existsById_AccountIdAndId_IngredientName(id, ingredientName)) {
            throw new CANotFoundException(
                    new StringBuffer()
                            .append("ShoppingList '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        return shoppingListRepo.save(shoppinglist);
    }

    @Override
    public Boolean delete(Long id, String IngredientName) {
        log.info("Deleting shoppinglist by id: {}", id);
        shoppingListRepo.deleteById_AccountIdAndId_IngredientName(id, IngredientName);
        return Boolean.TRUE;
    }

    @Override
    public  Boolean deleteAll(Long id){
        log.info("Deleting all shoppinglist by id: {}", id);
        shoppingListRepo.deleteById_AccountId(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean isShoppingListExist(Long id) {
        return shoppingListRepo.existsById(id);
    }

    @Override
    public List<ShoppingList> createAll(Collection<ShoppingList> ingredients, Long accountId) {
        log.info("Saving a new shopping list: {}");
        List<ShoppingList> result = ingredients
                                    .stream()
                                    .filter(
                                        ingre -> !shoppingListRepo.existsById_AccountIdAndId_IngredientName(accountId, ingre.getId().getIngredientName())
                                    )
                                    .toList(); 

        return shoppingListRepo.saveAll(result);
    }
}
