package com.saggezza.shoppingcart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

import com.saggezza.cartservice.CartServiceApplication;
import com.saggezza.cartservice.controller.CartController;
import com.saggezza.cartservice.entity.Product;
import com.saggezza.cartservice.service.CartService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { CartServiceApplication.class })
public class CartControllerTest {

	@Mock
	private CartService cartService;

	@InjectMocks
	private CartController cartController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
	}

	@Test
	public void getFindCart() throws Exception {
		Map<Product, Integer> products = new HashMap<>();
		Product product = getProduct();
		products.put(product, 1);
		when(cartService.getProductsInCart()).thenReturn(products);
		mockMvc.perform(get("/api/cart").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void getFindEmptyCart() throws Exception {
		when(cartService.getProductsInCart()).thenReturn(null);
		mockMvc.perform(get("/api/cart").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void addCart() throws Exception {
		when(cartService.findProduct(Mockito.anyLong())).thenReturn(getProduct());
		mockMvc.perform(post("/api/cart/{id}", (long) 1).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void addCartNotFound() throws Exception {
		when(cartService.findProduct(Mockito.anyLong())).thenReturn(null);
		mockMvc.perform(post("/api/cart/{id}", (long) 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	private Product getProduct() {
		Product product = new Product();
		product.setDescription("test product");
		product.setName("test product");
		product.setPrice(new BigDecimal(100));
		product.setQuantity(3);
		product.setSize("test size");
		return product;
	}

	@Test
	public void getRemoveProduct() throws Exception {
		when(cartService.findProduct(Mockito.anyLong())).thenReturn(getProduct());
		mockMvc.perform(delete("/api/cart/{id}", (long) 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getRemoveProductNotFound() throws Exception {
		when(cartService.findProduct(Mockito.anyLong())).thenReturn(null);
		mockMvc.perform(delete("/api/cart/{id}", (long) 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
