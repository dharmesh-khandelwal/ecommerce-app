package com.saggezza.productservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	@Length(min = 3, message = "*Name must have at least 5 characters")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "quantity", nullable = false)
	@Min(value = 0, message = "*Quantity has to be non negative number")
	private Integer quantity;

	@Column(name = "price", nullable = false)
	@DecimalMin(value = "0.00", message = "*Price has to be non negative number")
	private BigDecimal price;

	@Column(name = "size")
	private String size;

	@Column(name = "create_date", nullable = false, updatable = false)
	@DateTimeFormat
	private LocalDateTime createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}


	@Override
	public int hashCode() {
		return Objects.hash(createDate, description, id, name, price, quantity, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		return Objects.equals(createDate, other.createDate) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price) && Objects.equals(quantity, other.quantity)
				&& Objects.equals(size, other.size);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [id=").append(id).append(", name=").append(name).append(", description=")
				.append(description).append(", quantity=").append(quantity).append(", price=").append(price)
				.append(", size=").append(size).append(", createDate=").append(createDate).append("]");
		return builder.toString();
	}


}
