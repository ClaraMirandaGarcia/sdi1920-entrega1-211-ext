package com.uniovi.controllers;

import java.security.Principal;
import java.sql.Date;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.FriendsService;
import com.uniovi.services.PostService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddPostFormValidator;

@Controller
public class PostsController {

	@Autowired
	private PostService postsService;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private AddPostFormValidator addPostValidator;

	@RequestMapping("/post/postsOf/{email}")
	public String getPostsOf(Model model, @PathVariable String email){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String emailAuth = auth.getName();
		
		if (friendsService.areFriends(email, emailAuth)) {
			
			User target = usersService.getUserByEmail(email);
			List<Post> posts = postsService.getPostsListForUser(target);
			model.addAttribute("friend", email);
			model.addAttribute("posts", posts);
			
		}
		
		return "post/postsOf";
	}
	
	
	@RequestMapping("/post/list")
	public String getList(Model model, Pageable pageable, Principal principal) {

		String email = principal.getName();
		User user = usersService.getUserByEmail(email);

		Page<Post> posts = new PageImpl<Post>(new LinkedList<Post>());
		posts = postsService.getPostsForUser(pageable, user);

		model.addAttribute("postsList", posts.getContent());
		model.addAttribute("page", posts);
		return "post/list";
	}

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
		return "redirect:/post/list";
	}

	@RequestMapping(value = "/post/add")
	public String getPost(Model model) {
		model.addAttribute("usersList", usersService.getUsers());

		model.addAttribute("post", new Post());
		return "post/add";
	}

}