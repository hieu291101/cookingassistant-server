package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * The persistent class for the storage database table.
 * 
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Storage.findAll", query="SELECT s FROM Storage s")
public class Storage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StoragePK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="best_before")
	private Date bestBefore;

	@Column(name="is_warning")
	private byte isWarning;

	private String measure;

	private int quantity;

	//bi-directional many-to-one association to Account
	@JsonIgnore
	@MapsId("accountId")
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Storage storage = (Storage) o;
		return id != null && Objects.equals(id, storage.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}