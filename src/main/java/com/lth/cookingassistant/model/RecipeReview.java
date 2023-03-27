package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the recipes database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="recipes_review")
@NamedQuery(name="RecipeReview.findAll", query="SELECT r FROM RecipeReview r")
public class RecipeReview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="recipe_review_id")
	private Long recipeReviewId;

	@Column(name="review_text")
	private String reviewText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate = new Date();

	//bi-directional many-to-one association to Account
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	//bi-directional many-to-one association to CategoriesMain
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;
}