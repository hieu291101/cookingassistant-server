package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="recipes_rating")
@NamedQuery(name="RecipeRating.findAll", query="SELECT r FROM RecipeRating r")
public class RecipeRating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="recipe_rating_id")
	private long recipeRatingId;

	@Column(name="rating_star")
	private int ratingStar;

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
