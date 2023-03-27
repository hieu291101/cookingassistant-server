package com.lth.cookingassistant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class StorageResponse extends ShoppingStorageResponse{
    private Date bestBefore;
}
