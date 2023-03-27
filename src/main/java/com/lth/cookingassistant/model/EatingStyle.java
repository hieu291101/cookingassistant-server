package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eating_styles database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="eating_styles")
@NamedQuery(name="EatingStyle.findAll", query="SELECT e FROM EatingStyle e")
public class EatingStyle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="eating_style_id")
	private int eatingStyleId;

	@Column(name="eating_style")
	private String eatingStyle;

	//bi-directional many-to-one association to Profile
	@OneToMany(mappedBy="eatingStyle")
	private List<Profile> profiles;

}