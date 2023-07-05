package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemDto {

	private int id;

	private ProductDto product;

	private int quantity;

	private double total;

	@JsonIgnore
	private OrderDto order;
}
