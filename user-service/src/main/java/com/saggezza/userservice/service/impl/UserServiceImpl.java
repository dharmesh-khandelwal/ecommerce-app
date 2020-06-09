package com.saggezza.userservice.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saggezza.userservice.dto.UserDTO;
import com.saggezza.userservice.entity.User;
import com.saggezza.userservice.exception.ShoppingCartException;
import com.saggezza.userservice.repository.UserRepository;
import com.saggezza.userservice.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(UserDTO userDto) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(userDto, User.class);
		if (user.getEmail().equals("admin@gmail.com")) {
			user.setAdmin(true);
		} else {
			user.setAdmin(false);
		}

		return userRepository.save(user);
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
	}

	@Override
	public User find(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new ShoppingCartException("User not found with id:" + id);
		}
		return userOptional.get();
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public boolean existUserByEmail(String email) {
		return userRepository.existsUserByEmail(email);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

}
