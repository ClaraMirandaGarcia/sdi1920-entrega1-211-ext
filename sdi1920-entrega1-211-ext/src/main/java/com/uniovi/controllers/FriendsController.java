package com.uniovi.controllers;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Friend;
import com.uniovi.entities.FriendshipInvitation;
import com.uniovi.entities.IdFriend;
import com.uniovi.entities.User;
import com.uniovi.services.FriendsService;
import com.uniovi.services.FriendshipInvitationService;
import com.uniovi.services.UsersService;

@Controller
public class FriendsController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private FriendshipInvitationService friendshipService;

	
	@RequestMapping("/friend/list")
	public String getListado(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Page<User> friends = new PageImpl<User>(new LinkedList<User>());

		friends = friendsService.getFriendsFor(pageable, email);

		model.addAttribute("friendList", friends.getContent());
		model.addAttribute("page", friends);
		return "friend/list";
	}
	
	@RequestMapping(value = "/friend/add/{email}", method = RequestMethod.GET)
	public String setFriend(Model model, @PathVariable String email) {
		Friend friend = new Friend();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String emailFrom = auth.getName();
		User userTo = usersService.getUserByEmail(email);

		IdFriend idInvitation = new IdFriend(emailFrom, userTo.getEmail());
		friend.setId(idInvitation);

		model.addAttribute("friend", friend);
		friendsService.addFriend(friend);
		
		//Check if an invitation exists
		//Check if they are already friends

		//Realmente aquí podría haber más de una invitación? -> CHANGE
		FriendshipInvitation toDelete = friendshipService.getInvitationEmails(userTo.getEmail(), emailFrom);
		friendshipService.deleteInvitation(toDelete.getId());
		return "redirect:/invitation/list";
	}

}