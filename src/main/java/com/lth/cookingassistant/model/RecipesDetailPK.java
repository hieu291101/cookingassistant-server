package com.lth.cookingassistant.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the recipes_detail database table.
 * 
 */
@Embeddable
@Data
@NoArgsConstructor
public class RecipesDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="recipe_id", insertable=false, updatable=false)
	private long recipeId;

	@Column(name="ingredient_name")
	private String ingredientName;

}