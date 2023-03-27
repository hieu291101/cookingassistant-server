package com.lth.cookingassistant.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ShoppingStorageResponse {
    protected String ingredientName;
    protected String measure;
    protected int quantity;
}
