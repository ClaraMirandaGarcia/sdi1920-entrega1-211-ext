package com.uniovi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendshipInvitation;
import com.uniovi.entities.IdFriendship;

public interface FriendshipInvitationsRepository extends CrudRepository<FriendshipInvitation, IdFriendship> {

	@Query("select f from FriendshipInvitation f where (f.id.userEmailTo=?1 and f.id.userEmailFrom=?2) OR "
            + " (f.id.userEmailFrom=?1 and f.id.userEmailTo=?1)")
    FriendshipInvitation getInvitationEmails(String email1, String email2);
}
