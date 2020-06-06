package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friend;
import com.uniovi.entities.IdFriend;

public interface FriendsRepository extends CrudRepository<Friend, IdFriend> {

}
