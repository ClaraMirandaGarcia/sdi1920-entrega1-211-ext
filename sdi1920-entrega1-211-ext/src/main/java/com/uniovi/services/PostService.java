package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.repositories.PostsRepository;

public class PostService {

	@Autowired
	private PostsRepository postsRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<Post> getPosts() {
		List<Post> users = new ArrayList<Post>();
		postsRepository.findAll().forEach(users::add);
		return users;
	}

	public Post getPost(Long id) {
		return postsRepository.findById(id).get();
	}

	public Post getUserByAuthorEmail(String title) {
		return postsRepository.findByTitle(title);
	}

	public void deleteUser(Long id) {
		postsRepository.deleteById(id);
	}

	public void addPost(Post post) {

		postsRepository.save(post);

	}

	public Page<Post> getPostsForUser(Pageable pageable, User user) {
		Page<Post> posts = new PageImpl<Post>(new LinkedList<Post>());
		posts = postsRepository.findAllByUser(pageable, user);
		return posts;
	}

}
