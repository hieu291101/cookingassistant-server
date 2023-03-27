package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vitamin database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Vitamin.findAll", query="SELECT v FROM Vitamin v")
public class Vitamin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ingredient_id")
	private long ingredientId;

	private String name;
}