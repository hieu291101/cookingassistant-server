package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.model.EatingStyle;
import com.lth.cookingassistant.repo.EatingStyleRepo;
import com.lth.cookingassistant.service.EatingStyleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class EatingStyleServiceImpl implements EatingStyleService {
    public final EatingStyleRepo eatingStyleRepo;

    @Override
    public Collection<EatingStyle> searchEatingStyle(String keyword) {
        log.info("Fetching all eating style");
        return eatingStyleRepo.findEatingStylesByEatingStyleLike("%" + keyword + "%");
    }
}
