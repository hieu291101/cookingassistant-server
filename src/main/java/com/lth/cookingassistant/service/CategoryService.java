package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.CategoriesSub;

public interface CategoryService {
    CategoriesSub get(Integer id);
    Boolean isCategoryExist(Integer id);
}
