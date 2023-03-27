package com.lth.cookingassistant.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class StorageRequest extends ShoppingStorageRequest{
    private Date bestBefore;
}
