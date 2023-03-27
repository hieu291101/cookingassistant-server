package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the accounts database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="account_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long accountId;

	private byte activated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate = new Date();

	private String password;

	private String username;

	private String email;

	private EProvider provider;

	private String providerId;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="accounts_roles"
		, joinColumns={
			@JoinColumn(name="account_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id")
			}
		)
	private Set<Role> roles;

	//bi-directional many-to-one association to MyRecipe
	@OneToMany(mappedBy="account")
	private List<MyRecipe> myRecipes;

	//bi-directional many-to-one association to Planner
	@JsonManagedReference
	@OneToMany(mappedBy="account")
	private List<Planner> planners;

	//bi-directional many-to-one association to Profile
	@OneToMany(mappedBy="account")
	private List<Profile> profiles;

	//bi-directional many-to-one association to Recipe
	@OneToMany(mappedBy="account")
	private List<Recipe> recipes;

	//bi-directional many-to-one association to ShareAccount
	@OneToMany(mappedBy="account")
	private List<ShareAccount> shareAccounts;

	//bi-directional many-to-one association to ShoppingList
	@OneToMany(mappedBy="account")
	private List<ShoppingList> shoppingLists;

	//bi-directional many-to-one association to Storage
	@OneToMany(mappedBy="account")
	private List<Storage> storages;

	@OneToOne(mappedBy = "account")
	private PasswordRefreshToken passwordResetToken;

	@JsonIgnore
	@OneToMany(mappedBy="account")
	private List<RecipeReview> recipeReviews;

	@JsonIgnore
	@OneToMany(mappedBy="account")
	private List<RecipeRating> recipeRatings;
}