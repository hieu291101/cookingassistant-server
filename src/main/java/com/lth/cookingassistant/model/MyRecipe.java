package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the my_recipe database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="my_recipe")
@NamedQuery(name="MyRecipe.findAll", query="SELECT m FROM MyRecipe m")
public class MyRecipe implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MyRecipePK id;

	private String status;

	//bi-directional many-to-one association to Account
	@MapsId("accountId")
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	//bi-directional many-to-one association to Recipe
	@MapsId("recipeId")
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;

}