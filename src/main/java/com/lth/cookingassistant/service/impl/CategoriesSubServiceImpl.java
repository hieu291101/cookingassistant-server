package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.model.CategoriesSub;
import com.lth.cookingassistant.repo.CategoriesSubRepo;
import com.lth.cookingassistant.service.CategoriesSubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CategoriesSubServiceImpl implements CategoriesSubService {
    private final CategoriesSubRepo categoriesSubRepo;

    @Override
    public Collection<CategoriesSub> searchCategoriesSub( int mainId) {
        log.info("Fetching all categories sub");
        return categoriesSubRepo.findCategoriesSubsByCategoriesMain_CategoryMainId(mainId);
    }
}
