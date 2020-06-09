package com.saggezza.shoppingcart.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.saggezza.productservice.dto.ProductDTO;
import com.saggezza.productservice.entity.Product;
import com.saggezza.productservice.repository.ProductRepository;
import com.saggezza.productservice.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveProduct() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setDescription("test product");
		productDTO.setName("test product");
		productDTO.setPrice(new BigDecimal(100));
		productDTO.setQuantity(2);
		productDTO.setSize("test size");
		productServiceImpl.save(productDTO);
		Mockito.verify(productRepository, times(1)).save(Mockito.any());
	}

	@Test
	public void testUpdateProduct() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setDescription("test product");
		productDTO.setName("test product");
		productDTO.setPrice(new BigDecimal(100));
		productDTO.setQuantity(3);
		productDTO.setSize("test size");
		productServiceImpl.update((long) 1, productDTO);
		Mockito.verify(productRepository, times(1)).save(Mockito.any());
	}

	@Test
	public void testDelete() {
		productServiceImpl.delete((long) 1);
		Mockito.verify(productRepository, times(1)).deleteById(Mockito.any());
	}

	@Test
	public void testFindProduct() {
		Product product = new Product();
		product.setDescription("test product");
		product.setName("test product");
		product.setPrice(new BigDecimal(100));
		product.setQuantity(3);
		product.setSize("test size");
		Optional<Product> productOptional = Optional.of(product);
		when(productRepository.findById(Mockito.anyLong())).thenReturn(productOptional);
		productServiceImpl.find((long) 1);
		Mockito.verify(productRepository, times(1)).findById(Mockito.any());
	}

	@Test
	public void testFindAll() {
		productServiceImpl.findAll();
		Mockito.verify(productRepository, times(1)).findAll();
	}

}
