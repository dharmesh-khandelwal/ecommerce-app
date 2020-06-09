package com.saggezza.productservice.service;

import java.util.List;

import javax.validation.Valid;

import com.saggezza.productservice.dto.ProductDTO;
import com.saggezza.productservice.entity.Product;

public interface ProductService {
    Product save(@Valid ProductDTO product);

    void update(Long id, @Valid ProductDTO product);

    Product find(Long id);

    void delete(Long id);

    List<Product> findAll();
}
