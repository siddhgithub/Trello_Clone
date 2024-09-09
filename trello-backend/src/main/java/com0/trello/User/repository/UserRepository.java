package com0.trello.User.repository;

import com0.trello.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //The UserRepository interface extends the JpaRepository interface,
    // which provides a set of predefined methods for basic CRUD operations.

    User findByUserEmailAddress(String emailAddress);
}

