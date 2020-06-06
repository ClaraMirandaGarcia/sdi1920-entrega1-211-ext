package com.uniovi.controllers;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.PostService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddPostFormValidator;

@Controller
public class PostsController {

	@Autowired
	private PostService postsService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private AddPostFormValidator addPostValidator;

	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	public String setPost(@Validated Post post, BindingResult result) {
		addPostValidator.validate(post, result);

		if (result.hasErrors()) {
			return "post/add";
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userAux = usersService.getUserByEmail(email);
		post.setUser(userAux);
		Date sqlDate = new Date(Calendar.getInstance().getTime().getTime());
		post.setDate(sqlDate);

		postsService.addPost(post);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/post/add")
	public String getPost(Model model) {
		model.addAttribute("usersList", usersService.getUsers());

		model.addAttribute("post", new Post());
		return "post/add";
	}

}