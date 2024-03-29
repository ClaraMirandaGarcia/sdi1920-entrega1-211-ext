package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public Page<User> getUsersFor(Pageable pageable, String email) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.getUsersFor(pageable, email);
		return users;
	}

	public Page<User> searchByNameSurnameOrEmail(Pageable pageable, String searchText, String email) {
        Page<User> users = new PageImpl<User>(new LinkedList<User>());
        searchText = "%" + searchText + "%";
        users = usersRepository.searchByNameSurnameOrEmail(pageable, searchText, email);
        return users;
    }

	public Page<User> searchByNameSurnameOrEmailAdmin(Pageable pageable, String searchText, String email) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
        searchText = "%" + searchText + "%";
        users = usersRepository.searchByNameSurnameOrEmailAdmin(pageable, searchText, email);
        return users;
	}

	public void clear() {
		usersRepository.deleteAll();
	}

	
	
}