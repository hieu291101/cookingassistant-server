package com.lth.cookingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the planner_mealofday database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="planner_mealofday")
@NamedQuery(name="PlannerMealofday.findAll", query="SELECT p FROM PlannerMealofday p")
public class PlannerMealofday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="mealofday_id")
	private long mealofdayId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate = new Date();

	@Column(name="is_delete")
	private byte isDelete;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private EMeal meal;

	@Column(name="meal_desciption")
	private String mealDesciption;

	//bi-directional many-to-one association to MealDetail
	@JsonManagedReference
	@OneToMany(mappedBy="plannerMealofday")
	private List<MealDetail> mealDetails = new ArrayList<>();

	//bi-directional many-to-one association to Planner
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="planner_id")
	private Planner planner;

}