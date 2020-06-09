package com.saggezza.orderservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saggezza.orderservice.dto.OrderDTO;
import com.saggezza.orderservice.entity.Order;
import com.saggezza.orderservice.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping(value = "/orders")
	public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDTO order) {

		Order createdOrder = orderService.save(order);
		return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
	}

	@GetMapping(value = "/orders")
	public ResponseEntity<List<Order>> findOrders() {

		List<Order> orderList = orderService.findAll();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		if (orderList == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(orderList, HttpStatus.OK);
	}

	@GetMapping(value = "/orders/{id}")
	public ResponseEntity<Order> findOrder(@PathVariable Long id) {

		Order order = orderService.find(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		if (order == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

}
