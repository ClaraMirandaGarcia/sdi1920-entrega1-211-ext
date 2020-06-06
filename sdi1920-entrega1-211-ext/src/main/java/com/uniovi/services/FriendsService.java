package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friend;
import com.uniovi.entities.IdFriend;
import com.uniovi.repositories.FriendsRepository;

@Service
public class FriendsService {

	@Autowired
	private FriendsRepository fiRepository;

	public List<Friend> getFriends() {
		List<Friend> invitations = new ArrayList<Friend>();
		fiRepository.findAll().forEach(invitations::add);
		return invitations;
	}

	public Friend getFriend(IdFriend id) {
		return fiRepository.findById(id).get();
	}

	public void addFriend(Friend invitation) {
		fiRepository.save(invitation);
	}

	public void deleteFriend(IdFriend id) {
		fiRepository.deleteById(id);
	}

}