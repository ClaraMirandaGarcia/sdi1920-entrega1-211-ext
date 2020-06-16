package com.uniovi.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class IdFriend implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String userEmailFrom;
	private String userEmailTo;

	public IdFriend() {
	}

	public IdFriend(String userEmailFrom, String userEmailTo) {
		super();
		this.userEmailFrom = userEmailFrom;
		this.userEmailTo = userEmailTo;
	}

	public String getUserEmailFrom() {
		return userEmailFrom;
	}

	public String getUserEmailTo() {
		return userEmailTo;
	}

}