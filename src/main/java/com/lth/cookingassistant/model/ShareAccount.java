package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the share_account database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="share_account")
@NamedQuery(name="ShareAccount.findAll", query="SELECT s FROM ShareAccount s")
public class ShareAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ShareAccountPK id;

	//bi-directional many-to-one association to Account
	@MapsId("accountId")
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	//bi-directional many-to-one association to Share
	@MapsId("shareId")
	@ManyToOne
	@JoinColumn(name="share_id")
	private Share share;
}