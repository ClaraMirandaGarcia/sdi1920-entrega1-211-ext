package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendshipInvitation;
import com.uniovi.entities.IdFriendship;
import com.uniovi.repositories.FriendshipInvitationsRepository;

@Service
public class FriendshipInvitationService {

	@Autowired
	private FriendshipInvitationsRepository fiRepository;

	public List<FriendshipInvitation> getInvitations() {
		List<FriendshipInvitation> invitations = new ArrayList<FriendshipInvitation>();
		fiRepository.findAll().forEach(invitations::add);
		return invitations;
	}

	public FriendshipInvitation getInvitation(IdFriendship id) {
		return fiRepository.findById(id).get();
	}

	public void addInvitation(FriendshipInvitation invitation) {
		fiRepository.save(invitation);
	}

	public void deleteInvitation(IdFriendship id) {
		fiRepository.deleteById(id);
	}

	public FriendshipInvitation getInvitationEmails(String emailFrom, String emailTo) {
		return fiRepository.getInvitationEmails(emailFrom, emailTo);
	}

	public Page<FriendshipInvitation> getInvitationsByEmailFrom(Pageable pageable, String email) {
		Page<FriendshipInvitation> users = new PageImpl<FriendshipInvitation>(new LinkedList<FriendshipInvitation>());
		users = fiRepository.getInvitationsFor(pageable, email);
		return users;
	}

}