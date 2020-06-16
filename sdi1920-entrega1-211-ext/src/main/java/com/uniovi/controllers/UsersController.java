package com.uniovi.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniovi.entities.User;
import com.uniovi.services.FriendsService;
import com.uniovi.services.FriendshipInvitationService;
import com.uniovi.services.PostService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private SecurityService securityService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private FriendsService fService;
	@Autowired
	private FriendshipInvitationService fiService;
	@Autowired
	private PostService postsService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public String delete(RedirectAttributes rattrs,
			@RequestParam(value = "userDelete", required = false) List<String> emails) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getName();

		if (!emails.contains(userEmail)) {
			for (String email : emails) {
				User userToDelete = usersService.getUserByEmail(email);

				// deleting posts
				postsService.getPostsListForUser(userToDelete).forEach((post) -> {
					postsService.deletePost(post.getId());
				});
				// deleting friends
				fService.getFriendsListForUser(userToDelete.getEmail()).forEach((friend) -> {
					fService.deleteFriend(friend.getId());
				});
				// deleting invitations
				fiService.getInvitationsListForUser(userToDelete.getEmail()).forEach((invitation) -> {
					fiService.deleteInvitation(invitation.getId());
				});

				// deleting user
				usersService.deleteUser(userToDelete.getId());
			}
		} else {
			rattrs.addFlashAttribute("error", "You can not delete your own account");
		}

		return "redirect:list";
	}

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText, @ModelAttribute("error") String error) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);

		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersService.getUsersFor(pageable, email);

		if (user.getRole().equals(rolesService.getRoles()[1])) {
			// ROLE_USER
			if (searchText != null && !searchText.isEmpty()) {
				users = usersService.searchByNameSurnameOrEmail(pageable, searchText, email);
			} else {
				users = usersService.getUsersFor(pageable, email);
			}
		} else if (user.getRole().equals(rolesService.getRoles()[0])) {
			// ROLE_ADMIN
			if (searchText != null && !searchText.isEmpty()) {
				users = usersService.searchByNameSurnameOrEmailAdmin(pageable, searchText, email);
			} else {
				users = new PageImpl<User>(usersService.getUsers());
			}
		}

		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		
		List<User> aux = fiService.getInvitationsUsersListForUser(email);		
		model.addAttribute("invitationsList", fiService.getInvitationsUsersListForUser(email));

		// Check if already a friend -> add the list of friends
		Page<User> friends = new PageImpl<User>(new LinkedList<User>());
		friends = fService.getFriendsFor(pageable, email);
		model.addAttribute("friendsList", friends.getContent());

		if (error != null && !error.isEmpty()) {
			model.addAttribute("error", error);
		}

		return "user/list";
	}

	// Basic methods of the application
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[1]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");
		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

}
