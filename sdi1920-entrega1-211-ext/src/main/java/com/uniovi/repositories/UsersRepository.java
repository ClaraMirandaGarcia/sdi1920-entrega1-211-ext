package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email <> ?1 AND u.role <> 'ROLE_ADMIN'")
	Page<User> getUsersFor(Pageable pageable, String email);

	@Query("SELECT u FROM User u WHERE u.email <> ?2 AND u.role <> 'ROLE_ADMIN' AND"
			+ "(LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1) OR LOWER(u.email) LIKE LOWER(?1))")
	Page<User> searchByNameSurnameOrEmail(Pageable pageable, String searchText, String email);

}
