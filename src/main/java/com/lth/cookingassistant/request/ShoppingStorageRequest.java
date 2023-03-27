package com.lth.cookingassistant.request;

import lombok.Data;

@Data
public class ShoppingStorageRequest {
    protected Long accountId;
    protected String ingredientName;
    protected String measure;
    protected int quantity;
}
