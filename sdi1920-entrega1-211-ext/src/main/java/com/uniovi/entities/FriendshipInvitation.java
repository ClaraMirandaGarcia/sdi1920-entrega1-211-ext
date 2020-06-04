package com.uniovi.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class FriendshipInvitation {

	@ManyToOne
	private User userFrom;
	@ManyToOne
	private User userTo;

	@EmbeddedId
	private IdFriendship id;

	public FriendshipInvitation(boolean accepted) {
		super();
		
	}

	public FriendshipInvitation() {
	}

	public User getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(User userFrom) {
		this.userFrom = userFrom;
	}

	public User getUserTo() {
		return userTo;
	}

	public void setUserTo(User userTo) {
		this.userTo = userTo;
	}

	public IdFriendship getId() {
		return id;
	}

	public void setId(IdFriendship id) {
		this.id = id;
	}

}