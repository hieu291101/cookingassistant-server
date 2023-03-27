package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the categories_sub database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories_sub")
@NamedQuery(name="CategoriesSub.findAll", query="SELECT c FROM CategoriesSub c")
public class CategoriesSub implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="category_sub_id")
	private int id;

	private String name;

	//bi-directional many-to-one association to CategoriesMain
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="category_man_id")
	private CategoriesMain categoriesMain;

	//bi-directional many-to-one association to Recipe
	@JsonIgnore
	@OneToMany(mappedBy="categoriesSub")
	private List<Recipe> recipes;
}