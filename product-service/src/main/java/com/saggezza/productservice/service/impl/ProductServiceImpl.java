package com.saggezza.productservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saggezza.productservice.dto.ProductDTO;
import com.saggezza.productservice.entity.Product;
import com.saggezza.productservice.exception.ShoppingCartException;
import com.saggezza.productservice.repository.ProductRepository;
import com.saggezza.productservice.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product save(ProductDTO productDto) {
		ModelMapper modelMapper = new ModelMapper();
		Product product = modelMapper.map(productDto, Product.class);
		product.setCreateDate(LocalDateTime.now());
		return productRepository.save(product);
	}

	@Override
	public void update(Long id, ProductDTO productDto) {
		ModelMapper modelMapper = new ModelMapper();
		Product product = modelMapper.map(productDto, Product.class);
		product.setId(id);
		productRepository.save(product);
	}

	@Override
	public Product find(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (!productOptional.isPresent()) {
			throw new ShoppingCartException("User not found with id:" + id);
		}
		return productOptional.get();
	}

	@Override
	public void delete(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

}
