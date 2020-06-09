package com.saggezza.shoppingcart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.saggezza.orderservice.OrderServiceApplication;
import com.saggezza.orderservice.controller.OrderController;
import com.saggezza.orderservice.dto.OrderDTO;
import com.saggezza.orderservice.entity.Order;
import com.saggezza.orderservice.entity.User;
import com.saggezza.orderservice.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { OrderServiceApplication.class })
public class OrderControllerTest {

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	public void testOrderApi() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUserId((long) 1);
		Order order = new Order();
		order.setCreated(LocalDateTime.now());
		order.setId((long) 1);
		order.setUser(getUser());
		when(orderService.save(Mockito.any())).thenReturn(order);
		mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(orderDTO))
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
