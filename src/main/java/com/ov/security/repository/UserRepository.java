package com.ov.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ov.model.security.User;

public interface UserRepository extends JpaRepository<User, String>{
	User findByUsername(String username);
}
