package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


/**
 * The persistent class for the planner database table.
 * 
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="planner")
@NamedQuery(name="Planner.findAll", query="SELECT p FROM Planner p")
public class Planner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="planner_id")
	private long plannerId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate = new Date();

	@Enumerated(EnumType.STRING)
	@Column(name="day_of_week", length = 10)
	private EDayOfWeek dayOfWeek;

	@Column(name="is_delete")
	private byte isDelete;

	//bi-directional many-to-one association to Account
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	//bi-directional many-to-one association to PlannerMealofday
	@JsonIgnore
	@OneToMany( mappedBy="planner")
	@ToString.Exclude
	private List<PlannerMealofday> plannerMealofdays = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Planner planner = (Planner) o;
		return  Objects.equals(plannerId, planner.plannerId);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}