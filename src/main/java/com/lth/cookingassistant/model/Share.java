package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the share database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Share.findAll", query="SELECT s FROM Share s")
public class Share implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="share_id")
	private long shareId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	//bi-directional many-to-one association to ShareAccount
	@OneToMany(mappedBy="share")
	private List<ShareAccount> shareAccounts;

	//bi-directional many-to-one association to ShoppingList
	@OneToMany(mappedBy="share")
	private List<ShoppingList> shoppingLists;

}