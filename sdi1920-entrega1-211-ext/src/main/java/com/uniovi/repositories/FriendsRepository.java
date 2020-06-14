package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friend;
import com.uniovi.entities.IdFriend;
import com.uniovi.entities.User;

public interface FriendsRepository extends CrudRepository<Friend, IdFriend> {

	@Query("SELECT u FROM User u where u.email IN (select f.id.userEmailFrom from Friend f where f.id.userEmailTo=?1)"
            + " OR  u.email IN (select f.id.userEmailTo from Friend f where f.id.userEmailFrom=?1)")
    Page<User> getFriendsFor(Pageable pageable, String email);
	
	
	@Query("SELECT u FROM User u where u.email IN (select f.id.userEmailFrom from Friend f where f.id.userEmailTo=?1)"
            + " OR  u.email IN (select f.id.userEmailTo from Friend f where f.id.userEmailFrom=?1)")
    List<User> getFriendsListFor(String email);

	
	
	
}
