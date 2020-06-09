package com.saggezza.shoppingcart.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.saggezza.userservice.dto.UserDTO;
import com.saggezza.userservice.entity.User;
import com.saggezza.userservice.repository.UserRepository;
import com.saggezza.userservice.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveUser() {
		UserDTO user = new UserDTO();
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setLastName("user");
		user.setPassword("Qwerty@123");
		userServiceImpl.save(user);
		Mockito.verify(userRepository, times(1)).save(Mockito.any());
	}

	@Test
	public void testSaveAdminUser() {
		UserDTO user = new UserDTO();
		user.setEmail("admin@gmail.com");
		user.setFirstName("test");
		user.setLastName("user");
		user.setPassword("Qwerty@123");
		userServiceImpl.save(user);
		Mockito.verify(userRepository, times(1)).save(Mockito.any());
	}

	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setId((long) 1);
		user.setLastName("user");
		user.setPassword("Qwerty@123");
		userServiceImpl.update(user);
		Mockito.verify(userRepository, times(1)).save(Mockito.any());
	}

	@Test
	public void testDelete() {
		userServiceImpl.delete((long) 1);
		Mockito.verify(userRepository, times(1)).deleteById(Mockito.any());
	}

	@Test
	public void testFindUser() {
		User user = new User();
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setId((long) 1);
		user.setLastName("user");
		user.setPassword("Qwerty@123");
		Optional<User> userOptional = Optional.of(user);
		when(userRepository.findById(Mockito.anyLong())).thenReturn(userOptional);
		userServiceImpl.find((long) 1);
		Mockito.verify(userRepository, times(1)).findById(Mockito.any());
	}

	@Test
	public void testFindAll() {
		userServiceImpl.findAll();
		Mockito.verify(userRepository, times(1)).findAll();
	}

	@Test
	public void testExistUserByEmail() {
		userServiceImpl.existUserByEmail("test@test.com");
		Mockito.verify(userRepository, times(1)).existsUserByEmail(Mockito.anyString());
	}

	@Test
	public void testFindUserByEmail() {
		userServiceImpl.findUserByEmail("test@test.com");
		Mockito.verify(userRepository, times(1)).findUserByEmail(Mockito.anyString());
	}
}
