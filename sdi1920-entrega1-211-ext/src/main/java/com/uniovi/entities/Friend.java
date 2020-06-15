package com.uniovi.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Friend {
	
	@EmbeddedId
	private IdFriend id;

	public Friend() {
	}

	public IdFriend getId() {
		return id;
	}
	public void setId(IdFriend idFriend) {
		this.id = idFriend;
	}

}
