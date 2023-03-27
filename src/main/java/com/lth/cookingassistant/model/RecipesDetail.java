package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the recipes_detail database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="recipes_detail")
@NamedQuery(name="RecipesDetail.findAll", query="SELECT r FROM RecipesDetail r")
public class RecipesDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RecipesDetailPK id;

	private byte heading;

	private String measure;
	
	private int quantity;

	//bi-directional many-to-one association to Recipe
	@JsonIgnore
	@MapsId("recipeId")
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;
}