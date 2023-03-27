package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.ShoppingList;
import com.lth.cookingassistant.repo.ShoppingListRepo;
import com.lth.cookingassistant.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Collection;

@Service
public class ExcelService {
    @Autowired
    ShoppingListService shoppingListService;

    public ByteArrayInputStream load(Long id) {
        Collection<ShoppingList> shoppinglist = shoppingListService.list(1000, id);
        ByteArrayInputStream in = ExcelUtils.shoppinglistToExcel(shoppinglist);
        return in;
    }
}
