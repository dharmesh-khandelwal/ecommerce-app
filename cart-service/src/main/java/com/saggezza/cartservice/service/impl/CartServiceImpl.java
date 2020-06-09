package com.saggezza.cartservice.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.saggezza.cartservice.entity.Product;
import com.saggezza.cartservice.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Value("${productservice.endpoint}")
	private String productServiceEndpoint;

	private Map<Product, Integer> products = new HashMap<>();

	@Override
	public void addProduct(Product product) {
		if (this.products.containsKey(product)) {
			this.products.replace(product, this.products.get(product) + 1);
		} else {
			this.products.put(product, 1);
		}
	}

	@Override
	public void removeProduct(Product product) {
		if (this.products.containsKey(product)) {
			if (this.products.get(product) > 1)
				this.products.replace(product, this.products.get(product) - 1);
			else if (this.products.get(product) == 1) {
				this.products.remove(product);
			}
		}
	}

	@Override
	public Product findProduct(Long id) {
		RestTemplate restTemplate = new RestTemplate();
		String findProductUrl = productServiceEndpoint + "/products/" + id;
		Product product = restTemplate.getForObject(findProductUrl, Product.class);
		return product;
	}

	@Override
	public Map<Product, Integer> getProductsInCart() {
		return Collections.unmodifiableMap(this.products);
	}

	@Override
	public void clearCart() {
		this.products = new HashMap<>();
	}

	@Override
	public BigDecimal getTotal() {
		BigDecimal totalPrice = new BigDecimal(0);
		if (this.products.size() > 0) {
			for (Map.Entry<Product, Integer> pair : products.entrySet()) {
				totalPrice = totalPrice.add(pair.getKey().getPrice());
			}
		}
		return totalPrice;
	}
}
