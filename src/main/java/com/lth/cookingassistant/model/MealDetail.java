package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the meal_detail database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="meal_detail")
@NamedQuery(name="MealDetail.findAll", query="SELECT m FROM MealDetail m")
public class MealDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MealDetailPK id = new MealDetailPK();

	@Column(name="is_delete")
	private byte isDelete;

	//bi-directional many-to-one association to Recipe
	@MapsId("recipeId")
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;

	//bi-directional many-to-one association to PlannerMealofday
	@JsonBackReference
	@MapsId("mealofdayId")
	@ManyToOne
	@JoinColumn(name="mealofday_id")
	private PlannerMealofday plannerMealofday;
}