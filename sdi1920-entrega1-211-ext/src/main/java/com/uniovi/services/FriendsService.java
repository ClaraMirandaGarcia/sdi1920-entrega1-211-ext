package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friend;
import com.uniovi.entities.IdFriend;
import com.uniovi.entities.User;
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
	
	public Page<User> getFriendsFor(Pageable pageable, String email) {
        Page<User> friends = new PageImpl<User>(new LinkedList<User>());
        friends = fiRepository.getFriendsFor(pageable, email);
        return friends;
    }

}