package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ingredients database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ingredients")
@NamedQuery(name="Ingredient.findAll", query="SELECT i FROM Ingredient i")
public class Ingredient implements Serializable {
	// private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ingredient_id")
	private long ingredientId;

	private int calo;

	private int carb;

	private int fiber;

	private String name;

	private int protein;
}