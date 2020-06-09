package com.saggezza.orderservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.saggezza.orderservice.dto.OrderDTO;
import com.saggezza.orderservice.entity.Order;
import com.saggezza.orderservice.entity.User;
import com.saggezza.orderservice.exception.ShoppingCartException;
import com.saggezza.orderservice.repository.OrderRepository;
import com.saggezza.orderservice.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Value("${cartservice.endpoint}")
	private String cartServiceEndpoint;

	@Value("${userservice.endpoint}")
	private String userServiceEndpoint;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order save(OrderDTO orderDto) {
		RestTemplate restTemplate = new RestTemplate();
		String findUserUrl = userServiceEndpoint + "/users/" + orderDto.getUserId();
		User user = restTemplate.getForObject(findUserUrl, User.class);

		String cartUrl = cartServiceEndpoint + "/cart";
		Long cartPrice = restTemplate.getForObject(cartUrl + "/totalprice", Long.class);

		Order order = new Order();
		order.setUser(user);
		order.setCreated(LocalDateTime.now());
		order.setCartPrice(cartPrice);

		restTemplate.delete(cartUrl);
		return orderRepository.save(order);
	}

	@Override
	public void update(Order order) {
		orderRepository.save(order);
	}

	@Override
	public Order find(Long id) {
		Optional<Order> productOptional = orderRepository.findById(id);
		if (!productOptional.isPresent()) {
			throw new ShoppingCartException("User not found with id:" + id);
		}
		return productOptional.get();
	}

	@Override
	public void delete(Long id) {
		orderRepository.deleteById(id);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
}
