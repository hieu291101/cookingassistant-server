package com.lth.cookingassistant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ShoppingListResponse extends ShoppingStorageResponse{
    private String note;
}
