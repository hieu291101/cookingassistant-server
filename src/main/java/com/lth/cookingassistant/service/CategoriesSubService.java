package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.CategoriesSub;

import java.util.Collection;

public interface CategoriesSubService {
    Collection<CategoriesSub> searchCategoriesSub( int mainId);
}
