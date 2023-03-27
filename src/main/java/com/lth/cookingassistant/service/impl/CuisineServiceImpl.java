package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.model.Cuisine;
import com.lth.cookingassistant.repo.CuisineRepo;
import com.lth.cookingassistant.service.CuisineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CuisineServiceImpl implements CuisineService {
    private final CuisineRepo cuisineRepo;

    @Override
    public Collection<Cuisine> searchCuisine(String keyword) {
        log.info("Fetching all cuisine");
        return cuisineRepo.findCuisinesByNameLike("%" + keyword + "%");
    }
}
