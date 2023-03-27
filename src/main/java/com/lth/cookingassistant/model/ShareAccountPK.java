package com.lth.cookingassistant.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the share_account database table.
 * 
 */
@Embeddable
@Data
@NoArgsConstructor
public class ShareAccountPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="account_id", insertable=false, updatable=false)
	private long accountId;

	@Column(name="share_id", insertable=false, updatable=false)
	private long shareId;

	@Column(name="share_account_id")
	private long shareAccountId;
}