package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the recipes database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="recipes")
@NamedQuery(name="Recipe.findAll", query="SELECT r FROM Recipe r")
public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="recipe_id")
	private long recipeId;

	@Column(name="access_modifier")
	private byte accessModifier;

	@Column(name="active_time")
	private int activeTime;

	@Column(name="brief_description")
	private String briefDescription;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate = new Date();

	private String cuisine;

	@Column(name="main_ingredient")
	private String mainIngredient;

	@Column(name="photo_path")
	private String photoPath;

	private String preparation;

	private String title;

	@Column(name="total_time")
	private int totalTime;

	private int yields;

	//bi-directional many-to-one association to MyRecipe
	@JsonIgnore
	@OneToMany(mappedBy="recipe")
	private List<MyRecipe> myRecipes;

	//bi-directional many-to-one association to Account
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	//bi-directional many-to-one association to CategoriesMain
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="category_id")
	private CategoriesSub categoriesSub;

	//bi-directional many-to-one association to RecipesDetail
	@JsonIgnore
	@OneToMany(mappedBy="recipe")
	private List<RecipesDetail> recipesDetails;


	@JsonIgnore
	@OneToMany(mappedBy="recipe")
	private List<RecipeReview> recipeReviews;

	@JsonIgnore
	@OneToMany(mappedBy="recipe")
	private List<RecipeRating> recipeRatings;
}