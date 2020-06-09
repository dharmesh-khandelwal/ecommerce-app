package com.saggezza.productservice.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

public class ProductDTO {

	@Length(min = 3, message = "*Name must have at least 5 characters")
	private String name;

	private String description;

	@Min(value = 0, message = "*Quantity has to be non negative number")
	private Integer quantity;

	@DecimalMin(value = "0.00", message = "*Price has to be non negative number")
	private BigDecimal price;

	private String size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
