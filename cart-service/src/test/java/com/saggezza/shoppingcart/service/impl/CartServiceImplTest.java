package com.saggezza.shoppingcart.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.saggezza.cartservice.entity.Product;
import com.saggezza.cartservice.service.impl.CartServiceImpl;

@RunWith(SpringRunner.class)
public class CartServiceImplTest {

	@InjectMocks
	private CartServiceImpl cartServiceImpl;

	Map<Product, Integer> products = new HashMap<>();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(cartServiceImpl, "products", products);
	}

	@Test
	public void testAddProduct() {
		Product product = getProductDetails();
		cartServiceImpl.addProduct(product);
		Map<Product, Integer> actualMap = cartServiceImpl.getProductsInCart();
		assertEquals(1, actualMap.size());
	}

	@Test
	public void testAddExistingProduct() {
		Product product = getProductDetails();
		cartServiceImpl.addProduct(product);
		cartServiceImpl.addProduct(product);
		Map<Product, Integer> actualMap = cartServiceImpl.getProductsInCart();
		assertEquals((Integer) 2, actualMap.get(product));
	}

	@Test
	public void testRemoveProduct() {
		Product product = getProductDetails();
		cartServiceImpl.addProduct(product);
		cartServiceImpl.addProduct(product);
		cartServiceImpl.removeProduct(product);
		Map<Product, Integer> actualMap = cartServiceImpl.getProductsInCart();
		assertEquals((Integer) 1, actualMap.get(product));
		cartServiceImpl.removeProduct(product);
		actualMap = cartServiceImpl.getProductsInCart();
		assertEquals(null, actualMap.get(product));
	}

	@Test
	public void testClearCart() {
		Product product = getProductDetails();
		cartServiceImpl.addProduct(product);
		Map<Product, Integer> actualMap = cartServiceImpl.getProductsInCart();
		assertEquals(1, actualMap.size());
		cartServiceImpl.clearCart();
		actualMap = cartServiceImpl.getProductsInCart();
		assertEquals(0, actualMap.size());
	}

	@Test
	public void testGetTotal() {
		Product product = getProductDetails();
		cartServiceImpl.addProduct(product);
		BigDecimal price = cartServiceImpl.getTotal();
		assertEquals(new BigDecimal(100), price);
	}

	private Product getProductDetails() {
		Product product = new Product();
		product.setCreateDate(LocalDateTime.now());
		product.setDescription("test product");
		product.setId((long) (1));
		product.setName("test product");
		product.setPrice(new BigDecimal(100));
		product.setQuantity(2);
		product.setSize("test size");
		return product;
	}

}
