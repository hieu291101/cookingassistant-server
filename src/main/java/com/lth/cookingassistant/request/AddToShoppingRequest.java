package com.lth.cookingassistant.request;

import lombok.Data;

@Data
public class AddToShoppingRequest {
    private Long accountId;
    private Long recipeId;
}
