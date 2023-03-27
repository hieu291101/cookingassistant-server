package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.model.CategoriesMain;
import com.lth.cookingassistant.model.Measure;
import com.lth.cookingassistant.repo.CategoriesMainRepo;
import com.lth.cookingassistant.service.CategoriesMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CategoriesMainServiceImpl implements CategoriesMainService {
    private final CategoriesMainRepo categoriesMainRepo;

    @Override
    public Collection<CategoriesMain> list(int limit) {
        log.info("Fetching all categories main");
        return categoriesMainRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Collection<CategoriesMain> searchCategories(String keyword) {
        log.info("Fetching all categories main");
        return categoriesMainRepo.findCategoriesMainsByNameLike("%" + keyword + "%");
    }
}
