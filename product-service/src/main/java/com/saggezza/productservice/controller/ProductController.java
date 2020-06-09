package com.saggezza.productservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saggezza.productservice.dto.ProductDTO;
import com.saggezza.productservice.entity.Product;
import com.saggezza.productservice.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> findProducts() {

		List<Product> productList = productService.findAll();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		if (productList == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@PostMapping(value = "/products")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO product) {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		if (product == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		Product createdProduct = productService.save(product);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> findProduct(@PathVariable Long id) {

		Product product = productService.find(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		if (product == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PutMapping(value = "/products/{id}")
	public ResponseEntity<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO product) {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		Product productById = productService.find(id);

		if (productById == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		productService.update(id, product);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Void> removeProduct(@PathVariable Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		Product productById = productService.find(id);

		if (productById == null) {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		productService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
