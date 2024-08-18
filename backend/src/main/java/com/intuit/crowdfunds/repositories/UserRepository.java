package com.intuit.crowdfunds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intuit.crowdfunds.entites.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
