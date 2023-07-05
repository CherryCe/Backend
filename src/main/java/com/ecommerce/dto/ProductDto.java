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
public class ProductDto {

	private int id;

	private String name;

	private double price;

	private boolean stock;

	private int quantity;

	private boolean live;

	private String image;

	private String description;

	@JsonIgnore
	private CategoryDto category;
}
