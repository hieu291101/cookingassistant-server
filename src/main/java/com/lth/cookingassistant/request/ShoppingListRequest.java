package com.lth.cookingassistant.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShoppingListRequest extends ShoppingStorageRequest{
    private String note;
}
