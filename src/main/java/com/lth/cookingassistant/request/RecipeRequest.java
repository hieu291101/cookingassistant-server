package com.lth.cookingassistant.request;

import lombok.Data;

@Data
public class RecipeRequest {
//    "recipeId": 1,
//            "accessModifier": 1,
//            "activeTime": 1,
//            "briefDescription": "Dinner for children and parents",
//            "createDate": "2022-10-17T00:22:12.9304155",
//            "cuisine": "VIETNAM",
//            "mainIngredient": "rice",
//            "photoPath": "",
//            "preparation": "Xao rang",
//            "title": "Com rang",
//            "totalTime": 1,
//            "yields": 2,
//            "accountId": 4,
//            "categoriesSubId": 2
    private long recipeId;
    private byte accessModifier;
    private int activeTime;
    private String briefDescription;
    private String cuisine;
    private String mainIngredient;
    private String photoPath;
    private String preparation;
    private String title;
    private int totalTime;
    private int yields; 
    private long accountId;
    private int categoriesSubId;
}
