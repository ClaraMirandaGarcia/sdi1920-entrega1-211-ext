package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendshipInvitation;
import com.uniovi.entities.IdFriendship;
import com.uniovi.entities.User;

public interface FriendshipInvitationsRepository extends CrudRepository<FriendshipInvitation, IdFriendship> {

	@Query("select f from FriendshipInvitation f where (f.id.userEmailTo=?1 and f.id.userEmailFrom=?2) OR "
			+ " (f.id.userEmailFrom=?1 and f.id.userEmailTo=?1)")
	FriendshipInvitation getInvitationEmails(String email1, String email2);

	@Query("SELECT i.id.userEmailFrom from FriendshipInvitation i where i.id.userEmailTo=?1")
	Page<FriendshipInvitation> getInvitationsFor(Pageable pageable, String emailFrom);

	@Query("select f from FriendshipInvitation f where f.id.userEmailFrom=?1 OR f.id.userEmailTo=?1")
	List<FriendshipInvitation> getInvitationsListForUser(String email);

	@Query("SELECT u FROM User u where u.email IN (select f.id.userEmailFrom from FriendshipInvitation f where f.id.userEmailTo=?1)"
			+ " OR  u.email IN (select f.id.userEmailTo from FriendshipInvitation f where f.id.userEmailFrom=?1)")
	List<User> getInvitationsUsersListForUser(String email);

	
}
