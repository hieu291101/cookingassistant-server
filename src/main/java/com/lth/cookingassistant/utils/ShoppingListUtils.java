package com.lth.cookingassistant.utils;

import com.lth.cookingassistant.model.ShoppingList;
import com.lth.cookingassistant.model.ShoppingListPK;
import com.lth.cookingassistant.request.ShoppingListRequest;
import com.lth.cookingassistant.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListUtils {
    @Autowired
    AccountService accountService;

    public void copyValuesFromRequest(ShoppingListRequest req, ShoppingList db) {
        if(!req.getIngredientName().isEmpty() && req.getAccountId() != null){
            ShoppingListPK shoppingListPK = new ShoppingListPK( req.getAccountId(), req.getIngredientName());
            db.setId(shoppingListPK);
        }

        if(!req.getNote().isEmpty())
            db.setNote(req.getNote());

        if(!req.getMeasure().isEmpty())
            db.setMeasure(req.getMeasure());

        if(req.getQuantity() > 0)
            db.setQuantity(req.getQuantity());

        if(accountService.isAccountExist(req.getAccountId()))
            db.setAccount(
                    accountService.get(req.getAccountId())
            );
    }
}
