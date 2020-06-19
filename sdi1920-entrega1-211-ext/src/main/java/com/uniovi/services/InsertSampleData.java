package com.uniovi.services;

import java.sql.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friend;
import com.uniovi.entities.FriendshipInvitation;
import com.uniovi.entities.IdFriend;
import com.uniovi.entities.IdFriendship;
import com.uniovi.entities.Post;
import com.uniovi.entities.User;

@Service
public class InsertSampleData {

	@Autowired
	private UsersService usersService ;
	@Autowired
	private FriendsService frService;
	@Autowired
	private FriendshipInvitationService fiService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private PostService postsService;

	@PostConstruct
	public void init() {
		insertData();
	}

	public void clearData() {
		usersService.clear();
		frService.clear();
		fiService.clear();
		postsService.clear();
	}

	public void insertData() {
		// Creating normal users
		createUser("Name1", "Surname1", "example1@gmail.com", "123456");
		createUser("Name2", "Surname2", "example2@gmail.com", "123456");
		createUser("Name3", "Surname3", "example3@gmail.com", "123456");
		createUser("Name4", "Surname4", "example4@gmail.com", "123456");
		createUser("Name5", "Surname5", "example5@gmail.com", "123456");
		createUser("Name6", "Surname6", "example6@gmail.com", "123456");

		// Creating admin users
		createAdmin("Admin1", "SurnameA1", "admin1@gmail.com", "123456");
		createAdmin("Admin2", "SurnameA2", "admin2@gmail.com", "123456");

		// Sending invitations
		sendFrInvitation("example6@gmail.com", "example4@gmail.com");
		sendFrInvitation("example5@gmail.com", "example4@gmail.com");

		// Creating new friendships
		createFriends("example1@gmail.com", "example2@gmail.com");
		createFriends("example1@gmail.com", "example3@gmail.com");
		createFriends("example1@gmail.com", "example6@gmail.com");

		// Creating new posts
		createPost("TextPost1", "TitlePost1", "example6@gmail.com");
		createPost("TextPost2", "TitlePost2", "example6@gmail.com");

	}

	/**
	 * AUXILIAR METHODS
	 */

	private void createPost(String textPost, String titlePost, String emailUser) {
		Post post = new Post();
		User user = usersService.getUserByEmail(emailUser);

		post.setDate(new Date(new java.util.Date().getTime()));
		post.setText(textPost);
		post.setTitle(titlePost);
		post.setUser(user);

		postsService.addPost(post);
	}

	private void createFriends(String email1, String email2) {
		Friend friends = new Friend();
		friends.setId(new IdFriend(email1, email2));
		frService.addFriend(friends);
	}

	private void sendFrInvitation(String email1, String email2) {
		FriendshipInvitation invitation1 = new FriendshipInvitation();
		invitation1.setId(new IdFriendship(email1, email2));
		fiService.addInvitation(invitation1);
	}

	private void createAdmin(String name, String surname, String email, String password) {
		User user = new User(email, name, surname);
		user.setRole(rolesService.getRoles()[0]);
		user.setPassword(password);
		usersService.addUser(user);
	}

	private void createUser(String name, String surname, String email, String password) {
		User user = new User(email, name, surname);
		user.setRole(rolesService.getRoles()[1]);
		user.setPassword(password);
		usersService.addUser(user);
	}
	
	public void deleteUser(String email) {
		User user = usersService.getUserByEmail(email);
		usersService.deleteUser(user.getId());
	}
	
	
	

}
