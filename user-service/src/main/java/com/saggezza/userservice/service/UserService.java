package com.saggezza.userservice.service;

import java.util.List;

import javax.validation.Valid;

import com.saggezza.userservice.dto.UserDTO;
import com.saggezza.userservice.entity.User;

public interface UserService {
	User save(@Valid UserDTO user);

	void update(User user);

	User find(Long id);

	void delete(Long id);

	List<User> findAll();

	boolean existUserByEmail(String email);

	User findUserByEmail(String email);
}
