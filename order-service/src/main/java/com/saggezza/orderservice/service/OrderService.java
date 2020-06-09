package com.saggezza.orderservice.service;

import java.util.List;

import com.saggezza.orderservice.dto.OrderDTO;
import com.saggezza.orderservice.entity.Order;

public interface OrderService {
	Order save(OrderDTO order);

	void update(Order order);

	Order find(Long id);

	void delete(Long id);

	List<Order> findAll();
}
