package com.saggezza.shoppingcart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.saggezza.productservice.ProductServiceApplication;
import com.saggezza.productservice.controller.ProductController;
import com.saggezza.productservice.dto.ProductDTO;
import com.saggezza.productservice.entity.Product;
import com.saggezza.productservice.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { ProductServiceApplication.class })
public class ProductControllerTest {

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void testFindProductsApi() throws Exception {
		List<Product> products = new ArrayList<Product>();
		Product product = getProduct();
		products.add(product);
		when(productService.findAll()).thenReturn(products);
		mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testFindProductsApiNotFound() throws Exception {
		List<Product> products = new ArrayList<Product>();
		Product product = getProduct();
		products.add(product);
		when(productService.findAll()).thenReturn(null);
		mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void addProduct() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setDescription("test product");
		productDTO.setName("test product");
		productDTO.setPrice(new BigDecimal(100));
		productDTO.setQuantity(2);
		productDTO.setSize("test size");
		when(productService.save(Mockito.any())).thenReturn(getProduct());
		mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(asJsonString(productDTO))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
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
	public void getProductApi() throws Exception {
		when(productService.find(Mockito.anyLong())).thenReturn(getProduct());
		mockMvc.perform(get("/api/products/{id}", (long) 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getProductApiNotFound() throws Exception {
		when(productService.find(Mockito.anyLong())).thenReturn(null);
		mockMvc.perform(get("/api/products/{id}", (long) 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testRemoveProduct() throws Exception {
		when(productService.find(Mockito.anyLong())).thenReturn(getProduct());
		mockMvc.perform(delete("/api/products/{id}", (long) 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getRemoveProductNotFound() throws Exception {
		when(productService.find(Mockito.anyLong())).thenReturn(null);
		mockMvc.perform(delete("/api/products/{id}", (long) 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateProduct() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setDescription("test product");
		productDTO.setName("test product");
		productDTO.setPrice(new BigDecimal(100));
		productDTO.setQuantity(2);
		productDTO.setSize("test size");
		when(productService.find(Mockito.any())).thenReturn(getProduct());
		mockMvc.perform(put("/api/products/{id}", (long) 1).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(productDTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testUpdateProductNotFound() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setDescription("test product");
		productDTO.setName("test product");
		productDTO.setPrice(new BigDecimal(100));
		productDTO.setQuantity(2);
		productDTO.setSize("test size");
		when(productService.find(Mockito.any())).thenReturn(null);
		mockMvc.perform(put("/api/products/{id}", (long) 1).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(productDTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
