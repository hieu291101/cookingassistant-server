package com.lth.cookingassistant.request;

import lombok.Data;

import java.util.List;

@Data
public class RecipeInput {
    private List<String> ingredients;
    private int maxCookingTime;
}
