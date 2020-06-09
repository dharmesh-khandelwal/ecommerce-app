package com.saggezza.shoppingcart.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.saggezza.orderservice.entity.Order;
import com.saggezza.orderservice.entity.User;
import com.saggezza.orderservice.repository.OrderRepository;
import com.saggezza.orderservice.service.impl.OrderServiceImpl;

@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
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

	@Test
	public void testUpdate() {
		Order order = new Order();
		order.setCreated(LocalDateTime.now());
		order.setId((long) 1);
		order.setUser(getUser());
		orderServiceImpl.update(order);
		Mockito.verify(orderRepository, times(1)).save(Mockito.any());
	}

	@Test
	public void testDelete() {
		orderServiceImpl.delete((long) 1);
		Mockito.verify(orderRepository, times(1)).deleteById(Mockito.any());
	}

	@Test
	public void testFindOrder() {
		Order order = new Order();
		order.setCreated(LocalDateTime.now());
		order.setId((long) 1);
		order.setUser(getUser());
		Optional<Order> orderOptional = Optional.of(order);
		when(orderRepository.findById(Mockito.anyLong())).thenReturn(orderOptional);
		Order actual = orderServiceImpl.find((long) 1);
		Mockito.verify(orderRepository, times(1)).findById(Mockito.any());
		assertEquals(order.getId(), actual.getId());

	}

	@Test
	public void testFindAll() {
		orderServiceImpl.findAll();
		Mockito.verify(orderRepository, times(1)).findAll();
	}

}
