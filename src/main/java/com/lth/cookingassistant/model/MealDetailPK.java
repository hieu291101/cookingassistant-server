package com.lth.cookingassistant.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * The primary key class for the meal_detail database table.
 * 
 */
@Embeddable
@Data
@NoArgsConstructor
public class MealDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private java.util.Date createdDate = new Date();

	@Column(name="recipe_id", insertable=false, updatable=false)
	private long recipeId;

	@Column(name="mealofday_id", insertable=false, updatable=false)
	private long mealofdayId;

}