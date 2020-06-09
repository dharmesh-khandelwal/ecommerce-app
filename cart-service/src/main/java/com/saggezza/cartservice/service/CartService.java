package com.saggezza.cartservice.service;

import java.math.BigDecimal;
import java.util.Map;

import com.saggezza.cartservice.entity.Product;

public interface CartService {
	void addProduct(Product product);

	void removeProduct(Product product);

	Map<Product, Integer> getProductsInCart();

	void clearCart();

	BigDecimal getTotal();

	Product findProduct(Long id);
}
