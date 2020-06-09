package com.saggezza.shoppingcart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saggezza.userservice.UserServiceApplication;
import com.saggezza.userservice.controller.UserController;
import com.saggezza.userservice.dto.UserDTO;
import com.saggezza.userservice.entity.User;
import com.saggezza.userservice.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { UserServiceApplication.class })
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testAddNewUserApi() throws Exception {
		UserDTO userDto = new UserDTO();
		userDto.setEmail("test@test.com");
		userDto.setFirstName("test");
		userDto.setLastName("user");
		userDto.setPassword("Qwerty@123");
		when(userService.save(Mockito.any())).thenReturn(getUser());
		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userDto))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private User getUser() {
		User user = new User();
		user.setAdmin(false);
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setId((long) 1);
		user.setLastName("user");
		user.setPassword("Qwerty@123");
		return user;
	}

}
