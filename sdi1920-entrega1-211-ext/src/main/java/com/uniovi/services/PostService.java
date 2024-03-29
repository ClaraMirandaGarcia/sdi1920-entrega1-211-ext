package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.repositories.PostsRepository;

@Service
public class PostService {

	@Autowired
	private PostsRepository postsRepository;

	public void deletePost(Long id) {
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

	public List<Post> getPostsListForUser(User user) {
		List<Post> posts = new ArrayList<Post>();
		posts = postsRepository.getPostsListFor(user);
		return posts;
	}

	public void clear() {
		postsRepository.deleteAll();
	}

}
