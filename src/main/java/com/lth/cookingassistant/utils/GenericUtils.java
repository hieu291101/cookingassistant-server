package com.lth.cookingassistant.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class GenericUtils<T> {
    //Set value depend on field of Object
    public void copyNonNullValues(T req, T db) throws IllegalAccessException {
        Field[] allFields = req.getClass().getDeclaredFields();
        for(Field f : allFields){
            f.setAccessible(true);
            Object value = f.get(req);
            System.out.println(value);
            if(value != null)
                f.set(db, value);
        }
    }
}
