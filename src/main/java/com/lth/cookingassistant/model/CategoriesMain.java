package com.lth.cookingassistant.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categories_main database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories_main")
@NamedQuery(name="CategoriesMain.findAll", query="SELECT c FROM CategoriesMain c")
public class CategoriesMain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="category_main_id")
	private int categoryMainId;

	private String name;

	//bi-directional many-to-one association to CategoriesSub
	@OneToMany(mappedBy="categoriesMain")
	private List<CategoriesSub> categoriesSubs;
}