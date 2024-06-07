package com.giant.userportal.repository;

import com.giant.userportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);
}