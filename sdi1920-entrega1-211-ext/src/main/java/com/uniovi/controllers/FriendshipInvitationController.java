package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.uniovi.services.FriendshipInvitationService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipInvitationController {

	@Autowired
	private FriendshipInvitationService invitationsService;

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/invitation/add/{id}", method = RequestMethod.GET)
	public String setInvitation(Model model, @PathVariable Long id) {
		FriendshipInvitation invitation = new FriendshipInvitation();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String emailFrom = auth.getName();
		User userTo = usersService.getUser(id);
		String emailTo = userTo.getEmail();

		// check if userFrom && userTo -> friends
		
		// check if the invitation userFrom && userTo or userTo && userFrom exists
		FriendshipInvitation checkExists = invitationsService.getInvitationEmails(emailFrom, emailTo);

		if (checkExists != null) {
			IdFriendship idInvitation = new IdFriendship(emailFrom, userTo.getEmail());
			invitation.setId(idInvitation);
			model.addAttribute("invitation", invitation);
			invitationsService.addInvitation(invitation);
		}

		return "redirect:/home";
	}

}
