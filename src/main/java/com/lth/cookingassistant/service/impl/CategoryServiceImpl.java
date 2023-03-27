package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.model.CategoriesSub;
import com.lth.cookingassistant.repo.CategoryRepo;
import com.lth.cookingassistant.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public CategoriesSub get(Integer id) {
        log.info("Fetching category by id: {}", id);
        return categoryRepo.findById(id).get();
    }

    @Override
    public Boolean isCategoryExist(Integer id) {
        return categoryRepo.existsById(id);
    }
}
