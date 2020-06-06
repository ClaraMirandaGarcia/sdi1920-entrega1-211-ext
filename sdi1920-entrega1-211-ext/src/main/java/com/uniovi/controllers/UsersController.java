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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable, @RequestParam(value = "", required = false) String searchText) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);
		
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersService.getUsersFor(pageable, email);
		
		//ROLE_USER
		if(user.getRole().equals(rolesService.getRoles()[1])) {
			if (searchText != null && !searchText.isEmpty()) {
	            users = usersService.searchByNameSurnameOrEmail(pageable, searchText, email);
	        } else {
	            users = usersService.getUsersFor(pageable, email);
	        }
		} else if(user.getRole().equals(rolesService.getRoles()[0])) {
			if (searchText != null && !searchText.isEmpty()) {
	            users = usersService.searchByNameSurnameOrEmailAdmin(pageable, searchText, email);
	        } else {
	            users = new PageImpl<User>(usersService.getUsers());
	        }
		}
		
		
		//Check if already an invitation
		//Check if already a friend
		
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		
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
		user.setRole(rolesService.getRoles()[0]);
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
