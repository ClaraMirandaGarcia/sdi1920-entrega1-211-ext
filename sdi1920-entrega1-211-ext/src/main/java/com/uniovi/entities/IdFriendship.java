package com.uniovi.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class IdFriendship implements Serializable {
	private String userEmailFor;
	private String userEmailTo;

	public IdFriendship() {
	}

	public IdFriendship(String userEmailFor, String userEmailTo) {
		super();
		this.userEmailFor = userEmailFor;
		this.userEmailTo = userEmailTo;
	}

	public String getUserEmailFor() {
		return userEmailFor;
	}

	public String getUserEmailTo() {
		return userEmailTo;
	}

}