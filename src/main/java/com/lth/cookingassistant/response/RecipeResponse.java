package com.lth.cookingassistant.response;

import java.util.Date;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class RecipeResponse {
	private long recipeId;
	private byte accessModifier;
	private int activeTime;
	private String briefDescription;
	private Date createdDate;
	private String cuisine;
	private String mainIngredient;
	private String photoPath;
	private String preparation;
	private String title;
	private int totalTime;
	private int yields;
    private String username;
    private String category;
}
