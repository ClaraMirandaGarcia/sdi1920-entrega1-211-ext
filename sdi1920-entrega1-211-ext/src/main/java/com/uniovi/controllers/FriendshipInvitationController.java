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

import com.uniovi.entities.FriendshipInvitation;
import com.uniovi.entities.IdFriendship;
import com.uniovi.entities.User;
import com.uniovi.services.FriendsService;
import com.uniovi.services.FriendshipInvitationService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipInvitationController {

	@Autowired
	private FriendshipInvitationService invitationsService;

	@Autowired
	private UsersService usersService;
	@Autowired
	private FriendsService fService;

	@RequestMapping("/invitation/list")
	public String getListado(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String emailFrom = auth.getName();
		Page<FriendshipInvitation> invitations = new PageImpl<FriendshipInvitation>(
				new LinkedList<FriendshipInvitation>());

		invitations = invitationsService.getInvitationsByEmailFrom(pageable, emailFrom);
		model.addAttribute("invitationsList", invitations.getContent());
		model.addAttribute("page", invitations);
		return "invitation/list";
	}

	@RequestMapping(value = "/invitation/add/{id}", method = RequestMethod.GET)
	public String setInvitation(Model model, @PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String emailFrom = auth.getName();
		User userTo = usersService.getUser(id);
		String emailTo = userTo.getEmail();

		if (!fService.areFriends(emailFrom, emailTo)) {
			FriendshipInvitation checkExistsInvitation = invitationsService.getInvitationEmails(emailFrom, emailTo);

			if (checkExistsInvitation == null) {
				FriendshipInvitation invitation = new FriendshipInvitation();
				IdFriendship idInvitation = new IdFriendship(emailFrom, userTo.getEmail());
				invitation.setId(idInvitation);
				model.addAttribute("invitation", invitation);
				invitationsService.addInvitation(invitation);
			}
		}

		return "redirect:/invitation/list";
	}

}
