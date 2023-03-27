package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.Cuisine;

import java.util.Collection;

public interface CuisineService {
    Collection<Cuisine> searchCuisine(String keyword);
}
