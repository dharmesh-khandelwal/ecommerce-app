package com.saggezza.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saggezza.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsUserByEmail(String email);

	long countUserByEmail(String email);

	User findUserByEmail(String email);
}
