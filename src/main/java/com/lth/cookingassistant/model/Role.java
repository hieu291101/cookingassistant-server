package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="roles")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	private int roleId;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	//bi-directional many-to-many association to Account
	@ManyToMany(mappedBy="roles")
	private List<Account> accounts;

	//bi-directional many-to-one association to ShareAccount
	@OneToMany(mappedBy="role")
	private List<ShareAccount> shareAccounts;
}