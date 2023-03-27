package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.EatingStyle;

import java.util.Collection;

public interface EatingStyleService {
    Collection<EatingStyle> searchEatingStyle(String keyword);
}
