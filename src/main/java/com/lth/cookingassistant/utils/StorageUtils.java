package com.lth.cookingassistant.utils;

import com.lth.cookingassistant.model.Storage;
import com.lth.cookingassistant.model.StoragePK;
import com.lth.cookingassistant.request.StorageRequest;
import com.lth.cookingassistant.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StorageUtils extends GenericUtils<Storage> {
    @Autowired
    AccountService accountService;

    public void copyValuesFromRequest(StorageRequest req, Storage db) {
        if(!req.getIngredientName().isEmpty() && req.getAccountId() != null){
            StoragePK storagePK = new StoragePK(req.getIngredientName(), req.getAccountId());
            db.setId(storagePK);
        }

        if(req.getBestBefore().after(new Date()) )
            db.setBestBefore(req.getBestBefore());

        db.setIsWarning((byte)0);

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
