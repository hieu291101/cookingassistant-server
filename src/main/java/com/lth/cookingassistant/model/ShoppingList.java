package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the shopping_list database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shopping_list")
@NamedQuery(name="ShoppingList.findAll", query="SELECT s FROM ShoppingList s")
public class ShoppingList implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ShoppingListPK id;

	private String note;

	private String measure;

	private int quantity;

	//bi-directional many-to-one association to Account
	@MapsId("accountId")
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	//bi-directional many-to-one association to Share
	@ManyToOne
	@JoinColumn(name="share_id")
	private Share share;
}