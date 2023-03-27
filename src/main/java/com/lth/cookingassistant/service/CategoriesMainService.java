package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.CategoriesMain;

import java.util.Collection;

public interface CategoriesMainService {
    Collection<CategoriesMain> list(int limit);
    Collection<CategoriesMain> searchCategories(String keyword);
}
