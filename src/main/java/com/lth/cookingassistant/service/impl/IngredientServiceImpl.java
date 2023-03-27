package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Ingredient;
import com.lth.cookingassistant.repo.IngredientRepo;
import com.lth.cookingassistant.repo.ShoppingListRepo;
import com.lth.cookingassistant.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepo ingredientRepo;
    private final ShoppingListRepo shoppingListRepo;

    @Override
    public Collection<Ingredient> searchIngredients(String keyword) {
        log.info("Fetching ingredient");
        return ingredientRepo.findIngredientsByNameLike("%" + keyword + "%");
    }

    @Override
	public Collection<Ingredient> list(int page, int size) {
		log.info(String.format("Fetching accounts from %d to %d", page, page * size));
		return ingredientRepo.findAll(PageRequest.of(page - 1, size)).toList();
	}

    @Override
    public Collection<Ingredient> getAllIngredient() {
        log.info("Fetching all ingredient");
        return ingredientRepo.findAll();
    }

    @Override
    public long countIngredient() {
        return ingredientRepo.count();
    }

    @Override
    public Ingredient get(Long id) {
        log.info("Fetching ingredient by id: {}", id);
        return ingredientRepo.findById(id).get();
    }

    @Override
    public Ingredient update(Ingredient ingredient) {
        log.info("Updating ingredient: {}", ingredient.getName());
        long id = ingredient.getIngredientId();

        if(!ingredientRepo.existsById(id)) {
            throw new CANotFoundException(
                    new StringBuffer()
                            .append("Ingredient '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        return ingredientRepo.save(ingredient);
    }

    @Override
    public Ingredient create(Ingredient ingredient) {
        log.info("Saving a new measure: {}", ingredient.getName());
        if(ingredientRepo.existsByName(ingredient.getName()))
            return null;

        return ingredientRepo.save(ingredient);
    }


    @Override
    public Boolean delete(long id) {
        log.info("Deleting measure by id: {}", id);
        ingredientRepo.deleteById(id);
        return Boolean.TRUE;
    }
}
