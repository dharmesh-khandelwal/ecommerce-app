package com.saggezza.cartservice.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saggezza.cartservice.entity.Product;
import com.saggezza.cartservice.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping(value = "/cart/{id}")
	public ResponseEntity<Map<Product, Integer>> addProductCart(@PathVariable Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		Product product = cartService.findProduct(id);

		if (product == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		cartService.addProduct(product);
		Map<Product, Integer> cart = cartService.getProductsInCart();
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping(value = "/cart")
	public ResponseEntity<Map<Product, Integer>> findCart() {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		Map<Product, Integer> cart = cartService.getProductsInCart();

		if (cart == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.OK).body(cart);
	}

	@GetMapping(value = "/cart/totalprice")
	public ResponseEntity<Long> findCartTotalPrice() {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		BigDecimal cart = cartService.getTotal();

		return ResponseEntity.status(HttpStatus.OK).body(cart.longValue());
	}

	@DeleteMapping(value = "/cart/{id}")
	public ResponseEntity<Void> removeProduct(@PathVariable Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		Product product = cartService.findProduct(id);

		if (product == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		cartService.removeProduct(product);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/cart")
	public ResponseEntity<Void> clearCart() {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		cartService.clearCart();

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
